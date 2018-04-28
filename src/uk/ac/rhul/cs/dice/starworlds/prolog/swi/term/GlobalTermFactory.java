package uk.ac.rhul.cs.dice.starworlds.prolog.swi.term;

import io.github.lukehutch.fastclasspathscanner.scanner.ScanResult;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jpl7.Atom;
import org.jpl7.Compound;
import org.jpl7.Term;

import uk.ac.rhul.cs.dice.starworlds.annotations.Discovery;
import uk.ac.rhul.cs.dice.starworlds.exceptions.StarWorldsRuntimeException;
import uk.ac.rhul.cs.dice.starworlds.prolog.exceptions.PrologTermException;
import uk.ac.rhul.cs.dice.starworlds.prolog.term.TermFactory;
import uk.ac.rhul.cs.dice.starworlds.prolog.term.Termable;
import uk.ac.rhul.cs.dice.starworlds.prolog.utils.ReflectionUtils;
import uk.ac.rhul.cs.dice.starworlds.prolog.utils.SWIUtils;

public class GlobalTermFactory implements SWITermFactory {

	private static GlobalTermFactory INSTANCE;

	private Map<String, SWITermFactory> termFactories;
	private Map<Class<?>, SWITermFactory> objectFactories;

	private ListFactory listFactory = new ListFactory();

	private NameResolver nameResolver;

	public static GlobalTermFactory initialise() {
		if (INSTANCE == null) {
			try {
				INSTANCE = new GlobalTermFactory();
			} catch (ClassNotFoundException e) {
				throw new StarWorldsRuntimeException("Failed to initialise: " + GlobalTermFactory.class, e);
			}
		}
		return INSTANCE;
	}

	public static GlobalTermFactory getInstance() {
		return INSTANCE;
	}

	// initialise
	private GlobalTermFactory() throws ClassNotFoundException {
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
		objectFactories.put(Character.class, new CharFactory());

		StringFactory stringFactory = new StringFactory();
		objectFactories.put(String.class, stringFactory);
		objectFactories.put(Atom.class, stringFactory);

		termFactories.put(SWIUtils.ARRAYDELIM, new ListFactory());

		// possible add support for other types like byte etc

		ScanResult result;
		if (Discovery.scanned()) {
			result = Discovery.scan();
		} else {
			result = Discovery.newScan(new String[] {});
		}
		List<String> classes = result.getNamesOfClassesWithAnnotation(Termable.class);
		for (String c : classes) {
			initialiseClass(Class.forName(c));
		}
	}

