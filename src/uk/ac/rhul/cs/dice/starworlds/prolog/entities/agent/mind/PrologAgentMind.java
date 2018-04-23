package uk.ac.rhul.cs.dice.starworlds.prolog.entities.agent.mind;

import java.util.HashMap;
import java.util.Map;

import uk.ac.rhul.cs.dice.starworlds.actions.environmental.EnvironmentalAction;
import uk.ac.rhul.cs.dice.starworlds.entities.agent.AbstractAgentMind;
import uk.ac.rhul.cs.dice.starworlds.perception.Perception;
import uk.ac.rhul.cs.dice.starworlds.prolog.termmap.ActionMapper.ActionEntry;
import uk.ac.rhul.cs.dice.starworlds.prolog.termmap.TermFactory;
import uk.ac.rhul.cs.dice.starworlds.utils.StringUtils;

public abstract class PrologAgentMind<T extends uk.ac.rhul.cs.dice.starworlds.prolog.termmap.term.Term<R>, R, K>
		extends AbstractAgentMind<K> {

	protected Map<K, EnvironmentalAction> actions = new HashMap<>();
	protected TermFactory<R, T, K> termFactory = new TermFactory<>();

	public R toTerm(Perception percept) {
		return termFactory.toTerm(percept).getTerm();
	}

	public R toTerm(EnvironmentalAction action) {
		return termFactory.toTerm(action).getTerm();
	}

	public Perception perceptionFromTerm(T term) {
		return termFactory.perceptionFromTerm(term);
	}

	public ActionEntry<K, ? extends EnvironmentalAction> actionFromTerm(R term) {
		return termFactory.actionFromTerm(wrapTerm(term));
	}

	public abstract T wrapTerm(R term);

	protected Long getTime() {
		return this.getBody().getEnvironment().getPhysics().getTime();
	}

	@Override
	public void execute() {
		// do we want to do something in prolog here?
		System.out.println(StringUtils.mapToString(actions));
		this.getBody().attempt(actions);
		this.actions.clear();
	}
}
