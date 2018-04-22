package uk.ac.rhul.cs.dice.starworlds.prolog.termmap;

import alice.tuprolog.Term;

public interface TermMapper<P, R> {

	public Term toTerm(P arg);

	public R fromTerm(Term term);

}
