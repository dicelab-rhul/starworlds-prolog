package uk.ac.rhul.cs.dice.starworlds.prolog.swi.term;

import org.jpl7.Term;

public abstract class SimpleSWITermFactory extends SWITermFactory {

	@Override
	public Term toTerm(Object arg, Class<?>[] typeinfo) {
		return toTerm(arg);
	}

	@Override
	public Object fromTerm(Term term, Class<?>[] typeinfo) {
		return fromTerm(term);
	}

}
