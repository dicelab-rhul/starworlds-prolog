package uk.ac.rhul.cs.dice.starworlds.prolog.test.gridworld;

import java.util.HashSet;
import java.util.Set;

import uk.ac.rhul.cs.dice.starworlds.actions.environmental.EnvironmentalAction;
import uk.ac.rhul.cs.dice.starworlds.entities.agent.components.AbstractActuator;
import uk.ac.rhul.cs.dice.starworlds.entities.agent.components.concrete.SimpleComponentKey;

public class GridActuator extends AbstractActuator<SimpleComponentKey> {

	public final static Set<Class<?>> POSSIBLEACTIONS = new HashSet<>();
	static {
		POSSIBLEACTIONS.add(MoveAction.class);
	}

	public GridActuator() {
		super(SimpleComponentKey.ACTUATOR);

	}

	@Override
	public void attempt(EnvironmentalAction action) {
		this.getBody().getEnvironment().attemptAction(action);
	}

	@Override
	public Set<Class<?>> getPossibleActions() {
		return POSSIBLEACTIONS;
	}

}
