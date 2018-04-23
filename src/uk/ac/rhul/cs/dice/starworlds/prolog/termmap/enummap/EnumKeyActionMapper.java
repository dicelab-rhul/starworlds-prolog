package uk.ac.rhul.cs.dice.starworlds.prolog.termmap.enummap;

import uk.ac.rhul.cs.dice.starworlds.actions.environmental.EnvironmentalAction;
import uk.ac.rhul.cs.dice.starworlds.prolog.termmap.ActionMapper;
import uk.ac.rhul.cs.dice.starworlds.prolog.termmap.term.Term;

public abstract class EnumKeyActionMapper<L, T extends Term<L>, P extends EnvironmentalAction, R extends Enum<R>>
		extends ActionMapper<L, T, P, R> {

	protected EnumMapper<L, T, R> keymapper;

	public EnumKeyActionMapper(Class<P> actionClass, Class<R> keyClass, String predicateName) {
		super(actionClass, predicateName);
	}

	public EnumMapper<L, T, R> getKeymapper() {
		return keymapper;
	}
}
