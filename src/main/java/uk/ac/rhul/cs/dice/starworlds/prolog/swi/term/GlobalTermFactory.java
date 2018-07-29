package uk.ac.rhul.cs.dice.starworlds.prolog.swi.term;

import io.github.lukehutch.fastclasspathscanner.scanner.ScanResult;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jpl7.Atom;
import org.jpl7.Compound;
import org.jpl7.Term;

import uk.ac.rhul.cs.dice.starworlds.annotations.Discovery;
import uk.ac.rhul.cs.dice.starworlds.exceptions.StarWorldsRuntimeException;
import uk.ac.rhul.cs.dice.starworlds.prolog.exceptions.PrologTermException;
import uk.ac.rhul.cs.dice.starworlds.prolog.swi.term.collection.concrete.ArrayListFactory;
import uk.ac.rhul.cs.dice.starworlds.prolog.swi.term.collection.concrete.HashSetFactory;
import uk.ac.rhul.cs.dice.starworlds.prolog.swi.term.collection.concrete.LinkedHashSetFactory;
import uk.ac.rhul.cs.dice.starworlds.prolog.swi.term.collection.concrete.LinkedListFactory;
import uk.ac.rhul.cs.dice.starworlds.prolog.swi.term.primitive.ArrayFactory;
import uk.ac.rhul.cs.dice.starworlds.prolog.swi.term.primitive.CharacterFactory;
import uk.ac.rhul.cs.dice.starworlds.prolog.swi.term.primitive.DoubleFactory;
import uk.ac.rhul.cs.dice.starworlds.prolog.swi.term.primitive.EmptyArrayFactory;
import uk.ac.rhul.cs.dice.starworlds.prolog.swi.term.primitive.EnumFactory;
import uk.ac.rhul.cs.dice.starworlds.prolog.swi.term.primitive.FloatFactory;
import uk.ac.rhul.cs.dice.starworlds.prolog.swi.term.primitive.IntegerFactory;
import uk.ac.rhul.cs.dice.starworlds.prolog.swi.term.primitive.LongFactory;
import uk.ac.rhul.cs.dice.starworlds.prolog.swi.term.primitive.NullFactory;
import uk.ac.rhul.cs.dice.starworlds.prolog.swi.term.primitive.StringFactory;
import uk.ac.rhul.cs.dice.starworlds.prolog.term.TermFactory;
import uk.ac.rhul.cs.dice.starworlds.prolog.term.Termable;
import uk.ac.rhul.cs.dice.starworlds.prolog.utils.ReflectionUtils;
import uk.ac.rhul.cs.dice.starworlds.prolog.utils.SWIUtils;

public class GlobalTermFactory implements TermFactory<Term> {

	private static final GlobalTermFactory INSTANCE;
	static {
		try {
			// System.out.println("Initialising global term factory");
			INSTANCE = new GlobalTermFactory();
			INSTANCE.initialiseFactories();
		} catch (ClassNotFoundException e) {
			throw new StarWorldsRuntimeException("Failed to initialise: " + GlobalTermFactory.class, e);
		}
	}

	private Map<String, TermFactory<Term>> termFactories;
	private Map<Class<?>, TermFactory<Term>> objectFactories;
	private ScanResult scan;

	private NameResolver nameResolver;

	public static GlobalTermFactory getInstance() {
		return INSTANCE;
	}

	private GlobalTermFactory() {
		if (Discovery.scanned()) {
			scan = Discovery.scan();
		} else {
			scan = Discovery.newScan(new String[] {});
		}
		// System.out.println(StringUtils.mapToString(objectFactories));
	}

	private void initialiseFactories() throws ClassNotFoundException {
		termFactories = new HashMap<>();
		objectFactories = new HashMap<>();
		nameResolver = new NameResolver();

		// primitiveFactories = new HashMap<>();
		IntegerFactory intFactory = new IntegerFactory();
		objectFactories.put(Integer.class, intFactory);
		objectFactories.put(org.jpl7.Integer.class, intFactory);

		FloatFactory floatFactory = new FloatFactory();
		objectFactories.put(Float.class, floatFactory);
		objectFactories.put(org.jpl7.Float.class, floatFactory);

		objectFactories.put(Double.class, new DoubleFactory());
		objectFactories.put(Long.class, new LongFactory());
		objectFactories.put(Character.class, new CharacterFactory());

		StringFactory stringFactory = new StringFactory();
		objectFactories.put(String.class, stringFactory);
		objectFactories.put(Atom.class, stringFactory);

		// Collection factories
		objectFactories.put(Object[].class, new ArrayFactory());

		objectFactories.put(Collection.class, new ArrayListFactory());
		objectFactories.put(List.class, new ArrayListFactory());
		objectFactories.put(ArrayList.class, new ArrayListFactory());
		objectFactories.put(LinkedList.class, new LinkedListFactory());

		objectFactories.put(Set.class, new HashSetFactory());
		objectFactories.put(HashSet.class, new HashSetFactory());
		objectFactories.put(LinkedHashSet.class, new LinkedHashSetFactory());

		termFactories.put(NullFactory.NULL, new NullFactory());
		termFactories.put(SWIUtils.ARRAYDELIM, new ArrayFactory());
		termFactories.put(SWIUtils.EMPTYARRAY, new EmptyArrayFactory());

		List<String> classes = scan.getNamesOfClassesWithAnnotation(Termable.class);
		for (String c : classes) {
			initialiseClass(Class.forName(c));
		}
	}

