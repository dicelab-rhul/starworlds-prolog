package uk.ac.rhul.cs.dice.starworlds.prolog.termmap;

import uk.ac.rhul.cs.dice.starworlds.prolog.termmap.TermFactory.TermMapException;

public abstract class AbstractTermMapper<P, R> implements TermMapper<P, R> {

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