	private void initialiseClass(Class<?> cls) {
		if (!cls.isAnonymousClass()) {
			Termable termable = cls.getAnnotation(Termable.class);
			String name = nameResolver.addName(cls, termable);
			SWITermFactory factory;
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

	public void declareFactory(Class<?> cls, String name, SWITermFactory factory) {
		if (objectFactories.containsKey(cls)) {
			factoryAlreadyExistsWarning(cls, factory);
		} else {
			name = nameResolver.addName(cls, name);
		}
		termFactories.put(name, factory);
		objectFactories.put(cls, factory);
		declaredFactoryInfo(cls, name, factory.getClass());
	}

	private void factoryAlreadyExistsWarning(Class<?> cls, SWITermFactory factory) {
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

	@Override
	public Term toTerm(Object arg) throws Exception {
		if (!arg.getClass().isArray()) {
			return this.objectFactories.get(arg.getClass()).toTerm(arg);
		} else {
			return this.listFactory.toTerm(arg);
		}

	}

	@Override
	public Object fromTerm(Term term) throws Exception {
		if (term instanceof Compound) {
			return fromCompoundTerm(term);
		} else {
			if (emptyList(term)) {
				return new Object[0];
			}
			return objectFactories.get(term.getClass()).fromTerm(term);
		}
	}

	private Object fromCompoundTerm(Term term) throws Exception {
		SWITermFactory factory = termFactories.get(term.name());
		if (factory != null) {
			return factory.fromTerm(term);
		}
		throw new PrologTermException("Failed to convert term: " + term + " term name is invalid");
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

		// public Class<?> getObjectClass(String name) {
		// return termNames.get(name);
		// }

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

	private class ListFactory implements SWITermFactory {

		@Override
		public Term toTerm(Object arg) throws Exception {
			Object[] array = (Object[]) arg;
			Term list = new Atom("[]");
			for (int i = array.length - 1; i >= 0; --i) {
				list = new Compound(SWIUtils.ARRAYDELIM, new Term[] {
						objectFactories.get(array[i].getClass()).toTerm(array[i]), list });
			}
			return list;
		}

		@Override
		public Object fromTerm(Term term) throws Exception {
			Term firstTerm = term.arg(1);
			TermFactory<Term> factory;
			if (firstTerm instanceof Compound) {
				// System.out.println(firstTerm);
				factory = termFactories.get(firstTerm.name());
			} else {
				factory = objectFactories.get(firstTerm.getClass());
			}
			Object o1 = factory.fromTerm(term.arg(1));
			Object[] objects = (Object[]) Array.newInstance(o1.getClass(), getListLength(term));
			objects[0] = o1;
			Integer i = 1;
			if (!term.arg(2).name().equals("[]")) {
				term = term.arg(2);
				do {
					// System.out.println(term);
					objects[i] = factory.fromTerm(term.arg(1));
					i++;
					term = term.arg(2);
				} while (!term.name().equals("[]"));
			}
			return objects;
		}

		private Integer getListLength(Term list) {
			Integer i = 1;
			while (!list.arg(2).name().equals("[]")) {
				i++;
				list = list.arg(2);
			}
			return i;
		}
	}

	private class ObjectFactory implements SWITermFactory {

		private Class<?> cls;
		private Field[] fields;

		public ObjectFactory(Class<?> cls, Field[] fields) {
			super();
			this.cls = cls;
			this.fields = fields;
		}

		@Override
		public Term toTerm(Object arg) throws Exception {
			Term[] terms = new Term[fields.length];
			for (int i = 0; i < fields.length; i++) {
				Object v = fields[i].get(arg);
				if (v != null) {
					Class<?> c = getEnclosing(v.getClass());
					// System.out.println(c);
					if (!c.isArray()) {
						terms[i] = objectFactories.get(c).toTerm(v);
					} else {
						terms[i] = listFactory.toTerm(v);
					}
				} else {
					terms[i] = new Atom("null");
				}
			}
			// System.out.println(Arrays.toString(terms));
			return new Compound(nameResolver.getTermName(arg.getClass()), terms);
		}

		@Override
		public Object fromTerm(Term term) throws Exception {
			// System.out.println(term);
			Term[] terms = term.args();
			if (fields.length == terms.length) {
				try {
					Object o = cls.newInstance();
					for (int i = 0; i < terms.length; i++) {
						// System.out.println(fields[i].getType());
						// System.out.println(terms[i] + "    " + fields[i].getType());
						if (!"null".equals(terms[i].toString())) {
							if (!fields[i].getType().isArray()) {
								// System.out.println(fields[i].getType() + " : " + terms[i]);
								Object r = GlobalTermFactory.this.fromTerm(terms[i]);
								// Object r = objectFactories.get(fields[i].getType()).fromTerm(terms[i]);
								fields[i].set(o, r);
							} else {
								if (!term.name().equals("[]")) {
									listFactory.fromTerm(terms[i]);
								} else {
									fields[i].set(o, Array.newInstance(fields[i].getType(), 0));
								}
							}
						} else {
							fields[i].set(o, null);
						}
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
	}

	private class EnumFactory implements SWITermFactory {

		private Class<?> cls;

		public EnumFactory(Class<?> cls) {
			this.cls = cls;
		}

		@Override
		public Term toTerm(Object arg) throws Exception {
			return new Compound(nameResolver.getTermName(cls), new Term[] { new Atom(arg.toString().toLowerCase()) });
		}

		@Override
		public Object fromTerm(Term term) throws Exception {
			Enum<?>[] enums = (Enum<?>[]) this.cls.getEnumConstants();
			String name = term.arg(1).name().toUpperCase();
			for (int i = 0; i < enums.length; i++) {
				if (name.equals(enums[i].toString())) {
					return enums[i];
				}
			}
			throw new PrologTermException("Failed to construct Enum object from term: " + term);
		}
	}

	private class FloatFactory implements SWITermFactory {

		@Override
		public Term toTerm(Object arg) {
			return new org.jpl7.Float((float) arg);
		}

		@Override
		public Float fromTerm(Term term) {
			return term.floatValue();
		}
	}

	private class DoubleFactory implements SWITermFactory {

		@Override
		public Term toTerm(Object arg) {
			return new org.jpl7.Float((double) arg);
		}

		@Override
		public Double fromTerm(Term term) {
			return term.doubleValue();
		}
	}

	private class IntegerFactory implements SWITermFactory {

		@Override
		public Term toTerm(Object arg) {
			return new org.jpl7.Integer((Integer) arg);
		}

		@Override
		public Integer fromTerm(Term term) {
			return term.intValue();
		}
	}

	private class LongFactory implements SWITermFactory {

		@Override
		public Term toTerm(Object arg) {
			return new org.jpl7.Integer((Long) arg);
		}

		@Override
		public Long fromTerm(Term term) {
			return term.longValue();
		}
	}

	private class CharFactory implements SWITermFactory {
		@Override
		public Term toTerm(Object arg) {
			return new Atom(String.valueOf((Character) arg));
		}

		@Override
		public Integer fromTerm(Term term) {
			return term.intValue();
		}
	}

	private class StringFactory implements SWITermFactory {

		@Override
		public Term toTerm(Object arg) {
			// System.out.println(arg);
			return new Atom((String) arg);
		}

		@Override
		public String fromTerm(Term term) {
			return term.name();
		}
	}

	public boolean emptyList(Term term) {
		return term instanceof Atom && "[]".equals(term.name());
	}

}
