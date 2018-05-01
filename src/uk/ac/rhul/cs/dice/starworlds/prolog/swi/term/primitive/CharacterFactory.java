package uk.ac.rhul.cs.dice.starworlds.prolog.swi.term.primitive;

import org.jpl7.Atom;
import org.jpl7.Term;

import uk.ac.rhul.cs.dice.starworlds.prolog.term.TermFactory;

public final class CharacterFactory implements TermFactory<Term> {

	@Override
	public final Term toTerm(Object arg) {
		return new Atom(String.valueOf((Character) arg));
	}

	@Override
	public final Character fromTerm(Term term) {
		return term.name().charAt(0);
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
		return Character.class;
	}
}