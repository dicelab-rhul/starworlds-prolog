package uk.ac.rhul.cs.dice.starworlds.prolog.components;

import uk.ac.rhul.cs.dice.starworlds.annotations.PossibleAction;
import uk.ac.rhul.cs.dice.starworlds.entities.agent.components.AbstractActiveSensor;
import uk.ac.rhul.cs.dice.starworlds.entities.agent.components.ComponentKey;
import uk.ac.rhul.cs.dice.starworlds.entities.agent.components.Sensor;
import uk.ac.rhul.cs.dice.starworlds.prolog.actions.PrologSenseAction;

@PossibleAction({ PrologSenseAction.class })
public class PrologActiveSensor<K extends ComponentKey> extends AbstractActiveSensor<K> implements Sensor<K> {

	public PrologActiveSensor(K componentKey) {
		super(componentKey);
	}

}
