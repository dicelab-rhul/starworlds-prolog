package uk.ac.rhul.cs.dice.starworlds.prolog.test.gridworld;

import uk.ac.rhul.cs.dice.starworlds.actions.environmental.PhysicalAction;
import uk.ac.rhul.cs.dice.starworlds.prolog.term.Termable;
import uk.ac.rhul.cs.dice.starworlds.prolog.test.gridworld.GridAmbient.Coordinate;

@Termable(name = "move")
public class GridMoveAction extends PhysicalAction {

	private static final long serialVersionUID = -990069227039913880L;

	@Termable
	private Direction direction;

	public GridMoveAction() {
	}

	public GridMoveAction(Direction direction) {
		this.direction = direction;
	}

	public Direction getDirection() {
		return direction;
	}

	@Override
	public String toString() {
		return new StringBuilder("move").append("<").append(this.direction).append(">").toString();
	}

	@Termable(name = "direction")
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
}
