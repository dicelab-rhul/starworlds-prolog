package uk.ac.rhul.cs.dice.starworlds.prolog.components;

import uk.ac.rhul.cs.dice.starworlds.entities.agent.components.AbstractActuator;
import uk.ac.rhul.cs.dice.starworlds.entities.agent.components.ComponentKey;

public class PrologActuator<K extends ComponentKey> extends AbstractActuator<K> {

	public PrologActuator(K componentKey) {
		super(componentKey);
	}

}
