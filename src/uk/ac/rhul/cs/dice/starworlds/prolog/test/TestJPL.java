package uk.ac.rhul.cs.dice.starworlds.prolog.test;

import java.util.Arrays;

import org.jpl7.Atom;
import org.jpl7.Query;
import org.jpl7.Term;

import uk.ac.rhul.cs.dice.starworlds.prolog.swi.agent.SWIMind;

public class TestJPL {

	// 'c:/Users/Ben/Work/MastersThesis/StarworldsWorkspace/starworlds-prolog/src/uk/ac/rhul/cs/dice/starworlds/prolog/test/Test.pl'

	// 'c:/users/ben/work/mastersthesis/starworldsworkspace/starworlds-prolog/src/uk/ac/rhul/cs/dice/starworlds/prolog/test/test2.pl'
	public static final String TESTFILE1 = "src/uk/ac/rhul/cs/dice/starworlds/prolog/test/TestMind1.pl";
	public static final String TESTFILE2 = "src/uk/ac/rhul/cs/dice/starworlds/prolog/test/TestMind2.pl";
	public static final String MODULEMANAGERFILE = "src/uk/ac/rhul/cs/dice/starworlds/prolog/test/SWIPrologModuleManager.pl"
			.toLowerCase();

	public static void main(String[] args) {
		Query load = new Query("consult", new Atom(MODULEMANAGERFILE));
		load.allSolutions();
		String mindModule1 = SWIMind.declareMind(TESTFILE1);
		String mind1 = newMind(mindModule1);

		Query query = new Query(new StringBuilder(mind1).append(":perceive(").append("P").append(",1)").toString());
		System.out.println(Arrays.toString(query.allSolutions()));
	}

	protected static String newMind(String sourceModule) {
		String query = new StringBuilder("new_mind([perceive/2, decide/2],").append(sourceModule).append(",M)")
				.toString();
		Query q = new Query(query);
		Term module = q.allSolutions()[0].get("M");
		return module.name();
	}
}
