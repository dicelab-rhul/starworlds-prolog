package uk.ac.rhul.cs.dice.starworlds.prolog.components.key;

import org.jpl7.Atom;
import org.jpl7.Compound;
import org.jpl7.Term;

import uk.ac.rhul.cs.dice.starworlds.prolog.swi.term.SWITermFactory;
import uk.ac.rhul.cs.dice.starworlds.prolog.term.Termable;

@Termable(name = SimpleSWIComponentKey.TERMNAME, factory = SimpleSWIComponentKey.class)
public enum SimpleSWIComponentKey implements PrologComponentKey {

	ACTUATOR, PASSIVESENSOR, ACTIVESENSOR;

	public static final String TERMNAME = "component";

	public static class SimpleSWIComponentKeyFactory extends SWITermFactory {
		@Override
		public Term toTerm(Object arg) {
			return new Compound(TERMNAME, new Term[] { new Atom(arg.toString().toLowerCase()) });
		}

		@Override
		public Object fromTerm(Term term) {
			return SimpleSWIComponentKey.valueOf(term.arg(1).name().toUpperCase());
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
			return SimpleSWIComponentKey.class;
		}
	}

}
