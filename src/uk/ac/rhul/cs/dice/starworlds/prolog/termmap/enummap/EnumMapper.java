package uk.ac.rhul.cs.dice.starworlds.prolog.termmap.enummap;

import uk.ac.rhul.cs.dice.starworlds.prolog.termmap.TermMapper;
import uk.ac.rhul.cs.dice.starworlds.prolog.termmap.term.Term;
import alice.tuprolog.Struct;

public abstract class EnumMapper<L, T extends Term<L>, P extends Enum<P>> implements TermMapper<L, T, P, P> {

	private Class<P> cls;

	public EnumMapper(Class<P> cls) {
		this.cls = cls;
	}

	@Override
	public P fromTerm(T term) {
		return Enum.valueOf(cls, ((Struct) term).getName());
	}
}