package uk.ac.rhul.cs.dice.starworlds.prolog.termmap;

import uk.ac.rhul.cs.dice.starworlds.perception.Perception;

public abstract class PerceptionMapper<P extends Perception> extends AbstractTermMapper<P, P> {

	public PerceptionMapper(Class<P> cls, String predicateName) {
		super(cls, predicateName);
	}

}
