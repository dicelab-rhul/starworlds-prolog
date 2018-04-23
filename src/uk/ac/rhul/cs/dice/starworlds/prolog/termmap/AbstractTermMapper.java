package uk.ac.rhul.cs.dice.starworlds.prolog.termmap;

import uk.ac.rhul.cs.dice.starworlds.prolog.termmap.TermFactory.TermMapException;
import uk.ac.rhul.cs.dice.starworlds.prolog.termmap.term.Term;

public abstract class AbstractTermMapper<L, T extends Term<L>, P, R> implements TermMapper<L, T, P, R> {

	protected Class<P> cls;
	protected String predicateName;

	public AbstractTermMapper(Class<P> cls, String predicateName) {
		if (cls == null) {
			throw new TermMapException("Class cannot be null");
		}
		if (predicateName == null) {
			throw new TermMapException("Predicate name cannot be null");
		}
		this.cls = cls;
		this.predicateName = predicateName;
	}

	public Class<P> getMappingClass() {
		return cls;
	}

	public String getPredicateName() {
		return predicateName;
	}

}
