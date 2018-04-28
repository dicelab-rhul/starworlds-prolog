package uk.ac.rhul.cs.dice.starworlds.prolog.test.gridworld;

import uk.ac.rhul.cs.dice.starworlds.annotations.PossibleAction;
import uk.ac.rhul.cs.dice.starworlds.prolog.components.PrologActuator;
import uk.ac.rhul.cs.dice.starworlds.prolog.components.key.SimpleSWIComponentKey;

@PossibleAction({ GridMoveAction.class })
public class GridActuator extends PrologActuator<SimpleSWIComponentKey> {

	public GridActuator() {
		super(SimpleSWIComponentKey.ACTUATOR);
	}

}
