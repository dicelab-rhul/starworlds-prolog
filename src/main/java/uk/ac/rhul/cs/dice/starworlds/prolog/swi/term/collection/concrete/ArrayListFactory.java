package uk.ac.rhul.cs.dice.starworlds.prolog.swi.term.collection.concrete;

import java.util.ArrayList;

import uk.ac.rhul.cs.dice.starworlds.prolog.swi.term.collection.ListFactory;

public class ArrayListFactory extends ListFactory<ArrayList<Object>> {

	@Override
	public Class<?> getObjectClass() {
		return ArrayList.class;
	}

	@Override
	public ArrayList<Object> collection() {
		return new ArrayList<>();
	}

}
