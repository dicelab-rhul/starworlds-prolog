package uk.ac.rhul.cs.dice.starworlds.prolog.term;

public interface TermFactory<T> {

	public T toTerm(Object arg) throws Exception;

	public Object fromTerm(T term) throws Exception;

}
