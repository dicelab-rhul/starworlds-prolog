package uk.ac.rhul.cs.dice.starworlds.prolog.term;

import org.jpl7.Term;

public interface TermFactory<T> {

	public T toTerm(Object arg);

	public Object fromTerm(T term);

	public T toTerm(Object arg, Class<?>[] typeinfo);

	public Object fromTerm(Term term, Class<?>[] typeinfo);

	public Class<?> getObjectClass();

}
