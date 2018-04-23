package uk.ac.rhul.cs.dice.starworlds.prolog.termmap;

import uk.ac.rhul.cs.dice.starworlds.actions.environmental.EnvironmentalAction;
import uk.ac.rhul.cs.dice.starworlds.prolog.termmap.ActionMapper.ActionEntry;
import uk.ac.rhul.cs.dice.starworlds.prolog.termmap.TermFactory.TermMapException;
import uk.ac.rhul.cs.dice.starworlds.prolog.termmap.term.Term;

public abstract class ActionMapper<L, T extends Term<L>, A extends EnvironmentalAction, K> extends
		AbstractTermMapper<L, T, A, ActionEntry<K, A>> {

	public ActionMapper(Class<A> cls, String predicateName) {
		super(cls, predicateName);
	}

	public abstract T toTerm(A action);

	/**
	 * Expected input: componentkey(action(...))
	 */
	public abstract ActionEntry<K, A> fromTerm(T term);

	public static final class ActionEntry<K, A extends EnvironmentalAction> {
		K key;
		A action;

		public ActionEntry(K key, A action) {
			super();
			if (key == null) {
				throw new TermMapException("Key cannot be null");
			}
			if (action == null) {
				throw new TermMapException("Action cannot be null");
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
	}
}
