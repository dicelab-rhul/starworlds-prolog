package uk.ac.rhul.cs.dice.starworlds.prolog.swi.term.filter;

import org.jpl7.Term;

import uk.ac.rhul.cs.dice.starworlds.environment.ambient.filter.MapSearchFilter;
import uk.ac.rhul.cs.dice.starworlds.prolog.swi.term.SWITermFactory;

public class MapSearchFilterTermFactory extends SWITermFactory {

	public static final String TERMNAME = "mapsearch";

	@Override
	public Term toTerm(Object arg) {
		MapSearchFilter filter = (MapSearchFilter) arg;
		uk.ac.rhul.cs.dice.starworlds.prolog.swi.term.collection.CollectionFactory<?> factory;
		// return new Compound(TERMNAME, new Term[] { SWI});
		return null;
	}

	@Override
	public Object fromTerm(Term term) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Term toTerm(Object arg, Class<?>[] typeinfo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object fromTerm(Term term, Class<?>[] typeinfo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Class<?> getObjectClass() {
		// TODO Auto-generated method stub
		return null;
	}

}
