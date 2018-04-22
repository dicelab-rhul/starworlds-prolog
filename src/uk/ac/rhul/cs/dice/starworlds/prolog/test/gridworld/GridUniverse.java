package uk.ac.rhul.cs.dice.starworlds.prolog.test.gridworld;

import uk.ac.rhul.cs.dice.starworlds.appearances.EnvironmentAppearance;
import uk.ac.rhul.cs.dice.starworlds.environment.interfaces.AbstractEnvironment;
import uk.ac.rhul.cs.dice.starworlds.environment.interfaces.Universe;
import uk.ac.rhul.cs.dice.starworlds.environment.physics.AbstractPhysics;
import uk.ac.rhul.cs.dice.starworlds.environment.subscription.SensorSubscriptionHandler;
import uk.ac.rhul.cs.dice.starworlds.initialisation.IDFactory;

public class GridUniverse extends AbstractEnvironment<GridUniverse> implements Universe<GridUniverse> {

	public GridUniverse(GridAmbient ambient, AbstractPhysics<GridUniverse> physics,
			SensorSubscriptionHandler subscriptionHandler) {
		super(GridUniverse.class, ambient, physics, subscriptionHandler, new EnvironmentAppearance(IDFactory
				.getInstance().getNewID(), false, true), true);
	}
	
	@Override
	public GridAmbient getState() {
		return (GridAmbient) super.getState();
	}

	@Override
	public Boolean isSimple() {
		return true;
	}

	@Override
	public void postInitialisation() {
	}

	@Override
	public void simulate() {
		this.physics.simulate();
	}

	@Override
	public void run() {
		simulate();
	}

}
