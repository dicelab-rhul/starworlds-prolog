package uk.ac.rhul.cs.dice.starworlds.prolog.termmap;

import uk.ac.rhul.cs.dice.starworlds.perception.Perception;
import uk.ac.rhul.cs.dice.starworlds.prolog.termmap.term.Term;

public abstract class PerceptionMapper<L, T extends Term<L>, P extends Perception> extends
		AbstractTermMapper<L, T, P, P> {

	public PerceptionMapper(Class<P> cls, String predicateName) {
		super(cls, predicateName);
	}

}
