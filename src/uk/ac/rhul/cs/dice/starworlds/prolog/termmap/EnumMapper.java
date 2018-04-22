package uk.ac.rhul.cs.dice.starworlds.prolog.termmap;

import alice.tuprolog.Struct;
import alice.tuprolog.Term;

public class EnumMapper<P extends Enum<P>> implements TermMapper<P, P> {

	private Class<P> cls;

	public EnumMapper(Class<P> cls) {
		this.cls = cls;
	}

	@Override
	public Term toTerm(P arg) {
		return new Struct(arg.name().toLowerCase());
	}

	@Override
	public P fromTerm(Term term) {
		return Enum.valueOf(cls, ((Struct) term).getName());
	}
}