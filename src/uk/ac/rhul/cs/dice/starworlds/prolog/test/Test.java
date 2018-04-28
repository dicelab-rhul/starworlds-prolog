package uk.ac.rhul.cs.dice.starworlds.prolog.test;

import java.util.ArrayList;
import java.util.List;

import org.jpl7.Atom;
import org.jpl7.Compound;
import org.jpl7.Term;
import org.jpl7.Util;

import uk.ac.rhul.cs.dice.starworlds.prolog.swi.term.GlobalTermFactory;
import uk.ac.rhul.cs.dice.starworlds.prolog.swi.term.SWITermFactory;
import uk.ac.rhul.cs.dice.starworlds.prolog.term.Termable;
import uk.ac.rhul.cs.dice.starworlds.prolog.utils.SWIUtils;
import uk.ac.rhul.cs.dice.starworlds.prolog.utils.SWIUtils.TermCollector;

public class Test {

	private static GlobalTermFactory factory;

	static {
		// SWIGeneralTermFactory.initialise();
		// factory = SWIGeneralTermFactory.getInstance();
	}

	public static void main(String[] args) throws Exception {
		System.out.println(Util.stringArrayToList(new String[] {}).name());
		System.out.println(Util.stringArrayToList(new String[] { "" }).name());

		List<Term> terms = new ArrayList<>();
		terms.add(new Atom("a"));
		terms.add(new Atom("b"));
		terms.add(new Atom("c"));
		Term term = SWIUtils.toList(terms);
		List<Term> eterms = SWIUtils.visitList(term, new TermCollector<ArrayList<Term>>(new ArrayList<>()));
		System.out.println(eterms);
	}

	public class TestP1 {
		public class TestP2 {

		}
	}

	// @Termable(name = Test1.TERMNAME, factory = Test1Factory.class)
	public static class Test1 {

		public static final String TERMNAME = "test";

		@Termable
		private String t;

		public Test1() {
		}

		public Test1(String t) {
			this.t = t;
		}

		@Override
		public String toString() {
			return this.getClass().getSimpleName() + " -> " + t;
		}
	}

	public static class Test1Factory implements SWITermFactory {

		public Test1Factory() {
		}

		@Override
		public Term toTerm(Object arg) throws Exception {
			Test1 t = (Test1) arg;
			return new Compound(Test1.TERMNAME, new Term[] { new Atom(t.t) });
		}

		@Override
		public Test1 fromTerm(Term term) throws Exception {
			return new Test1(term.arg(1).name());
		}

	}

	// @Termable(name = "test")
	public static class Test2 {
		@Termable
		private String message = "hello";
		@Termable
		private Integer index = 1;
		@Termable
		private E e = null;

		@Override
		public String toString() {
			return this.getClass().getSimpleName() + "  " + message + "  " + index + "  " + e;
		}
	}

	private static void test(Object arg) throws Exception {
		System.out.println();
		Term term = factory.toTerm(arg);
		System.out.println(term);
		Object obj = factory.fromTerm(term);
		System.out.println(obj.getClass());
	}

	private static void test(Term term) throws Exception {
		System.out.println();
		System.out.println(term);
		Object obj = factory.fromTerm(term);
		System.out.println(obj);
		System.out.println(factory.toTerm(obj));
	}

	// @Termable
	private enum E {
		A, B, C;
	}
}
