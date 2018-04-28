package uk.ac.rhul.cs.dice.starworlds.prolog.entities.agent.mind;

import java.util.Collection;

import uk.ac.rhul.cs.dice.starworlds.appearances.ActiveBodyAppearance;
import uk.ac.rhul.cs.dice.starworlds.entities.agent.AbstractAgent;
import uk.ac.rhul.cs.dice.starworlds.entities.agent.components.AbstractComponent;
import uk.ac.rhul.cs.dice.starworlds.entities.agent.components.ComponentKey;

public abstract class PrologAgent<T, K extends ComponentKey> extends AbstractAgent<K> {

	public PrologAgent(ActiveBodyAppearance<K> appearance, Collection<AbstractComponent<K>> components,
			PrologMind<T, K> mind) {
		super(appearance, components, mind);
	}

	public PrologAgent(Collection<AbstractComponent<K>> components, PrologMind<T, K> mind) {
		super(components, mind);
	}

}
