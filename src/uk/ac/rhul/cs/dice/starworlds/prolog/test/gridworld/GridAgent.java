package uk.ac.rhul.cs.dice.starworlds.prolog.test.gridworld;

import java.util.Collection;

import uk.ac.rhul.cs.dice.starworlds.entities.agent.AbstractAgentMind;
import uk.ac.rhul.cs.dice.starworlds.entities.agent.AbstractAutonomousAgent;
import uk.ac.rhul.cs.dice.starworlds.entities.agent.components.AbstractComponent;
import uk.ac.rhul.cs.dice.starworlds.entities.agent.components.concrete.SimpleComponentKey;

public class GridAgent extends AbstractAutonomousAgent<SimpleComponentKey> {

	public GridAgent(Collection<AbstractComponent<SimpleComponentKey>> components,
			AbstractAgentMind<SimpleComponentKey> mind) {
		super(components, mind);
	}
}
