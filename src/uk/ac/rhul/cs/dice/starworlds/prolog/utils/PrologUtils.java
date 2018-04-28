package uk.ac.rhul.cs.dice.starworlds.prolog.utils;

import java.util.Collection;

import alice.tuprolog.Term;

public class PrologUtils {

	public static String collectionToPrologListString(Collection<Term> terms) {
		StringBuilder builder = new StringBuilder("[");
		for (Term string : terms) {
			builder.append(string).append(",");

		}
		if (builder.length() > 1)
			builder.deleteCharAt(builder.length() - 1);
		return builder.append("]").toString();
	}

}
