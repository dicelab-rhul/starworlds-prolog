package uk.ac.rhul.cs.dice.starworlds.prolog.swi.term.components.key;

import org.jpl7.Atom;
import org.jpl7.Compound;
import org.jpl7.Term;

import uk.ac.rhul.cs.dice.starworlds.entities.agent.components.concrete.SimpleComponentKey;
import uk.ac.rhul.cs.dice.starworlds.prolog.swi.term.SWITermFactory;

public class SimpleComponentKeyFactory extends SWITermFactory {

	public static final String TERMNAME = "component";

	@Override
	public Term toTerm(Object arg) {
		return new Compound(TERMNAME, new Term[] { new Atom(arg.toString().toLowerCase()) });
	}

	@Override
	public Object fromTerm(Term term) {
		return SimpleComponentKey.valueOf(term.arg(1).name().toUpperCase());
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
		return SimpleComponentKey.class;
	}

	public static String getTermName() {
		return TERMNAME;
	}
}