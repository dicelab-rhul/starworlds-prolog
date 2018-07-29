package uk.ac.rhul.cs.dice.starworlds.prolog.swi.term.primitive;

import java.lang.reflect.Array;

import org.jpl7.Atom;
import org.jpl7.Term;

import uk.ac.rhul.cs.dice.starworlds.prolog.term.TermFactory;
import uk.ac.rhul.cs.dice.starworlds.prolog.utils.SWIUtils;

public class EmptyArrayFactory implements TermFactory<Term> {

	@Override
	public Term toTerm(Object arg) {
		return new Atom(SWIUtils.EMPTYARRAY);
	}

	@Override
	public Object fromTerm(Term term) {
		return new Object[0];
	}

	@Override
	public Term toTerm(Object arg, Class<?>[] typeinfo) {
		return new Atom(SWIUtils.EMPTYARRAY);
	}

	@Override
	public Object fromTerm(Term term, Class<?>[] typeinfo) {
		return Array.newInstance(typeinfo[0], 0);
	}

	@Override
	public Class<?> getObjectClass() {
		return Object[].class;
	}
}
