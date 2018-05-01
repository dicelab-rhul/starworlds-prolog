package uk.ac.rhul.cs.dice.starworlds.prolog.swi.term.map;

import java.util.Map;

import org.jpl7.Term;

import uk.ac.rhul.cs.dice.starworlds.prolog.exceptions.PrologTermException;
import uk.ac.rhul.cs.dice.starworlds.prolog.utils.SWIUtils.TermVisitor;

public class MapObjectCollector<C extends Map<Object, Object>> implements TermVisitor<C> {

	private C map = null;
	private Class<?> keyGeneric = null;
	private Class<?> valueGeneric = null;
	private MapFactory<C> factory;

	public MapObjectCollector(C map, Class<?> keyGeneric, Class<?> valueGeneric, MapFactory<C> factory) {
		this.map = map;
		this.keyGeneric = keyGeneric;
		this.valueGeneric = valueGeneric;
		this.factory = factory;
	}

	@Override
	public C visit(Term term) {
		add(term);
		return map;
	}

	@Override
	public C visit(Term term, C prev) {
		add(term);
		return map;
	}

	private void add(Term term) {
		Object key = factory.resolveFactory(term.arg(1)).fromTerm(term.arg(1));
		Object value = factory.resolveFactory(term.arg(2)).fromTerm(term.arg(2));
		if (keyGeneric.isAssignableFrom(key.getClass()) && valueGeneric.isAssignableFrom(value.getClass())) {
			map.put(key, value);
		} else {
			throw new PrologTermException("Invalid term: " + term + ", for map with generic types: " + keyGeneric + ","
					+ valueGeneric);
		}

	}
}