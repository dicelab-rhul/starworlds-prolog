package uk.ac.rhul.cs.dice.starworlds.prolog.entities.agent.mind;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import cli.System.Security.Policy.Evidence;
import uk.ac.rhul.cs.dice.starworlds.actions.environmental.EnvironmentalAction;
import uk.ac.rhul.cs.dice.starworlds.entities.agent.AbstractAgentMind;
import uk.ac.rhul.cs.dice.starworlds.entities.agent.components.concrete.SimpleComponentKey;
import uk.ac.rhul.cs.dice.starworlds.prolog.exceptions.PrologRunTimeException;
import uk.ac.rhul.cs.dice.starworlds.prolog.termmap.ActionMapper.ActionEntry;
import uk.ac.rhul.cs.dice.starworlds.prolog.termmap.TermFactory;
import uk.ac.rhul.cs.dice.starworlds.utils.StringUtils;
import alice.tuprolog.InvalidTheoryException;
import alice.tuprolog.Long;
import alice.tuprolog.NoSolutionException;
import alice.tuprolog.Prolog;
import alice.tuprolog.SolveInfo;
import alice.tuprolog.Struct;
import alice.tuprolog.Term;
import alice.tuprolog.Theory;
import alice.tuprolog.UnknownVarException;
import alice.tuprolog.Var;

public class PrologAgentMind<K> extends AbstractAgentMind<K> {

	protected Map<K, EnvironmentalAction> actions = new HashMap<>();
	protected Prolog engine = new Prolog();
	protected TermFactory<K> termFactory = new TermFactory<>();

	// protected Class<K> keyClass;

	public PrologAgentMind(String mindPath) throws InvalidTheoryException, IOException {
		File mindFile = new File(mindPath);
		if (mindFile.exists()) {
			init(new FileInputStream(mindFile));
		} else {
			throw new FileNotFoundException("Prolog Mind not found: " + mindFile);
		}
	}

	public PrologAgentMind(InputStream mindInputStream) throws InvalidTheoryException, IOException {
		init(mindInputStream);
	}

	protected void init(InputStream mindInputStream) throws InvalidTheoryException, IOException {
		engine.setTheory(new Theory(mindInputStream));
	}

	@Override
	public void perceive() {
		// get perceptions
		Struct perceptions = new Struct(this.getBody().perceive().stream().map(termFactory::toTerm)
				.toArray(Term[]::new));
		System.out.println(perceptions);
		Struct perceive = new Struct("perceive", perceptions, getTime());
		SolveInfo info = this.engine.solve(perceive);
		if (!info.isSuccess()) {
			throw new PrologRunTimeException("Invalid PrologMind, check code for errors, could not process: "
					+ perceive);
		}
	}

	@Override
	public void decide() {
		System.out.println("Agent: " + this.getId());
		Var actions = new Var("A");
		Struct decide = new Struct("decide", actions, getTime());
		SolveInfo solution;
		try {
			solution = this.engine.solve(decide);
			solve(solution, actions);
			// what about possible other solutions?
			// while (this.engine.hasOpenAlternatives()) {
			// solution = this.engine.solve(decide);
			// solve(solution, actions);
			// }
		} catch (NoSolutionException | UnknownVarException e) {
			e.printStackTrace();
		}
		System.out.println("Agent: " + this.getId());
	}

	protected void solve(SolveInfo solution, Var var) throws NoSolutionException, UnknownVarException {
		Term t = solution.getTerm(var.getName());
		if (!t.isEmptyList()) {
			Struct sol = (Struct) t;
			if (sol.isList()) {
				Iterator<? extends Term> iter = sol.listIterator();
				while (iter.hasNext()) {
					addEntry(iter.next());
				}
			} else {
				addEntry(sol);
			}
		}
	}

	protected void addEntry(Term action) {
		System.out.println("  " + action);
		ActionEntry<K, ? extends EnvironmentalAction> entry = termFactory.actionFromTerm(action);
		this.actions.put(entry.getKey(), entry.getAction());
	}

	@Override
	public void execute() {
		// do we want to do something in prolog here?
		System.out.println(StringUtils.mapToString(actions));
		this.getBody().attempt(actions);
		this.actions.clear();
	}

	protected Long getTime() {
		return new Long(this.getBody().getEnvironment().getPhysics().getTime());
	}
}
