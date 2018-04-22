package uk.ac.rhul.cs.dice.starworlds.prolog.test.gridworld;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import uk.ac.rhul.cs.dice.starworlds.entities.agent.AbstractAutonomousAgent;
import uk.ac.rhul.cs.dice.starworlds.entities.agent.components.AbstractComponent;
import uk.ac.rhul.cs.dice.starworlds.entities.agent.components.Sensor;
import uk.ac.rhul.cs.dice.starworlds.entities.agent.components.concrete.SimpleActiveSensor;
import uk.ac.rhul.cs.dice.starworlds.entities.agent.components.concrete.SimpleComponentKey;
import uk.ac.rhul.cs.dice.starworlds.entities.agent.components.concrete.SimplePassiveSensor;
import uk.ac.rhul.cs.dice.starworlds.environment.physics.actiondefinition.ActionDefinition;
import uk.ac.rhul.cs.dice.starworlds.environment.physics.actiondefinition.CommunicationActionDefinition;
import uk.ac.rhul.cs.dice.starworlds.environment.physics.actiondefinition.SenseActionDefinition;
import uk.ac.rhul.cs.dice.starworlds.environment.subscription.SensorSubscriptionHandler;
import uk.ac.rhul.cs.dice.starworlds.prolog.test.gridworld.MoveAction.MoveActionRuleSet;
import alice.tuprolog.InvalidTheoryException;

public class TestGridWorld {

	public static void main(String[] args) throws IllegalAccessException, InvalidTheoryException, IOException {
		// create agents
		Set<AbstractAutonomousAgent<?>> agents = new HashSet<>();
		agents.add(getGridAgent());
		// agents.add(getGridAgent());
		// create ambient
		GridAmbient ambient = new GridAmbient(agents, null, null, null);
		// create physics
		GridPhysics physics = new GridPhysics();
		// add action definitions
		// add MoveAction Definition
		ActionDefinition<MoveAction, GridUniverse> moveAD = new ActionDefinition<MoveAction, GridUniverse>(
				MoveAction.class, new MoveActionRuleSet());
		physics.addActionDefinition(moveAD);
		// add SenseAction Definition
		SenseActionDefinition<GridUniverse> communicationAD = new SenseActionDefinition<GridUniverse>();
		physics.addActionDefinition(communicationAD);
		// add CommunicationAction Definition
		CommunicationActionDefinition<GridUniverse> senseAD = new CommunicationActionDefinition<GridUniverse>();
		physics.addActionDefinition(senseAD);

		// create and declare Sensor Types in the subscription handler
		Set<Class<? extends Sensor<?>>> sensors = new HashSet<>();
		sensors.add(SimplePassiveSensor.class);
		sensors.add(SimpleActiveSensor.class);
		SensorSubscriptionHandler subscriptionHandler = new SensorSubscriptionHandler(sensors);

		// create universe
		GridUniverse universe = new GridUniverse(ambient, physics, subscriptionHandler);

		// start simulation
		universe.simulate();

	}

	public static Collection<AbstractComponent<SimpleComponentKey>> getComponents() {
		List<AbstractComponent<SimpleComponentKey>> components = new ArrayList<>();
		components.add(new SimplePassiveSensor());
		components.add(new SimpleActiveSensor());
		components.add(new GridActuator());
		return components;
	}

	public static GridAgent getGridAgent() throws InvalidTheoryException, IOException {
		return new GridAgent(
				getComponents(),
				new GridAgentMind(
						"C:/Users/Ben/Work/MastersThesis/StarworldsWorkspace/StarWorldsTP/src/uk/ac/rhul/cs/dice/starworlds/prolog/mind/PrologExampleMind.pl"));
	}
}
