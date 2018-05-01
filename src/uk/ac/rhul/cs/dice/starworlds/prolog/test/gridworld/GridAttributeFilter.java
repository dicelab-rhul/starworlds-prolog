package uk.ac.rhul.cs.dice.starworlds.prolog.test.gridworld;

import uk.ac.rhul.cs.dice.starworlds.actions.environmental.SenseAction;
import uk.ac.rhul.cs.dice.starworlds.entities.agent.AbstractAgent;
import uk.ac.rhul.cs.dice.starworlds.environment.ambient.Ambient;
import uk.ac.rhul.cs.dice.starworlds.environment.ambient.filter.Filter;
import uk.ac.rhul.cs.dice.starworlds.environment.ambient.filter.FilterKey;
import uk.ac.rhul.cs.dice.starworlds.prolog.term.Termable;
import uk.ac.rhul.cs.dice.starworlds.prolog.test.gridworld.GridAmbient.Coordinate;
import uk.ac.rhul.cs.dice.starworlds.prolog.test.gridworld.GridAmbient.Grid;
import uk.ac.rhul.cs.dice.starworlds.prolog.test.gridworld.GridAmbient.Tile;

@Termable(name = "gridfilter")
public class GridAttributeFilter implements Filter<Grid, Tile[]> {

	public static GridKey KEY = new GridKey();

	@Override
	public Tile[] filter(Grid grid) {
		return null; // unused
	}

	@Override
	public Tile[] filter(Grid grid, Ambient ambient) {
		return null; // unused
	}

	@Override
	public Tile[] filter(Grid attribute, Ambient ambient, SenseAction action) {
		AbstractAgent<?> agent = ambient.getAgent(action.getActor().getId());
		Coordinate c = attribute.getCoordinateOf(agent.getId());
		Tile[] tiles = new Tile[9];
		int k = 0;
		for (int i = c.getX() - 1; i <= c.getX() + 1; i++) {
			for (int j = c.getY() - 1; j <= c.getY() + 1; j++) {
				tiles[k] = attribute.getTile(new Coordinate(i, j));
				k++;
			}
		}
		return tiles;
	}

	public static class GridKey extends FilterKey<Grid, Tile[]> {
		@Override
		public Tile[] filter(Filter<Grid, Tile[]> filter, Grid attribute, Ambient ambient, SenseAction action) {
			return filter.filter(attribute, ambient, action);
		}
	}

	@Override
	public GridKey getKey() {
		return KEY;
	}

}
