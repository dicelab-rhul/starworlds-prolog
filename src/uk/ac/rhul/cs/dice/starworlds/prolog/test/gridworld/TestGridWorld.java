package uk.ac.rhul.cs.dice.starworlds.prolog.test.gridworld;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import uk.ac.rhul.cs.dice.starworlds.entities.agent.AbstractAgent;
import uk.ac.rhul.cs.dice.starworlds.entities.agent.components.AbstractComponent;
import uk.ac.rhul.cs.dice.starworlds.entities.agent.components.concrete.GeneralActiveSensor;
import uk.ac.rhul.cs.dice.starworlds.entities.agent.components.concrete.GeneralPassiveSensor;
import uk.ac.rhul.cs.dice.starworlds.environment.physics.actiondefinition.ActionDefinition;
import uk.ac.rhul.cs.dice.starworlds.environment.physics.actiondefinition.CommunicationActionDefinition;
import uk.ac.rhul.cs.dice.starworlds.environment.physics.actiondefinition.SenseActionDefinition;
import uk.ac.rhul.cs.dice.starworlds.environment.subscription.SensorSubscriptionHandler;
import uk.ac.rhul.cs.dice.starworlds.exceptions.StarWorldsException;
import uk.ac.rhul.cs.dice.starworlds.prolog.components.key.SimpleSWIComponentKey;
import uk.ac.rhul.cs.dice.starworlds.prolog.swi.agent.SWIMind;
import uk.ac.rhul.cs.dice.starworlds.prolog.test.gridworld.GridPerception.GridPerceptionFactory;
import alice.tuprolog.InvalidTheoryException;

public class TestGridWorld {

	// private static final String TUEXAMPLEMIND =
	// "C:/Users/Ben/Work/MastersThesis/StarworldsWorkspace/starworlds-prolog/src/uk/ac/rhul/cs/dice/starworlds/prolog/test/gridworld/tu/TuPrologExampleMind.pl";
	private static final String SWIEXAMPLEMIND = "C:/Users/Ben/Work/MastersThesis/StarworldsWorkspace/starworlds-prolog/src/uk/ac/rhul/cs/dice/starworlds/prolog/test/gridworld/SWIPrologExampleMind.pl";

	public static void main(String[] args) throws IllegalAccessException, InvalidTheoryException, IOException,
			StarWorldsException {
		// create agents
		Set<AbstractAgent<?>> agents = new HashSet<>();

		// do the module stuff
		String examplemindmodule = SWIMind.declareMind(SWIEXAMPLEMIND);
		agents.add(getSWIGridAgent(examplemindmodule));

		// agents.add(getGridAgent());
		// create ambient
		GridAmbient ambient = new GridAmbient(agents, null, null, null);
		ambient.addFilter(GridAttributeFilter.KEY);

		// create physics
		GridPhysics physics = new GridPhysics();
		// add action definitions
		// add MoveAction Definition
		ActionDefinition<GridMoveAction, GridUniverse> moveAD = new ActionDefinition<GridMoveAction, GridUniverse>(
				GridMoveAction.class, new GridMoveActionRuleSet());
		physics.addActionDefinition(moveAD);
		// add SenseAction Definition

		// create a perception factory for the SWIGrid perception

		SenseActionDefinition<GridUniverse, GridPerception> senseAD = new SenseActionDefinition<GridUniverse, GridPerception>(
				new GridPerceptionFactory());
		physics.addActionDefinition(senseAD);
		// add CommunicationAction Definition
		CommunicationActionDefinition<GridUniverse> communicationAD = new CommunicationActionDefinition<GridUniverse>();
		physics.addActionDefinition(communicationAD);

		// create and declare Sensor Types in the subscription handler

		SensorSubscriptionHandler subscriptionHandler = new SensorSubscriptionHandler();

		// create universe
		GridUniverse universe = new GridUniverse(ambient, physics, subscriptionHandler);

		// start simulation
		universe.simulate();

	}

	public static GridAgent getSWIGridAgent(String module) throws InvalidTheoryException, IOException {
		return new GridAgent(getSWIComponents(), new SWIMind<SimpleSWIComponentKey>(module));
	}

	public static Collection<AbstractComponent<SimpleSWIComponentKey>> getSWIComponents() {
		List<AbstractComponent<SimpleSWIComponentKey>> components = new ArrayList<>();
		components.add(new GeneralPassiveSensor<SimpleSWIComponentKey>(SimpleSWIComponentKey.PASSIVESENSOR));
		components.add(new GeneralActiveSensor<SimpleSWIComponentKey>(SimpleSWIComponentKey.ACTIVESENSOR));
		components.add(new GridActuator());
		return components;
	}
}
