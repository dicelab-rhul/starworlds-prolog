package uk.ac.rhul.cs.dice.starworlds.prolog.swi.term.primitive;

import org.jpl7.Atom;
import org.jpl7.Term;

import uk.ac.rhul.cs.dice.starworlds.prolog.term.TermFactory;

public class NullFactory implements TermFactory<Term> {

	public static final String NULL = "null";

	@Override
	public Term toTerm(Object arg) {
		return new Atom(NULL);
	}

	@Override
	public Object fromTerm(Term term) {
		return null;
	}

	@Override
	public Term toTerm(Object arg, Class<?>[] typeinfo) {
		return new Atom(NULL);
	}

	@Override
	public Object fromTerm(Term term, Class<?>[] typeinfo) {
		return null;
	}

	@Override
	public Class<?> getObjectClass() {
		return null;
	}

}
