package uk.ac.rhul.cs.dice.starworlds.prolog.termmap.term;

public interface Term<T> {

	public String getName();

	public Integer getArity();

	public T getTerm();

}
