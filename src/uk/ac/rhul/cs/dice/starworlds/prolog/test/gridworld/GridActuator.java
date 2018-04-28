package uk.ac.rhul.cs.dice.starworlds.prolog.test.gridworld;

import uk.ac.rhul.cs.dice.starworlds.actions.environmental.CommunicationAction;
import uk.ac.rhul.cs.dice.starworlds.annotations.PossibleAction;
import uk.ac.rhul.cs.dice.starworlds.entities.agent.components.AbstractActuator;
import uk.ac.rhul.cs.dice.starworlds.prolog.components.key.SimpleSWIComponentKey;

@PossibleAction({ GridMoveAction.class, CommunicationAction.class })
public class GridActuator extends AbstractActuator<SimpleSWIComponentKey> {

	public GridActuator() {
		super(SimpleSWIComponentKey.ACTUATOR);
	}

}
