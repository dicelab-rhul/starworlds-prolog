package uk.ac.rhul.cs.dice.starworlds.prolog.test.gridworld;

import uk.ac.rhul.cs.dice.starworlds.actions.environmental.PhysicalAction;
import uk.ac.rhul.cs.dice.starworlds.entities.PhysicalBody;
import uk.ac.rhul.cs.dice.starworlds.environment.physics.ruleset.ActionRuleSet;
import uk.ac.rhul.cs.dice.starworlds.prolog.test.gridworld.GridAmbient.Coordinate;
import uk.ac.rhul.cs.dice.starworlds.prolog.test.gridworld.GridAmbient.Grid;
import uk.ac.rhul.cs.dice.starworlds.prolog.test.gridworld.GridAmbient.Grid.Tile;

public class MoveAction extends PhysicalAction {

	private static final long serialVersionUID = -990069227039913880L;

	private Direction direction;

	public MoveAction(Direction direction) {
		this.direction = direction;
	}

	public Direction getDirection() {
		return direction;
	}

	@Override
	public String toString() {
		return new StringBuilder(this.getClass().getSimpleName()).append("(").append(this.direction).append(")")
				.toString();
	}

	public static enum Direction implements DirectionMoveInterface {
		NORTH {
			public Coordinate move(Coordinate coord) {
				return new Coordinate(coord.getX(), coord.getY() - 1);
			}
		},
		SOUTH {
			public Coordinate move(Coordinate coord) {
				return new Coordinate(coord.getX(), coord.getY() + 1);
			}
		},
		EAST {
			public Coordinate move(Coordinate coord) {
				return new Coordinate(coord.getX() + 1, coord.getY());
			}
		},
		WEST {
			public Coordinate move(Coordinate coord) {
				return new Coordinate(coord.getX() - 1, coord.getY());
			}
		};
	}

	public interface DirectionMoveInterface {
		public Coordinate move(Coordinate coord);
	}

	public static class MoveActionRuleSet implements ActionRuleSet<MoveAction, GridUniverse> {

		@Override
		public boolean isPossible(MoveAction action, GridUniverse environment) {
			// aslong as the tile is empty or goal
			Grid grid = environment.getState().getGrid();
			Coordinate position = grid.getCoordinateOf(action.getActor().getId());
			Tile to = grid.getTile(action.direction.move(position));
			// System.out.println("possible: " + (to.isEmpty() || to.isGoal()));
			return to.isEmpty() || to.isGoal();
		}

		@Override
		public boolean perform(MoveAction action, GridUniverse environment) {
			// perform the move
			Grid grid = environment.getState().getGrid();
			Coordinate from = grid.getCoordinateOf(action.getActor().getId());
			Coordinate to = action.direction.move(from);
			PhysicalBody replaced = grid.updatePosition(action.getActor().getId(), from, to);
			if (replaced != null) {
				// replaced must be the goal!
				// System.out.println("Agent: " + action.getActor() + " has reached the goal!");
			}
			// notify any agents that are within range of the new position?
			return true;
		}

		@Override
		public boolean verify(MoveAction action, GridUniverse environment) {
			// we don't need to check... nothing will go wrong... ...hopefully!
			return true;
		}

	}
}
