package uk.ac.rhul.cs.dice.starworlds.prolog.entities.agent.mind;

import java.util.Collection;

import uk.ac.rhul.cs.dice.starworlds.appearances.ActiveBodyAppearance;
import uk.ac.rhul.cs.dice.starworlds.entities.agent.AbstractAgent;
import uk.ac.rhul.cs.dice.starworlds.entities.agent.components.Component;
import uk.ac.rhul.cs.dice.starworlds.entities.agent.components.ComponentKey;

public abstract class PrologAgent<T, K extends ComponentKey> extends AbstractAgent<K> {

	public PrologAgent(String id, ActiveBodyAppearance appearance, Collection<Component<K>> components,
			PrologMind<T, K> mind) {
		super(id, appearance, components, mind);
	}

	public PrologAgent(String id, Collection<Component<K>> components, PrologMind<T, K> mind) {
		super(id, components, mind);
	}

}
