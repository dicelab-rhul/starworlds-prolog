package uk.ac.rhul.cs.dice.starworlds.prolog.test.gridworld;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import uk.ac.rhul.cs.dice.starworlds.actions.environmental.SenseAction;
import uk.ac.rhul.cs.dice.starworlds.appearances.Appearance;
import uk.ac.rhul.cs.dice.starworlds.entities.PhysicalBody;
import uk.ac.rhul.cs.dice.starworlds.entities.agent.AbstractAutonomousAgent;
import uk.ac.rhul.cs.dice.starworlds.environment.ambient.Ambient;
import uk.ac.rhul.cs.dice.starworlds.environment.ambient.attribute.Attribute;
import uk.ac.rhul.cs.dice.starworlds.environment.ambient.filter.Filter;
import uk.ac.rhul.cs.dice.starworlds.environment.ambient.filter.FilterKey;
import uk.ac.rhul.cs.dice.starworlds.prolog.test.gridworld.GridAmbient.Coordinate;
import uk.ac.rhul.cs.dice.starworlds.prolog.test.gridworld.GridAmbient.Grid;
import uk.ac.rhul.cs.dice.starworlds.prolog.test.gridworld.GridAmbient.Grid.Tile;
import uk.ac.rhul.cs.dice.starworlds.prolog.test.gridworld.GridAttributeFilter.GridView;

public class GridAttributeFilter implements Filter<Grid, GridView> {

	public final static GridKey KEY = new GridKey();

	@Override
	public GridView filter(Grid grid) {
		return null; // unused
	}

	@Override
	public GridView filter(Grid grid, Ambient ambient) {
		return null; // unused
	}

	@Override
	public GridView filter(Grid attribute, Ambient ambient, SenseAction action) {
		AbstractAutonomousAgent<?> agent = ambient.getAgent(action.getActor().getId());
		Coordinate c = attribute.getCoordinateOf(agent.getId());
		Map<Coordinate, Tile> map = new HashMap<>();
		for (int i = c.getX() - 1; i <= c.getX() + 1; i++) {
			for (int j = c.getY() - 1; j <= c.getY() + 1; j++) {
				Coordinate cc = new Coordinate(i, j);
				map.put(cc, attribute.getTile(cc));
			}
		}
		return new GridView(map, c);
	}

	@Override
	public FilterKey<Grid, GridView> getKey() {
		return KEY;
	}

	public static class GridView implements Attribute {

		public Map<Coordinate, Tile> view;
		public Coordinate self;

		public GridView(Map<Coordinate, Tile> view, Coordinate self) {
			this.view = view;
			this.self = self;
		}

		public Collection<Coordinate> getCoordinates() {
			return view.keySet();
		}

		public Appearance get(Coordinate coord) {
			PhysicalBody e = view.get(coord).getEntity();
			if (e != null) {
				return e.getAppearance();
			} else {
				return new EmptyAppearance();
			}
		}

		public Coordinate getSelfCoordinate() {
			return self;
		}

		public class EmptyAppearance implements Appearance {
			private static final long serialVersionUID = 3176252093371148175L;

			@Override
			public String represent() {
				return " ";
			}

			@Override
			public String getId() {
				return " ";
			}

			@Override
			public void setId(String id) {
				// unused
			}
		}
	}

	public static class EmptyAppearance implements Appearance {
		private static final long serialVersionUID = 3176252093371148175L;

		@Override
		public String represent() {
			return " ";
		}

		@Override
		public String getId() {
			return " ";
		}

		@Override
		public void setId(String id) {
			// unused
		}
	}

	public static class GridKey extends FilterKey<Grid, GridView> {

		@Override
		public GridView filter(Filter<Grid, GridView> filter, Grid attribute, Ambient ambient, SenseAction action) {
			return filter.filter(attribute, ambient, action);
		}
	}

}
