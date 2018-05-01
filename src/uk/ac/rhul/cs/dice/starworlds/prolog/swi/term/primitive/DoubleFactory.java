package uk.ac.rhul.cs.dice.starworlds.prolog.swi.term.primitive;

import org.jpl7.Term;

import uk.ac.rhul.cs.dice.starworlds.prolog.term.TermFactory;

public final class DoubleFactory implements TermFactory<Term> {

	@Override
	public final Term toTerm(Object arg) {
		return new org.jpl7.Float((double) arg);
	}

	@Override
	public final Double fromTerm(Term term) {
		return term.doubleValue();
	}

	@Override
	public Term toTerm(Object arg, Class<?>[] typeinfo) {
		return toTerm(arg);
	}

	@Override
	public Object fromTerm(Term term, Class<?>[] typeinfo) {
		return fromTerm(term);
	}

	@Override
	public final Class<?> getObjectClass() {
		return Double.class;
	}
}