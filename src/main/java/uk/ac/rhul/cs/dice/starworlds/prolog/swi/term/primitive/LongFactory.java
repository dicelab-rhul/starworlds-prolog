package uk.ac.rhul.cs.dice.starworlds.prolog.swi.term.primitive;

import org.jpl7.Term;

import uk.ac.rhul.cs.dice.starworlds.prolog.term.TermFactory;

public final class LongFactory implements TermFactory<Term> {

	@Override
	public final Term toTerm(Object arg) {
		return new org.jpl7.Integer((Long) arg);
	}

	@Override
	public final Long fromTerm(Term term) {
		return term.longValue();
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
		return Long.class;
	}
}