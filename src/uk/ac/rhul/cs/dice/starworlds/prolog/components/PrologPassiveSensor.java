package uk.ac.rhul.cs.dice.starworlds.prolog.components;

import uk.ac.rhul.cs.dice.starworlds.entities.agent.components.AbstractPassiveSensor;
import uk.ac.rhul.cs.dice.starworlds.entities.agent.components.ComponentKey;

public class PrologPassiveSensor<K extends ComponentKey> extends AbstractPassiveSensor<K> {

	public PrologPassiveSensor(K componentKey) {
		super(componentKey);
	}

}
