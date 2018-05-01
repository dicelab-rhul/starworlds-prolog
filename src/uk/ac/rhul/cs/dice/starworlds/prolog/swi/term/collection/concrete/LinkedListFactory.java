package uk.ac.rhul.cs.dice.starworlds.prolog.swi.term.collection.concrete;

import java.util.LinkedList;

import uk.ac.rhul.cs.dice.starworlds.prolog.swi.term.collection.ListFactory;

public class LinkedListFactory extends ListFactory<LinkedList<Object>> {

	@Override
	public Class<?> getObjectClass() {
		return LinkedList.class;
	}

	@Override
	public LinkedList<Object> collection() {
		return new LinkedList<>();
	}

}
