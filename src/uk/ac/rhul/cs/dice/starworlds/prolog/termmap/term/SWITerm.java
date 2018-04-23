package uk.ac.rhul.cs.dice.starworlds.prolog.termmap.term;

public class SWITerm implements Term<org.jpl7.Term> {

	private org.jpl7.Term term;

	public SWITerm(org.jpl7.Term term) {
		super();
		this.term = term;
	}

	public org.jpl7.Term getTerm() {
		return term;
	}

	@Override
	public String getName() {
		return term.name();
	}

	@Override
	public Integer getArity() {
		return term.arity();
	}

}
