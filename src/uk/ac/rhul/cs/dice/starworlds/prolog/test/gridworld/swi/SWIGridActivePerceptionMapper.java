package uk.ac.rhul.cs.dice.starworlds.prolog.test.gridworld.swi;

import org.jpl7.Atom;
import org.jpl7.Compound;
import org.jpl7.Integer;
import org.jpl7.Term;

import uk.ac.rhul.cs.dice.starworlds.perception.ActivePerception;
import uk.ac.rhul.cs.dice.starworlds.prolog.termmap.PerceptionMapper;
import uk.ac.rhul.cs.dice.starworlds.prolog.termmap.TermFactory.TermMapException;
import uk.ac.rhul.cs.dice.starworlds.prolog.termmap.term.SWITerm;
import uk.ac.rhul.cs.dice.starworlds.prolog.test.gridworld.GridAmbient.Coordinate;
import uk.ac.rhul.cs.dice.starworlds.prolog.test.gridworld.GridAmbient.Grid.Tile;
import uk.ac.rhul.cs.dice.starworlds.prolog.test.gridworld.GridAttributeFilter.GridView;

public class SWIGridActivePerceptionMapper extends PerceptionMapper<Term, SWITerm, ActivePerception> {

	public final static String NAME = "gridpercept";

	public SWIGridActivePerceptionMapper() {
		super(ActivePerception.class, NAME);
	}

	@Override
	public SWITerm toTerm(ActivePerception perception) {
		GridView view = (GridView) perception.getContent();
		Term[] percepts = view.getCoordinates().stream().map(c -> getPercept(view, c)).toArray(Term[]::new);
		return new SWITerm(new Compound("gridpercept", percepts));
	}

	// maybe we dont need this?
	// private Compound getSelf(GridView view) {
	// return new Compound("tile", new Term[] { new Compound("c", new Term[] { new Integer(view.self.getX()),
	// new Integer(view.self.getX()), new Atom("self") }) });
	// }

	private Compound getPercept(GridView view, Coordinate c) {
		return new Compound("tile", new Term[] { new Compound("c", new Term[] { new Integer(c.getX()),
				new Integer(c.getX()), representTile(view.view.get(c)) }) });
	}

	private Term representTile(Tile tile) {
		if (tile.isEmpty()) {
			return new Atom("empty");
		} else if (tile.isAgent()) {
			return new Compound("agent", new Term[] { new Integer(Long.valueOf(tile.getEntity().getId())) });
		} else if (tile.isWall()) {
			return new Atom("wall");
		} else if (tile.isGoal()) {
			return new Atom("goal");
		}
		throw new TermMapException("Failed to map tile: " + tile);
	}

	@Override
	public ActivePerception fromTerm(SWITerm term) {
		// TODO
		return null;
	}

}
