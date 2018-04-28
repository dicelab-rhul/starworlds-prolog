package uk.ac.rhul.cs.dice.starworlds.prolog.test.gridworld;

import uk.ac.rhul.cs.dice.starworlds.entities.PhysicalBody;
import uk.ac.rhul.cs.dice.starworlds.environment.physics.ruleset.ActionRuleSet;
import uk.ac.rhul.cs.dice.starworlds.prolog.test.gridworld.GridAmbient.Coordinate;
import uk.ac.rhul.cs.dice.starworlds.prolog.test.gridworld.GridAmbient.Grid;
import uk.ac.rhul.cs.dice.starworlds.prolog.test.gridworld.GridAmbient.Tile;

public class GridMoveActionRuleSet implements ActionRuleSet<GridMoveAction, GridUniverse> {

	@Override
	public boolean isPossible(GridMoveAction action, GridUniverse environment) {
		// aslong as the tile is empty or goal
		Grid grid = environment.getState().getGrid();
		Coordinate position = grid.getCoordinateOf(action.getActor().getId());
		Tile to = grid.getTile(action.getDirection().move(position));
		// System.out.println("possible: " + (to.isEmpty() || to.isGoal()));
		return to.isEmpty() || to.isGoal();
	}

	@Override
	public boolean perform(GridMoveAction action, GridUniverse environment) {
		// perform the move
		Grid grid = environment.getState().getGrid();
		Coordinate from = grid.getCoordinateOf(action.getActor().getId());
		Coordinate to = action.getDirection().move(from);
		PhysicalBody replaced = grid.updatePosition(action.getActor().getId(), from, to);
		if (replaced != null) {
			// replaced must be the goal!
			// System.out.println("Agent: " + action.getActor() + " has reached the goal!");
		}
		// notify any agents that are within range of the new position?
		return true;
	}

	@Override
	public boolean verify(GridMoveAction action, GridUniverse environment) {
		// we don't need to check... nothing will go wrong... ...hopefully!
		return true;
	}

}