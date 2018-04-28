package uk.ac.rhul.cs.dice.starworlds.prolog.utils;

import java.util.Collection;
import java.util.List;

import org.jpl7.Atom;
import org.jpl7.Compound;
import org.jpl7.Term;
import org.jpl7.Util;

public class SWIUtils {

	public static final String ARRAYDELIM = Util.stringArrayToList(new String[] { "" }).name();
	public static final String EMPTYARRAY = Util.stringArrayToList(new String[] {}).name();

	static {
		Util.stringArrayToList(new String[] {});
		Util.stringArrayToList(new String[] { "" });
	}

	public static Term toList(List<Term> terms) {
		Term list = new Atom(EMPTYARRAY);
		for (int i = terms.size() - 1; i >= 0; --i) {
			list = new Compound(ARRAYDELIM, new Term[] { terms.get(i), list });
		}
		return list;
	}

	public static Term toList(Term[] terms) {
		Term list = new Atom(EMPTYARRAY);
		for (int i = terms.length - 1; i >= 0; --i) {
			list = new Compound(ARRAYDELIM, new Term[] { terms[i], list });
		}
		return list;
	}

	public static <V> V visitList(Term term, TermVisitor<V> vistor) {
		if (term == null || term.name().equals("[]")) {
			return null;
		}
		V v = vistor.visit(term.arg(1));
		Term next = term.arg(2);
		while (!next.name().equals(EMPTYARRAY)) {
			v = vistor.visit(next.arg(1), v);
			next = next.arg(2);
		}
		return v;
	}

	public static class StringCollector<A extends Collection<String>> implements TermVisitor<A> {
		private A terms;

		public StringCollector(A collection) {
			terms = collection;
		}

		@Override
		public A visit(Term term) {
			terms.add(term.toString());
			return terms;
		}

		@Override
		public A visit(Term term, A prev) {
			terms.add(term.toString());
			return terms;
		}
	}

	public static class TermCollector<A extends Collection<Term>> implements TermVisitor<A> {
		private A terms;

		public TermCollector(A collection) {
			terms = collection;
		}

		@Override
		public A visit(Term term) {
			terms.add(term);
			return terms;
		}

		@Override
		public A visit(Term term, A prev) {
			terms.add(term);
			return terms;
		}
	}

	public static interface TermVisitor<V> {

		public V visit(Term term);

		public V visit(Term term, V prev);
	}
}