	public ScanResult getScanResult() {
		return scan;
	}

	private void initialiseClass(Class<?> cls) {
		if (!cls.isAnonymousClass()) {
			Termable termable = cls.getAnnotation(Termable.class);
			String name = nameResolver.addName(cls, termable);
			TermFactory<Term> factory;
			if (!declareFactory(cls, name, termable.factory())) {
				if (!cls.isEnum()) {
					List<Field> fields = ReflectionUtils.getFieldWithAnnotation(cls, Termable.class);
					fields.forEach(f -> f.setAccessible(true));
					factory = new ObjectFactory(cls, fields.toArray(new Field[fields.size()]));
				} else {
					factory = new EnumFactory(cls);
				}
				termFactories.put(name, factory);
				objectFactories.put(cls, factory);
				declaredFactoryInfo(cls, name, factory.getClass());
			}

		} else {
			if (!Enum.class.isAssignableFrom(cls.getEnclosingClass())) {
				throw new PrologTermException(
						this.getClass().getSimpleName()
								+ " does not support the construction of annomous classes unless they are defined as an Enum constant");
			}
		}
	}

	public void declareFactory(Class<?> cls, SWITermFactory factory) {
		declareFactory(cls, cls.getSimpleName(), factory);
	}

	public void declareFactory(Class<?> cls, String name, TermFactory<Term> factory) {
		if (objectFactories.containsKey(cls)) {
			factoryAlreadyExistsWarning(cls, factory);
		} else {
			name = nameResolver.addName(cls, name);
		}
		termFactories.put(name, factory);
		objectFactories.put(cls, factory);
		declaredFactoryInfo(cls, name, factory.getClass());
	}

	private void factoryAlreadyExistsWarning(Class<?> cls, TermFactory<Term> factory) {
		System.out.println("Warning: Overwritting factory: " + objectFactories.get(cls) + ", for class: " + cls
				+ ", with: " + factory);
	}

	protected boolean declareFactory(Class<?> cls, String name, Class<?> fcls) {
		if (SWITermFactory.class.isAssignableFrom(fcls)) {
			SWITermFactory factory;
			try {
				factory = (SWITermFactory) fcls.newInstance();
				termFactories.put(name, factory);
				objectFactories.put(cls, factory);
			} catch (InstantiationException | IllegalAccessException e) {
				throw new PrologTermException("Failed to instantiate custom " + SWITermFactory.class.getName() + " - "
						+ fcls + " must delcare a public empty constructor", e);
			}
			declaredFactoryInfo(cls, name, fcls);
			return true;
		}
		return false;
	}

	private void declaredFactoryInfo(Class<?> cls, String name, Class<?> fcls) {
		System.out.println("declared factory: " + cls + "->" + name + " : " + fcls.getSimpleName());
	}

	public synchronized Term toTerm(Object arg) {
		return getFactory(arg.getClass()).toTerm(arg);
	}

	public synchronized Object fromTerm(Term term) {
		return getFactory(term).fromTerm(term);
	}

	public synchronized Object fromTerm(Term term, Class<?> type) {
		return getFactory(type).fromTerm(term);
	}

	public TermFactory<Term> getFactory(Class<?> cls) {
		TermFactory<Term> factory = this.objectFactories.get(cls);
		if (factory != null) {
			return factory;
		} else {
			throw new PrologTermException("No TermFactory exists for objects of type: " + cls);
		}
	}

	public TermFactory<Term> getFactory(String name) {
		TermFactory<Term> factory = termFactories.get(name);
		if (factory != null) {
			return factory;
		} else {
			throw new PrologTermException("No TermFactory exists for term with name: " + name);
		}
	}

	public TermFactory<Term> getFactory(Term term) {
		TermFactory<Term> factory;
		if (term instanceof Compound) {
			factory = termFactories.get(term.name());
		} else {
			factory = objectFactories.get(term.getClass());
		}
		if (factory != null) {
			return factory;
		} else {
			throw new PrologTermException("No TermFactory exists for term: " + term);
		}
	}

	public String resolveName(Class<?> cls) {
		return nameResolver.getTermName(cls);
	}

	public Class<?> resolveClass(Term term) {
		return nameResolver.getObjectClass(term);
	}

	private Class<?> getEnclosing(Class<?> cls) {
		return cls.isAnonymousClass() ? getEnclosing(cls.getEnclosingClass()) : cls;
	}

	private class NameResolver {
		private Map<String, Class<?>> termNames = new HashMap<>();
		private Map<Class<?>, String> objectNames = new HashMap<>();

