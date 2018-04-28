package uk.ac.rhul.cs.dice.starworlds.prolog.actions;

import uk.ac.rhul.cs.dice.starworlds.actions.environmental.SenseAction;
import uk.ac.rhul.cs.dice.starworlds.environment.ambient.filter.FilterChain;

public abstract class PrologSenseAction extends SenseAction implements PrologEnvironmentalAction {

	private static final long serialVersionUID = -1055539712309198218L;
	public static final String TERMNAME = "sense";

	public PrologSenseAction(FilterChain<?, ?> chain) {
		super(chain);
	}

}
