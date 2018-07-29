package uk.ac.rhul.cs.dice.starworlds.prolog.entities.agent.mind;

import uk.ac.rhul.cs.dice.starworlds.actions.environmental.EnvironmentalAction;
import uk.ac.rhul.cs.dice.starworlds.entities.agent.AbstractMind;
import uk.ac.rhul.cs.dice.starworlds.entities.agent.components.ComponentKey;
import uk.ac.rhul.cs.dice.starworlds.exceptions.StarWorldsRuntimeException;
import uk.ac.rhul.cs.dice.starworlds.perception.Perception;
import uk.ac.rhul.cs.dice.starworlds.prolog.term.TermFactory;

public abstract class PrologMind<T, K extends ComponentKey> extends AbstractMind<K> {

	private TermFactory<T> factory;
	private static final String FORGOTFACTORY = System.lineSeparator() + " did you forget to declare a term factory?";

	public PrologMind(TermFactory<T> factory) {
		this.factory = factory;
	}

	public T toTerm(Perception perception) {
		try {
			return factory.toTerm(perception);
		} catch (Exception e) {
			throw new StarWorldsRuntimeException("Failed to convert perception: " + perception + " to term."
					+ FORGOTFACTORY, e);
		}
	}

	public T toTerm(EnvironmentalAction action) {
		try {
			return factory.toTerm(action);
		} catch (Exception e) {
			throw new StarWorldsRuntimeException("Failed to convert action: " + action + " to term." + FORGOTFACTORY, e);
		}
	}

	public Object fromTerm(T term) {
		try {
			return factory.fromTerm(term);
		} catch (Exception e) {
			throw new StarWorldsRuntimeException("Failed to convert term: " + term + " to object." + FORGOTFACTORY, e);
		}
	}
}
