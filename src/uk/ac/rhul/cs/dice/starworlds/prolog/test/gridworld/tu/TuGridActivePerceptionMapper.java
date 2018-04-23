package uk.ac.rhul.cs.dice.starworlds.prolog.test.gridworld.tu;

import uk.ac.rhul.cs.dice.starworlds.perception.ActivePerception;
import uk.ac.rhul.cs.dice.starworlds.prolog.termmap.PerceptionMapper;
import uk.ac.rhul.cs.dice.starworlds.prolog.termmap.TermFactory.TermMapException;
import uk.ac.rhul.cs.dice.starworlds.prolog.termmap.term.TuTerm;
import uk.ac.rhul.cs.dice.starworlds.prolog.test.gridworld.GridAmbient.Coordinate;
import uk.ac.rhul.cs.dice.starworlds.prolog.test.gridworld.GridAmbient.Grid.Tile;
import uk.ac.rhul.cs.dice.starworlds.prolog.test.gridworld.GridAttributeFilter.GridView;
import alice.tuprolog.Int;
import alice.tuprolog.Struct;
import alice.tuprolog.Term;

public class TuGridActivePerceptionMapper extends PerceptionMapper<Struct, TuTerm, ActivePerception> {

	public final static String NAME = "gridpercept";

	public TuGridActivePerceptionMapper() {
		super(ActivePerception.class, NAME);
	}

	@Override
	public TuTerm toTerm(ActivePerception perception) {
		GridView view = (GridView) perception.getContent();
		Term[] percepts = view.getCoordinates().stream().map(c -> getPerceptStruct(view, c)).toArray(Term[]::new);
		return new TuTerm(new Struct("gridpercept", new Struct(percepts)));
	}

	// maybe dont need this?
	// private Struct getSelfStruct(GridView view) {
	// return new Struct("tile", new Struct("c", new Int(view.self.getX()), new Int(view.self.getY())), new Struct(
	// "self"));
	// }

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
	public ActivePerception fromTerm(TuTerm term) {
		// TODO
		return null;
	}

}
