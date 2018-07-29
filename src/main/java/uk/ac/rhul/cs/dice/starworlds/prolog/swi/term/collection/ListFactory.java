package uk.ac.rhul.cs.dice.starworlds.prolog.swi.term.collection;

import java.util.List;

import org.jpl7.Term;

public abstract class ListFactory<C extends List<Object>> extends CollectionFactory<C> {

	@Override
	public List<?> fromTerm(Term term) {
		return (List<?>) super.fromTerm(term);
	}

	@Override
	public List<?> fromTerm(Term term, Class<?>[] generics) {
		return (List<?>) super.fromTerm(term, generics);
	}

}
