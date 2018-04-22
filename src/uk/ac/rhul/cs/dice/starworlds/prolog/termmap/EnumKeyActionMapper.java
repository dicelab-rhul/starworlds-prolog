package uk.ac.rhul.cs.dice.starworlds.prolog.termmap;

import uk.ac.rhul.cs.dice.starworlds.actions.environmental.EnvironmentalAction;

public abstract class EnumKeyActionMapper<P extends EnvironmentalAction, R extends Enum<R>> extends ActionMapper<P, R> {

	protected EnumMapper<R> keymapper;

	public EnumKeyActionMapper(Class<P> actionClass, Class<R> keyClass, String predicateName) {
		super(actionClass, predicateName);
		keymapper = new EnumMapper<>(keyClass);
	}

	public EnumMapper<R> getKeymapper() {
		return keymapper;
	}
}
