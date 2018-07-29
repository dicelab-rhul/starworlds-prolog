package uk.ac.rhul.cs.dice.starworlds.prolog.actions;

import uk.ac.rhul.cs.dice.starworlds.actions.environmental.EnvironmentalAction;
import uk.ac.rhul.cs.dice.starworlds.entities.agent.components.ComponentKey;
import uk.ac.rhul.cs.dice.starworlds.prolog.exceptions.PrologTermException;

public final class ActionEntry<K extends ComponentKey, A extends EnvironmentalAction> {

	private K key;
	private A action;

	public ActionEntry(K key, A action) {
		super();
		if (key == null) {
			throw new PrologTermException("Key cannot be null");
		}
		if (action == null) {
			throw new PrologTermException("Action cannot be null");
		}
		this.key = key;
		this.action = action;
	}

	public K getKey() {
		return key;
	}

	public A getAction() {
		return action;
	}

	@Override
	public String toString() {
		return new StringBuilder(key.toString()).append("->").append(action).toString();
	}
}
