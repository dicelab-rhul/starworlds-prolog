package uk.ac.rhul.cs.dice.starworlds.prolog.swi.term;

import org.jpl7.Term;

import uk.ac.rhul.cs.dice.starworlds.prolog.term.TermFactory;

public abstract class SWITermFactory implements TermFactory<Term> {

	public static final GlobalTermFactory GLOBAL = GlobalTermFactory.getInstance();

	public SWITermFactory() {
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