		public String getTermName(Class<?> cls) {
			return objectNames.get(cls);
		}

		public Class<?> getObjectClass(Term term) {
			return termNames.get(term.name());
		}

		public String addName(Class<?> cls, String name) {
			name = name.toLowerCase();
			if (!termNames.containsKey(name)) {
				termNames.put(name, cls);
				objectNames.put(cls, name);
				return name;
			} else {
				warning(cls, name, 1);
				return addName(cls, name, 1);
			}
		}

		public String addName(Class<?> cls, Termable termable) {
			String name = "".equals(termable.name()) ? cls.getSimpleName() : termable.name();
			name = name.toLowerCase();
			if (!termNames.containsKey(name)) {
				termNames.put(name, cls);
				objectNames.put(cls, name);
				return name;
			} else {
				warning(cls, name, 1);
				return addName(cls, name, 1);
			}
		}

		private String addName(Class<?> cls, String name, Integer count) {
			String newName = name + count;
			if (!termNames.containsKey(newName)) {
				termNames.put(newName, cls);
				objectNames.put(cls, newName);
				return newName;
			} else {
				warning(cls, newName, count + 1);
				return addName(cls, name, count + 1);
			}
		}

		private void warning(Class<?> cls, String name, int count) {
			System.out.println("Warning: term name conflict: " + name + System.lineSeparator() + "from classes: " + cls
					+ " and " + termNames.get(name) + ", " + cls + " term will be renamed to " + name + count);
		}
	}

	// *********************** ******************** *********************** //
	// ************************* OBJECT FACTORIES ************************* //
	// *********************** ******************** *********************** //

	public class ObjectFactory extends SWITermFactory {

		private Class<?> cls;
		private Field[] fields;

		public ObjectFactory(Class<?> cls, Field[] fields) {
			super();
			this.cls = cls;
			this.fields = fields;
		}

		@Override
		public Term toTerm(Object arg) {
			Term[] terms = new Term[fields.length];
			for (int i = 0; i < fields.length; i++) {
				Object v = null;
				try {
					v = fields[i].get(arg);
				} catch (IllegalArgumentException | IllegalAccessException e) {
					throw new PrologTermException("Failed to get value of field: " + fields[i] + " from object: " + arg
							+ " when converting to term", e);
				}
				if (v != null) {
					Class<?> c = getEnclosing(v.getClass());
					if (!c.isArray()) {
						// System.out.println(fields[i]);
						terms[i] = objectFactories.get(c).toTerm(v, ReflectionUtils.getGenericTypes(fields[i]));
					} else {
						terms[i] = fromArray((Object[]) v);
					}
				} else {
					terms[i] = termFactories.get(NullFactory.NULL).toTerm(null);
				}
			}
			// System.out.println(Arrays.toString(terms));
			return new Compound(nameResolver.getTermName(arg.getClass()), terms);
		}

		private Term fromArray(Object[] obj) {
			Class<?>[] typeinfo = { obj.getClass().getComponentType() };
			if (obj.length > 0) {
				return termFactories.get(SWIUtils.ARRAYDELIM).toTerm(obj, typeinfo);
			} else {
				return termFactories.get(SWIUtils.EMPTYARRAY).toTerm(obj, typeinfo);
			}
		}

		@Override
		public Object fromTerm(Term term) {
			// System.out.println(term);
			Term[] terms = term.args();
			if (fields.length == terms.length) {
				try {
					Object o = cls.newInstance();
					for (int i = 0; i < terms.length; i++) {
						Object r;
						if (Collection.class.isAssignableFrom(fields[i].getType())) {
							r = objectFactories.get(fields[i].getType()).fromTerm(terms[i],
									ReflectionUtils.getGenericTypes(fields[i]));
						} else {
							r = GlobalTermFactory.this.fromTerm(terms[i], fields[i].getType());
						}
						fields[i].set(o, r);
					}
					return o;
				} catch (InstantiationException e) {
					throw new PrologTermException("Could not constuct object of type: " + cls
							+ " ensure that the class declares an empty constructor", e);
				} catch (Exception e) {
					throw new PrologTermException("Invalid term: " + term + " for construction of object of type: "
							+ cls, e);
				}
			} else {
				throw new PrologTermException("Invalid term: " + term + " for construction of object of type: " + cls);
			}
		}

		@Override
		public Class<?> getObjectClass() {
			return Object.class;
		}

		@Override
		public Term toTerm(Object arg, Class<?>[] typeinfo) {
			throw new UnsupportedOperationException("TODO");
		}

		@Override
		public Object fromTerm(Term term, Class<?>[] typeinfo) {
			throw new UnsupportedOperationException("TODO");
		}
	}

	@Override
	public Term toTerm(Object arg, Class<?>[] typeinfo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object fromTerm(Term term, Class<?>[] typeinfo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Class<?> getObjectClass() {
		// TODO Auto-generated method stub
		return null;
	}
}
