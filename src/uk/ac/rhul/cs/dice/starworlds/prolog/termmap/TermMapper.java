package uk.ac.rhul.cs.dice.starworlds.prolog.termmap;

import uk.ac.rhul.cs.dice.starworlds.prolog.termmap.term.Term;

public interface TermMapper<L, T extends Term<L>, P, R> {

	public T toTerm(P arg);

	public R fromTerm(T term);

}
