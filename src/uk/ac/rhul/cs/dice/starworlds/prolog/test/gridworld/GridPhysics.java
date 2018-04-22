package uk.ac.rhul.cs.dice.starworlds.prolog.test.gridworld;

import uk.ac.rhul.cs.dice.starworlds.environment.physics.AbstractPhysics;

public class GridPhysics extends AbstractPhysics<GridUniverse> {

	@Override
	public void cycleAddition() {
		System.out.println(this.getEnvironment().getState().getGrid());
	}

}
