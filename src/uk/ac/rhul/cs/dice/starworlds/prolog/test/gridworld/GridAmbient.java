package uk.ac.rhul.cs.dice.starworlds.prolog.test.gridworld;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import uk.ac.rhul.cs.dice.starworlds.appearances.PhysicalBodyAppearance;
import uk.ac.rhul.cs.dice.starworlds.entities.ActiveBody;
import uk.ac.rhul.cs.dice.starworlds.entities.PassiveBody;
import uk.ac.rhul.cs.dice.starworlds.entities.PhysicalBody;
import uk.ac.rhul.cs.dice.starworlds.entities.agent.AbstractAutonomousAgent;
import uk.ac.rhul.cs.dice.starworlds.entities.avatar.AbstractAvatarAgent;
import uk.ac.rhul.cs.dice.starworlds.environment.ambient.AbstractAmbient;
import uk.ac.rhul.cs.dice.starworlds.environment.ambient.attribute.Attribute;
import uk.ac.rhul.cs.dice.starworlds.environment.ambient.attribute.key.AttributeKey;

public class GridAmbient extends AbstractAmbient {

	public static final AttributeKey<GridAmbient.Grid> GRIDKEY = new AttributeKey<GridAmbient.Grid>(
			GridAmbient.Grid.class) {
	};

	public static final Integer GRIDSIZE = 10;
	private static final Random random = new Random();

	private Grid grid;

	public GridAmbient(Set<AbstractAutonomousAgent<?>> agents, Set<ActiveBody<?>> activeBodies,
			Set<PassiveBody> passiveBodies, Set<AbstractAvatarAgent<?>> avatars) {
		super(agents, activeBodies, passiveBodies, avatars);
		grid = new Grid(GRIDSIZE);
		this.addAttibute(GRIDKEY, grid);
		this.addFilter(GridAttributeFilter.KEY);
		// grid.placeGoal(new Coordinate(random.nextInt(GRIDSIZE - 2) + 1, random.nextInt(GRIDSIZE - 2) + 1));
		// grid.placeAgents(agents);
		grid.placeAgent((AbstractAutonomousAgent<?>) agents.toArray()[0], new Coordinate(3, 2));
		grid.placeGoal(new Coordinate(6, 3));
	}

	public Grid getGrid() {
		return grid;
	}

	public class Grid implements Attribute {

		private Map<Coordinate, Tile> grid;
		private Map<String, Coordinate> entities;

		private int size = -1;

		public Grid(int size) {
			this.size = size;
			this.grid = new HashMap<>();
			this.entities = new HashMap<>();
			for (int i = 0; i < size; i++) {
				placeWall(new Coordinate(i, 0));
				placeWall(new Coordinate(i, size - 1));
				placeWall(new Coordinate(0, i));
				placeWall(new Coordinate(size - 1, i));
			}
			for (int i = 1; i < size - 1; i++) {
				for (int j = 1; j < size - 1; j++) {
					grid.put(new Coordinate(i, j), new Tile());
				}
			}
		}

		public PhysicalBody updatePosition(String id, Coordinate from, Coordinate to) {
			PhysicalBody replaced = grid.get(to).fill(grid.get(from).empty());
			this.entities.put(id, to);
			if (replaced != null) {
				this.entities.put(replaced.getId(), new Coordinate(-1, -1)); // ???
			}
			return replaced;
		}

		public Tile getTile(Coordinate c) {
			return grid.get(c);
		}

		public Coordinate getCoordinateOf(String id) {
			return entities.get(id);
		}

		public Tile getTileOf(String id) {
			return grid.get(entities.get(id));
		}

		public void placeGoal(Coordinate c) {
			Goal goal = new Goal(c);
			grid.put(c, new Tile(goal));
			entities.put(goal.getId(), c);
			addPassiveBody(goal);
		}

		public void placeWall(Coordinate c) {
			Wall wall = new Wall(c);
			grid.put(c, new Tile(wall));
			entities.put(wall.getId(), c);
			addPassiveBody(wall);
		}

