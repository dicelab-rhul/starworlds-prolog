package uk.ac.rhul.cs.dice.starworlds.prolog.test.gridworld;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import uk.ac.rhul.cs.dice.starworlds.actions.environmental.EnvironmentalAction;
import uk.ac.rhul.cs.dice.starworlds.actions.environmental.SenseAction;
import uk.ac.rhul.cs.dice.starworlds.entities.agent.components.AbstractComponent;
import uk.ac.rhul.cs.dice.starworlds.environment.ambient.filter.FilterChain;
import uk.ac.rhul.cs.dice.starworlds.prolog.components.key.SimpleSWIComponentKey;
import uk.ac.rhul.cs.dice.starworlds.prolog.swi.agent.SWIAgent;
import uk.ac.rhul.cs.dice.starworlds.prolog.swi.agent.SWIMind;

public class GridAgent extends SWIAgent<SimpleSWIComponentKey> {

	public GridAgent(Collection<AbstractComponent<SimpleSWIComponentKey>> components,
			SWIMind<SimpleSWIComponentKey> mind) {
		super(components, mind);
	}

	@Override
	public void run() {
		super.run();
		// HACKY - remove
		FilterChain<?, ?> chain = FilterChain.start(GridAmbient.GRIDKEY).append(new GridAttributeFilter());
		SenseAction action = new SenseAction(chain);
		Map<SimpleSWIComponentKey, EnvironmentalAction> actions = new HashMap<>();
		actions.put(SimpleSWIComponentKey.ACTIVESENSOR, action);
		this.attempt(actions);
	}
}
