package uk.ac.rhul.cs.dice.starworlds.prolog.swi.term.primitive;

import org.jpl7.Term;

import uk.ac.rhul.cs.dice.starworlds.prolog.term.TermFactory;

public final class IntegerFactory implements TermFactory<Term> {

	@Override
	public final Term toTerm(Object arg) {
		return new org.jpl7.Integer((Integer) arg);
	}

	@Override
	public final Integer fromTerm(Term term) {
		return term.intValue();
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
	public Class<?> getObjectClass() {
		return Integer.class;
	}
}