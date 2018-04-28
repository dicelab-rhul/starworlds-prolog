package uk.ac.rhul.cs.dice.starworlds.prolog.entities.agent.mind;

import uk.ac.rhul.cs.dice.starworlds.entities.agent.AbstractMind;
import uk.ac.rhul.cs.dice.starworlds.entities.agent.components.ComponentKey;
import uk.ac.rhul.cs.dice.starworlds.exceptions.StarWorldsRuntimeException;
import uk.ac.rhul.cs.dice.starworlds.prolog.actions.PrologEnvironmentalAction;
import uk.ac.rhul.cs.dice.starworlds.prolog.perception.PrologPerception;
import uk.ac.rhul.cs.dice.starworlds.prolog.term.TermFactory;

public abstract class PrologMind<T, K extends ComponentKey> extends AbstractMind<K> {
	private TermFactory<T> factory;

	public PrologMind(TermFactory<T> factory) {
		this.factory = factory;
	}

	public T toTerm(PrologPerception perception) {
		try {
			return factory.toTerm(perception);
		} catch (Exception e) {
			throw new StarWorldsRuntimeException("Failed to convert perception: " + perception + " to term.", e);
		}
	}

	public T toTerm(PrologEnvironmentalAction action) {
		try {
			return factory.toTerm(action);
		} catch (Exception e) {
			throw new StarWorldsRuntimeException("Failed to convert action: " + action + " to term.", e);
		}
	}

	public Object fromTerm(T term) {
		try {
			return factory.fromTerm(term);
		} catch (Exception e) {
			throw new StarWorldsRuntimeException("Failed to convert term: " + term + " to object.", e);
		}
	}
}
