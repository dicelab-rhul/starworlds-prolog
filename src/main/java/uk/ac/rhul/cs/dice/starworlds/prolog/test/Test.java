package uk.ac.rhul.cs.dice.starworlds.prolog.test;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jpl7.Term;

import uk.ac.rhul.cs.dice.starworlds.prolog.swi.term.GlobalTermFactory;
import uk.ac.rhul.cs.dice.starworlds.prolog.swi.term.SWITermFactory;
import uk.ac.rhul.cs.dice.starworlds.prolog.swi.term.action.SenseActionTermFactory;
import uk.ac.rhul.cs.dice.starworlds.prolog.term.Termable;

public class Test {

	private static GlobalTermFactory factory;

	static {
		factory = GlobalTermFactory.getInstance();
		SenseActionTermFactory termfactory = new SenseActionTermFactory();
	}

	public static void main(String[] args) throws Exception {
		System.out.println(Arrays.toString(new Test1<List<?>>().getClass().getDeclaredFields()));
		Set<String> set = new HashSet<>();
		set.add("a");
		set.add("b");
		Term t = factory.toTerm(new Test1<Set<?>>(set));
		System.out.println(t);
	}

	private static Object test(SWITermFactory factory, Object obj) {
		Term term = factory.toTerm(obj);
		System.out.println(term);
		return factory.fromTerm(term);
	}

	@Termable
	public static class Test1<T extends Collection<?>> {

		@Termable
		T arg;

		public Test1() {
		}

		public Test1(T arg) {
			this.arg = arg;
		}

		@Override
		public String toString() {
			return this.getClass().getSimpleName() + arg;
		}

	}
}
