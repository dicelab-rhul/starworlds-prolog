package uk.ac.rhul.cs.dice.starworlds.prolog.swi.term.collection.concrete;

import java.util.HashSet;

import uk.ac.rhul.cs.dice.starworlds.prolog.swi.term.collection.SetFactory;

public class HashSetFactory extends SetFactory<HashSet<Object>> {

	@Override
	public Class<?> getObjectClass() {
		return HashSet.class;
	}

	@Override
	public HashSet<Object> collection() {
		return new HashSet<Object>();
	}
}