		public void placeAgentsRandomly(Collection<AbstractAutonomousAgent<?>> agents) {
			if ((size * size - 4 * size - 4) > agents.size()) {
				agents.forEach(this::placeAgent);
			}
		}

		private void placeAgent(AbstractAutonomousAgent<?> agent) {
			Coordinate c = new Coordinate(random.nextInt(size - 2) + 1, random.nextInt(size - 2) + 1);
			if (grid.get(c).isEmpty()) {
				placeAgent(agent, c);
			} else {
				placeAgent(agent);
			}
		}

		private void placeAgent(AbstractAutonomousAgent<?> agent, Coordinate c) {
			grid.get(c).entity = agent;
			entities.put(agent.getId(), c);
		}

		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			for (int i = 0; i < size; i++) { // y
				for (int k = 0; k < size * 4 + 1; k++) {
					builder.append("-");
				}
				builder.append(System.lineSeparator());
				builder.append("| ");
				for (int j = 0; j < size - 1; j++) { // /x
					builder.append(this.grid.get(new Coordinate(j, i))).append(" | ");
				}
				builder.append(this.grid.get(new Coordinate(size - 1, i))).append(" |");
				builder.append(System.lineSeparator());
			}

			return builder.toString();
		}

		public class Tile {

			private PhysicalBody entity;

			public Tile(PhysicalBody entity) {
				this.entity = entity;
			}

			public Tile() {
				this.entity = null;
			}

			public boolean isEmpty() {
				return entity == null;
			}

			public boolean isAgent() {
				return entity instanceof AbstractAutonomousAgent<?>;
			}

			public boolean isWall() {
				return entity instanceof Wall;
			}

			public boolean isGoal() {
				return entity instanceof Goal;
			}

			public PhysicalBody empty() {
				PhysicalBody entity = this.entity;
				this.entity = null;
				return entity;
			}

			public PhysicalBody fill(PhysicalBody body) {
				PhysicalBody entity = this.entity;
				this.entity = body;
				return entity;
			}

			public PhysicalBody getEntity() {
				return entity;
			}

			public void setEntity(PhysicalBody entity) {
				this.entity = entity;
			}

			@Override
			public String toString() {
				if (isEmpty()) {
					return " ";
				} else if (isWall()) {
					return "#";
				} else if (isAgent()) {
					return "A";
				} else if (isGoal()) {
					return "G";
				} else
					return "U"; // unknown?
			}
		}

		public class Wall extends GridBody {
			public Wall(Coordinate position) {
				super(position, Wall.class);
			}
		}

		public class Goal extends GridBody {
			public Goal(Coordinate position) {
				super(position, Goal.class);
			}
		}

		public class GridBody extends PassiveBody {

			private Coordinate position;

			public GridBody(Coordinate position, Class<? extends PhysicalBody> cls) {
				super();
				this.position = position;
				this.setAppearance(new GridBodyAppearance(cls));
			}

			public class GridBodyAppearance extends PhysicalBodyAppearance {
				private static final long serialVersionUID = -9216134006760290209L;

				public GridBodyAppearance(Class<? extends PhysicalBody> cls) {
					super(cls);
				}

				public Coordinate getPosition() {
					return new Coordinate(position.x, position.y);
				}
			}
		}
	}

	public static class Coordinate {
		private int x;
		private int y;

		public Coordinate(int x, int y) {
			super();
			this.x = x;
			this.y = y;
		}

		public int getX() {
			return x;
		}

		public void setX(int x) {
			this.x = x;
		}

		public int getY() {
			return y;
		}

		public void setY(int y) {
			this.y = y;
		}

		@Override
		public String toString() {
			return "(" + x + "," + y + ")";
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + x;
			result = prime * result + y;
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Coordinate other = (Coordinate) obj;
			if (x != other.x)
				return false;
			if (y != other.y)
				return false;
			return true;
		}
	}

}
