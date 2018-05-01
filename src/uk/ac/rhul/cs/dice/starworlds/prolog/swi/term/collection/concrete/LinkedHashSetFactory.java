package uk.ac.rhul.cs.dice.starworlds.prolog.swi.term.collection.concrete;

import java.util.LinkedHashSet;

import uk.ac.rhul.cs.dice.starworlds.prolog.swi.term.collection.SetFactory;

public class LinkedHashSetFactory extends SetFactory<LinkedHashSet<Object>> {

	@Override
	public Class<?> getObjectClass() {
		return LinkedHashSet.class;
	}

	@Override
	public LinkedHashSet<Object> collection() {
		return new LinkedHashSet<Object>();
	}

}
