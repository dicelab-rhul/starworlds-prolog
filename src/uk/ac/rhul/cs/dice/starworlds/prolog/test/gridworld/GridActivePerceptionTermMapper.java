package uk.ac.rhul.cs.dice.starworlds.prolog.test.gridworld;

import uk.ac.rhul.cs.dice.starworlds.perception.ActivePerception;
import uk.ac.rhul.cs.dice.starworlds.prolog.termmap.PerceptionMapper;
import uk.ac.rhul.cs.dice.starworlds.prolog.termmap.TermFactory.TermMapException;
import uk.ac.rhul.cs.dice.starworlds.prolog.test.gridworld.GridAmbient.Coordinate;
import uk.ac.rhul.cs.dice.starworlds.prolog.test.gridworld.GridAmbient.Grid.Tile;
import uk.ac.rhul.cs.dice.starworlds.prolog.test.gridworld.GridAttributeFilter.GridView;
import alice.tuprolog.Int;
import alice.tuprolog.Struct;
import alice.tuprolog.Term;

public class GridActivePerceptionTermMapper extends PerceptionMapper<ActivePerception> {

	public final static String NAME = "gridpercept";

	public GridActivePerceptionTermMapper() {
		super(ActivePerception.class, NAME);
	}

	@Override
	public Term toTerm(ActivePerception perception) {
		GridView view = (GridView) perception.getContent();
		Term[] percepts = view.getCoordinates().stream().map(c -> getPerceptStruct(view, c)).toArray(Term[]::new);
		return new Struct("gridpercept", getSelfStruct(view), new Struct(percepts));
	}

	private Struct getSelfStruct(GridView view) {
		return new Struct("tile", new Struct("c", new Int(view.self.getX()), new Int(view.self.getY())), new Struct(
				"self"));
	}

	private Struct getPerceptStruct(GridView view, Coordinate c) {
		return new Struct("tile", new Struct("c", new Int(c.getX()), new Int(c.getY())),
				representTile(view.view.get(c)));
	}

	private Struct representTile(Tile tile) {
		if (tile.isEmpty()) {
			return new Struct("empty");
		} else if (tile.isAgent()) {
			return new Struct("agent", new Int(Integer.valueOf(tile.getEntity().getId())));
		} else if (tile.isWall()) {
			return new Struct("wall");
		} else if (tile.isGoal()) {
			return new Struct("goal");
		}
		throw new TermMapException("Failed to map tile: " + tile);
	}

	@Override
	public ActivePerception fromTerm(Term term) {
		// TODO Auto-generated method stub
		return null;
	}

}
