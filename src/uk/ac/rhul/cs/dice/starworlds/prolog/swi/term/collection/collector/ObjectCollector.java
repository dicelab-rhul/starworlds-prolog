package uk.ac.rhul.cs.dice.starworlds.prolog.swi.term.collection.collector;

import java.util.Collection;

import org.jpl7.Term;

import uk.ac.rhul.cs.dice.starworlds.prolog.exceptions.PrologTermException;
import uk.ac.rhul.cs.dice.starworlds.prolog.swi.term.collection.CollectionFactory;
import uk.ac.rhul.cs.dice.starworlds.prolog.utils.SWIUtils.TermVisitor;

public class ObjectCollector<C extends Collection<Object>> implements TermVisitor<C> {

	private C collection = null;
	private Class<?> generic = null;
	private CollectionFactory<C> factory;

	public ObjectCollector(C collection, Class<?> generic, CollectionFactory<C> factory) {
		this.collection = collection;
		this.generic = generic;
		this.factory = factory;
	}

	@Override
	public C visit(Term term) {
		add(term);
		return collection;
	}

	@Override
	public C visit(Term term, C prev) {
		add(term);
		return collection;
	}

	private void add(Term term) {
		Object obj = factory.resolveFactory(term).fromTerm(term);
		if (generic.isAssignableFrom(obj.getClass())) {
			collection.add(obj);
		} else {
			throw new PrologTermException("Invalid term: " + term + ", for collection with generic type: " + generic);
		}
	}
}