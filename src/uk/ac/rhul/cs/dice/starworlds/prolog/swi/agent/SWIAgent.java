package uk.ac.rhul.cs.dice.starworlds.prolog.swi.agent;

import java.util.Collection;

import org.jpl7.Term;

import uk.ac.rhul.cs.dice.starworlds.appearances.ActiveBodyAppearance;
import uk.ac.rhul.cs.dice.starworlds.entities.agent.components.AbstractComponent;
import uk.ac.rhul.cs.dice.starworlds.entities.agent.components.ComponentKey;
import uk.ac.rhul.cs.dice.starworlds.prolog.entities.agent.mind.PrologAgent;
import uk.ac.rhul.cs.dice.starworlds.prolog.entities.agent.mind.PrologMind;

public class SWIAgent<K extends ComponentKey> extends PrologAgent<Term, K> {

	public SWIAgent(ActiveBodyAppearance<K> appearance, Collection<AbstractComponent<K>> components,
			PrologMind<Term, K> mind) {
		super(appearance, components, mind);
	}

	public SWIAgent(Collection<AbstractComponent<K>> components, PrologMind<Term, K> mind) {
		super(components, mind);
	}
}
