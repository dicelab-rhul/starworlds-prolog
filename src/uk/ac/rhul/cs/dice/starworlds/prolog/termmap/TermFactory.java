package uk.ac.rhul.cs.dice.starworlds.prolog.termmap;

import java.util.HashMap;
import java.util.Map;

import uk.ac.rhul.cs.dice.starworlds.actions.environmental.EnvironmentalAction;
import uk.ac.rhul.cs.dice.starworlds.exceptions.StarWorldsRuntimeException;
import uk.ac.rhul.cs.dice.starworlds.perception.Perception;
import uk.ac.rhul.cs.dice.starworlds.prolog.termmap.ActionMapper.ActionEntry;
import uk.ac.rhul.cs.dice.starworlds.prolog.termmap.term.Term;

public class TermFactory<L, T extends Term<L>, K> {

	private Map<Class<?>, PerceptionTermFactoryMapper<?>> p2t;
	private Map<String, PerceptionTermFactoryMapper<?>> t2p;

	private Map<Class<?>, ActionTermFactoryMapper<?>> a2t;
	private Map<String, ActionTermFactoryMapper<?>> t2a;

	public TermFactory() {
		p2t = new HashMap<>();
		a2t = new HashMap<>();
		t2p = new HashMap<>();
		t2a = new HashMap<>();
	}

	public T toTerm(Perception percept) {
		try {
			return p2t.get(percept.getClass()).toTerm(percept);
		} catch (Exception e) {
			throw new TermMapException("No mapping exists for perception: " + percept, e);
		}
	}

	public T toTerm(EnvironmentalAction action) {
		try {
			return a2t.get(action.getClass()).toTerm(action);
		} catch (Exception e) {
			throw new TermMapException("No mapping exists for action: " + action, e);
		}
	}

	public ActionEntry<K, ? extends EnvironmentalAction> actionFromTerm(T term) {
		ActionTermFactoryMapper<?> mapper = t2a.get(term.getName());
		if (mapper != null) {
			return mapper.fromTerm(term);
		}
		throw new TermMapException("No mapper exists for term: " + term + " with name: " + term.getName());
	}

	public Perception perceptionFromTerm(T term) {
		PerceptionTermFactoryMapper<?> mapper = t2p.get(term.getName());
		if (mapper != null) {
			return (Perception) mapper.fromTerm(term);
		}
		throw new TermMapException("No mapper exists for term: " + term);
	}

	public <P extends Perception> void addMapper(PerceptionMapper<L, T, P> mapper) {
		PerceptionTermFactoryMapper<P> tmapper = new PerceptionTermFactoryMapper<>(mapper.getMappingClass(), mapper);
		this.p2t.put(mapper.getMappingClass(), tmapper);
		this.t2p.put(mapper.getPredicateName(), tmapper);
	}

	public <P extends EnvironmentalAction> void addMapper(ActionMapper<L, T, P, K> mapper) {
		ActionTermFactoryMapper<P> tmapper = this.new ActionTermFactoryMapper<>(mapper.getMappingClass(), mapper);
		this.a2t.put(mapper.getMappingClass(), tmapper);
		this.t2a.put(mapper.getPredicateName(), tmapper);
	}

	private final class ActionTermFactoryMapper<P extends EnvironmentalAction> {
		private Class<P> cls;
		private AbstractTermMapper<L, T, P, ActionEntry<K, P>> mapper;

		public ActionTermFactoryMapper(Class<P> cls, AbstractTermMapper<L, T, P, ActionEntry<K, P>> mapper) {
			this.cls = cls;
			this.mapper = mapper;
		}

		public T toTerm(EnvironmentalAction action) {
			return mapper.toTerm(cls.cast(action));
		}

		public ActionEntry<K, P> fromTerm(T term) {
			return mapper.fromTerm(term);
		}
	}

	private final class PerceptionTermFactoryMapper<P extends Perception> {
		private Class<P> cls;
		private AbstractTermMapper<L, T, P, P> mapper;

		public PerceptionTermFactoryMapper(Class<P> cls, AbstractTermMapper<L, T, P, P> mapper) {
			this.cls = cls;
			this.mapper = mapper;
		}

		public T toTerm(Perception percept) {
			return mapper.toTerm(cls.cast(percept));
		}

		public P fromTerm(T term) {
			return mapper.fromTerm(term);
		}
	}

	public static class TermMapException extends StarWorldsRuntimeException {

		private static final long serialVersionUID = -6041722142538507078L;

		public TermMapException() {
			super();
		}

		public TermMapException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
			super(message, cause, enableSuppression, writableStackTrace);
		}

		public TermMapException(String message, Throwable cause) {
			super(message, cause);
		}

		public TermMapException(String message) {
			super(message);
		}

		public TermMapException(Throwable cause) {
			super(cause);
		}

	}

}
