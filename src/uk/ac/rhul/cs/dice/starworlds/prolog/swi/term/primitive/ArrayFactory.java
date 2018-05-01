package uk.ac.rhul.cs.dice.starworlds.prolog.swi.term.primitive;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import org.jpl7.Atom;
import org.jpl7.Compound;
import org.jpl7.Term;

import uk.ac.rhul.cs.dice.starworlds.prolog.swi.term.collection.CollectionFactory;
import uk.ac.rhul.cs.dice.starworlds.prolog.utils.SWIUtils;

public class ArrayFactory extends CollectionFactory<ArrayList<Object>> {

	@Override
	public Term toTerm(Object arg) {
		Object[] array = (Object[]) arg;
		Term list = new Atom(SWIUtils.EMPTYARRAY);
		for (int i = array.length - 1; i >= 0; --i) {
			list = new Compound(SWIUtils.ARRAYDELIM, new Term[] {
					this.resolveFactory(array[i].getClass()).toTerm(array[i]), list });
		}
		return list;
	}

	@Override
	public Object[] fromTerm(Term term) {
		return ((List<?>) super.fromTerm(term)).toArray();
	}

	@Override
	public Term toTerm(Object arg, Class<?>[] typeinfo) {
		return toTerm(arg);
	}

	@Override
	public Object[] fromTerm(Term term, Class<?>[] typeinfo) {
		Object[] array = (Object[]) Array.newInstance(typeinfo[0], 0);
		return ((List<?>) super.fromTerm(term, typeinfo)).toArray(array);
	}

	public <C> Object[] fromTerm(Term term, Class<C> componentType) {
		return fromTerm(term, new Class[] { componentType });
	}

	@Override
	public Class<?> getObjectClass() {
		return Object[].class;
	}

	@Override
	public ArrayList<Object> collection() {
		return new ArrayList<>();
	}
}
