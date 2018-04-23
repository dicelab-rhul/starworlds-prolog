package uk.ac.rhul.cs.dice.starworlds.prolog.entities.agent.mind;

import java.io.File;
import java.util.Map;

import org.jpl7.Atom;
import org.jpl7.Query;
import org.jpl7.Term;

import uk.ac.rhul.cs.dice.starworlds.actions.environmental.EnvironmentalAction;
import uk.ac.rhul.cs.dice.starworlds.prolog.exceptions.PrologRunTimeException;
import uk.ac.rhul.cs.dice.starworlds.prolog.termmap.ActionMapper.ActionEntry;
import uk.ac.rhul.cs.dice.starworlds.prolog.termmap.term.SWITerm;

public class SWIPrologAgentMind<K> extends PrologAgentMind<SWITerm, Term, K> {

	public static String MODULEMANAGERFILE = "src/uk/ac/rhul/cs/dice/starworlds/prolog/test/SWIPrologModuleManager.pl"
			.toLowerCase();

	static {
		new Query("consult", new Atom(MODULEMANAGERFILE)).allSolutions();
	}

	protected String module;

	public SWIPrologAgentMind(String mindModule) {
		this.module = this.newMind(mindModule);
	}

	@Override
	public void perceive() {
		StringBuilder builder = new StringBuilder(module).append(":perceive([");
		this.getBody().perceive().stream().map(this::toTerm).forEach(x -> builder.append(x.toString()));
		builder.append("],").append(getTime()).append(")");
		// System.out.println("PERCEIVE: " + builder.toString());
		Query perceive = new Query(builder.toString());
		perceive.allSolutions();
		// System.out.println(Arrays.toString(perceive.allSolutions()));
	}

	@Override
	public void decide() {
		StringBuilder builder = new StringBuilder(module).append(":decide(Actions,").append(getTime()).append(")");
		Query decide = new Query(builder.toString());
		Map<String, Term>[] solutions = decide.allSolutions();
		// what to do about multiple solutions?
		Term actions = solutions[0].get("Actions");
		for (Term t : actions.toTermArray()) {
			ActionEntry<K, ? extends EnvironmentalAction> entry = this.actionFromTerm(t);
			this.actions.put(entry.getKey(), entry.getAction());
		}
	}

	@Override
	public void execute() {
		super.execute();
	}

	protected String newMind(String sourceModule) {
		String query = new StringBuilder("new_mind(").append(getVisiblePredicates()).append(",").append(sourceModule)
				.append(",M)").toString();
		Query q = new Query(query);
		Term module = q.allSolutions()[0].get("M");
		System.out.println("Successfully created a SWIProlog mind from source module: " + sourceModule
				+ ", mind module: " + module);
		return module.name();
	}

	protected String getVisiblePredicates() {
		return "[perceive/2, decide/2]";
	}

	@Override
	public SWITerm wrapTerm(Term term) {
		return new SWITerm(term);
	}

	public static String declareMind(String sourcePath) {
		String source = sourcePath;
		File file = new File(sourcePath);
		if (file.exists()) {
			if (!file.isAbsolute()) {
				source = file.getAbsolutePath();
			}
			source = source.toLowerCase().replace('\\', '/');
			String query = new StringBuilder("declare_mind('").append(source).append("',M)").toString();
			Query q = new Query(query);
			Term module = q.allSolutions()[0].get("M");
			System.out.println("Successfully added mind module: " + module.name() + " from file: "
					+ System.lineSeparator() + "    " + source);
			return module.name();
		}
		throw new PrologRunTimeException("Failed to declare new mind, file: " + source + " not found.");
	}
}
