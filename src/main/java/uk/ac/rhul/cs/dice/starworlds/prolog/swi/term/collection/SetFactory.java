package uk.ac.rhul.cs.dice.starworlds.prolog.swi.term.collection;

import java.util.Set;

import org.jpl7.Term;

public abstract class SetFactory<C extends Set<Object>> extends CollectionFactory<C> {

	@Override
	public Set<?> fromTerm(Term term) {
		return (Set<?>) super.fromTerm(term);
	}

	@Override
	public Set<?> fromTerm(Term term, Class<?>[] generics) {
		return (Set<?>) super.fromTerm(term, generics);
	}

}
