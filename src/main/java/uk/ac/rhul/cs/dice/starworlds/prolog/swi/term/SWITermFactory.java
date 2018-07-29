package uk.ac.rhul.cs.dice.starworlds.prolog.swi.term;

import org.jpl7.Term;

import uk.ac.rhul.cs.dice.starworlds.prolog.swi.term.GlobalTermFactory.ObjectFactory;
import uk.ac.rhul.cs.dice.starworlds.prolog.swi.term.primitive.IntegerFactory;
import uk.ac.rhul.cs.dice.starworlds.prolog.swi.term.primitive.StringFactory;
import uk.ac.rhul.cs.dice.starworlds.prolog.swi.term.primitive.ArrayFactory;
import uk.ac.rhul.cs.dice.starworlds.prolog.swi.term.primitive.DoubleFactory;
import uk.ac.rhul.cs.dice.starworlds.prolog.term.TermFactory;
import uk.ac.rhul.cs.dice.starworlds.prolog.utils.SWIUtils;

public abstract class SWITermFactory implements TermFactory<Term> {

	public static final GlobalTermFactory GLOBAL = GlobalTermFactory.getInstance();

	public SWITermFactory() {
	}

	public ArrayFactory getArrayFactory() {
		return (ArrayFactory) GLOBAL.getFactory(SWIUtils.ARRAYDELIM);
	}

	public <T> Term arrayToTerm(T[] array) {
		System.out.println("array to term: " + array.getClass().getComponentType());
		return getArrayFactory().toTerm(array, new Class<?>[] { array.getClass().getComponentType() });
	}

	public <T> T[] termToArray(Term term, Class<T> type) {
		return uncheckedTermToArray(term, type);
	}

	public <T> T fromSWITerm(Term term, Class<T> type) {
		return type.cast(GLOBAL.getFactory(type).fromTerm(term));
	}

	public <T> Term toSWITerm(T obj) {
		return GLOBAL.getFactory(obj.getClass()).toTerm(obj);
	}

	public StringFactory getStringFactory() {
		return (StringFactory) GLOBAL.getFactory(String.class);
	}

	public DoubleFactory getDoubleFactory() {
		return (DoubleFactory) GLOBAL.getFactory(Double.class);
	}

	public TermFactory<Term> getFloatFactory() {
		return GLOBAL.getFactory(Float.class);
	}

	public IntegerFactory getIntegerFactory() {
		return (IntegerFactory) GLOBAL.getFactory(Integer.class);
	}

	@SuppressWarnings("unchecked")
	private <T> T[] uncheckedTermToArray(Term term, Class<T> type) {
		return (T[]) getArrayFactory().fromTerm(term, new Class<?>[] { type });
	}

	public TermFactory<Term> resolveFactory(String name) {
		return GLOBAL.getFactory(name);
	}

	public TermFactory<Term> resolveFactory(Class<?> cls) {
		return GLOBAL.getFactory(cls);
	}

	public TermFactory<Term> resolveFactory(Term term) {
		return GLOBAL.getFactory(term);
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName();
	}
}
