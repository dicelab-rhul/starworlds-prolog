package uk.ac.rhul.cs.dice.starworlds.prolog.termmap.term;

import alice.tuprolog.Struct;

public class TuTerm implements Term<Struct> {

	private Struct term;

	public TuTerm(Struct term) {
		super();
		this.term = term;
	}

	public Struct getTerm() {
		return term;
	}

	@Override
	public String getName() {
		return term.getName();
	}

	@Override
	public Integer getArity() {
		return term.getArity();
	}

}
