package uk.ac.rhul.cs.dice.starworlds.prolog.swi.term.primitive;

import org.jpl7.Atom;
import org.jpl7.Compound;
import org.jpl7.Term;

import uk.ac.rhul.cs.dice.starworlds.prolog.exceptions.PrologTermException;
import uk.ac.rhul.cs.dice.starworlds.prolog.swi.term.GlobalTermFactory;
import uk.ac.rhul.cs.dice.starworlds.prolog.term.TermFactory;

public final class EnumFactory implements TermFactory<Term> {

	private Class<?> cls;
	private GlobalTermFactory global = GlobalTermFactory.getInstance();

	public EnumFactory(Class<?> cls) {
		this.cls = cls;
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
	public final Term toTerm(Object arg) {
		return new Compound(global.resolveName(cls), new Term[] { new Atom(arg.toString().toLowerCase()) });
	}

	@Override
	public final Object fromTerm(Term term) {
		Enum<?>[] enums = (Enum<?>[]) this.cls.getEnumConstants();
		String name = term.arg(1).name().toUpperCase();
		for (int i = 0; i < enums.length; i++) {
			if (name.equals(enums[i].toString())) {
				return enums[i];
			}
		}
		throw new PrologTermException("Failed to construct Enum object from term: " + term);
	}

	@Override
	public final Class<?> getObjectClass() {
		return Enum.class;
	}

}