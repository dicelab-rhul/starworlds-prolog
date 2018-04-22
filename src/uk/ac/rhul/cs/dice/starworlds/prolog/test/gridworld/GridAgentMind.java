package uk.ac.rhul.cs.dice.starworlds.prolog.test.gridworld;

import java.io.IOException;

import uk.ac.rhul.cs.dice.starworlds.actions.environmental.SenseAction;
import uk.ac.rhul.cs.dice.starworlds.entities.agent.components.concrete.SimpleComponentKey;
import uk.ac.rhul.cs.dice.starworlds.environment.ambient.filter.FilterChain;
import uk.ac.rhul.cs.dice.starworlds.prolog.entities.agent.mind.PrologAgentMind;
import uk.ac.rhul.cs.dice.starworlds.prolog.test.gridworld.GridAmbient.Grid;
import uk.ac.rhul.cs.dice.starworlds.prolog.test.gridworld.GridAttributeFilter.GridView;
import alice.tuprolog.InvalidTheoryException;
import alice.tuprolog.NoMoreSolutionException;
import alice.tuprolog.Struct;
import alice.tuprolog.Var;

public class GridAgentMind extends PrologAgentMind<SimpleComponentKey> {

	public GridAgentMind(String mindpath) throws InvalidTheoryException, IOException {
		super(mindpath);
		this.termFactory.addMapper(new GridActivePerceptionTermMapper());
		this.termFactory.addMapper(new GridActionTermMapper());
	}

	@Override
	public void perceive() {
		super.perceive();
		printKnowledgeBase();
	}

	public void printKnowledgeBase() {
		System.out.println("GOAL: " + this.engine.solve(new Struct("g", new Var("Tile"), new Var("Time"))));
		System.out.println("SELF: " + this.engine.solve(new Struct("s", new Var("Tile"), new Var("Time"))));
		System.out.println("OTHER: ");
		System.out.println(this.engine.solve(new Struct("t", new Var("Tile"), new Var("Time"))));
		while (this.engine.hasOpenAlternatives()) {
			try {
				System.out.println(this.engine.solveNext());
			} catch (NoMoreSolutionException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void execute() {
		FilterChain<Grid, GridView> chain = FilterChain.start(GridAmbient.GRIDKEY).append(new GridAttributeFilter());
		actions.put(SimpleComponentKey.ACTIVESENSOR, new SenseAction(chain));
		super.execute();

	}
}
