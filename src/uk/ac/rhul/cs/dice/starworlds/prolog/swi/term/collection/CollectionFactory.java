package uk.ac.rhul.cs.dice.starworlds.prolog.swi.term.collection;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.jpl7.Atom;
import org.jpl7.Compound;
import org.jpl7.Term;

import uk.ac.rhul.cs.dice.starworlds.prolog.swi.term.SWITermFactory;
import uk.ac.rhul.cs.dice.starworlds.prolog.swi.term.collection.collector.ObjectCollector;
import uk.ac.rhul.cs.dice.starworlds.prolog.term.TermFactory;
import uk.ac.rhul.cs.dice.starworlds.prolog.utils.SWIUtils;
import uk.ac.rhul.cs.dice.starworlds.prolog.utils.SWIUtils.TermVisitor;

public abstract class CollectionFactory<C extends Collection<Object>> extends SWITermFactory {

	private Map<Object, SWITermFactory> cache = new HashMap<>();

	// private SWITermFactory primitivecache; // TODO

	public abstract C collection();

	@Override
	public Object fromTerm(Term term, Class<?>[] generics) {
		ObjectCollector<C> collector = new ObjectCollector<>(collection(), generics[0], this);
		return this.visitList(term, collector);
	}

	@Override
	public Object fromTerm(Term term) {
		ObjectCollector<C> collector = new ObjectCollector<>(collection(), Object.class, this);
		return this.visitList(term, collector);
	}

	@Override
	public Term toTerm(Object arg) {
		Collection<?> collection = (Collection<?>) arg;
		return this.toList(collection.stream().map(this::resolveToTerm).collect(Collectors.toList()));
	}

	private Term resolveToTerm(Object arg) {
		return resolveFactory(arg.getClass()).toTerm(arg);
	}

	@Override
	public Term toTerm(Object arg, Class<?>[] typeinfo) {
		return toTerm(arg);
	}

	@Override
	public TermFactory<Term> resolveFactory(Class<?> cls) {
		if (cache.containsKey(cls)) {
			return cache.get(cls);
		} else {
			return super.resolveFactory(cls);
		}
	}

	@Override
	public TermFactory<Term> resolveFactory(Term term) {
		// this could be made more efficient, if one term is compound, then all of them are
		if (term instanceof Compound) {
			String name = term.name();
			if (cache.containsKey(name)) {
				return cache.get(name);
			} else {
				return super.resolveFactory(name);
			}
		} else {
			Class<?> cls = term.getClass();
			if (cache.containsKey(cls)) {
				return cache.get(cls);
			} else {
				return super.resolveFactory(cls);
			}
		}
	}

	public Term toList(List<Term> terms) {
		Term list = new Atom(SWIUtils.EMPTYARRAY);
		for (int i = terms.size() - 1; i >= 0; --i) {
			list = new Compound(SWIUtils.ARRAYDELIM, new Term[] { terms.get(i), list });
		}
		return list;
	}

	public <V> V visitList(Term term, TermVisitor<V> vistor) {
		if (term == null || term.name().equals(SWIUtils.EMPTYARRAY)) {
			return null;
		}
		V v = vistor.visit(term.arg(1));
		Term next = term.arg(2);
		while (!next.name().equals(SWIUtils.EMPTYARRAY)) {
			v = vistor.visit(next.arg(1), v);
			next = next.arg(2);
		}
		return v;
	}

	// TODO
	// public Class<?> verifyTypes(Term term) {
	// return null;
	// }
	//
	// private class TypeVerifyVisitor implements TermVisitor<Class<?>> {
	//
	// @Override
	// public Class<?> visit(Term term) {
	// return GLOBAL.resolveClass(term);
	// }
	//
	// @Override
	// public Class<?> visit(Term term, Class<?> prev) {
	// Class<?> cls = GLOBAL.resolveClass(term);
	// return null;
	// }
	// }
}
