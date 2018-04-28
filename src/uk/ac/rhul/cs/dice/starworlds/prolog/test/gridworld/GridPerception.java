package uk.ac.rhul.cs.dice.starworlds.prolog.test.gridworld;

import uk.ac.rhul.cs.dice.starworlds.appearances.Appearance;
import uk.ac.rhul.cs.dice.starworlds.perception.ActivePerception;
import uk.ac.rhul.cs.dice.starworlds.perception.factory.PerceptionFactory;
import uk.ac.rhul.cs.dice.starworlds.prolog.term.Termable;
import uk.ac.rhul.cs.dice.starworlds.prolog.test.gridworld.GridAmbient.Tile;

@Termable(name = GridPerception.TERMNAME)
public class GridPerception extends ActivePerception {

	public static final String TERMNAME = "gridpercept";

	@Termable
	private Tile[] view;

	public GridPerception(Tile[] view) {
		super();
		this.view = view;
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

	public static class GridPerceptionFactory implements PerceptionFactory<GridPerception> {
		@Override
		public GridPerception getPerception(Object... args) {
			return new GridPerception((Tile[]) args[0]);
		}
	}
}
