package uk.ac.rhul.cs.dice.starworlds.prolog.test.gridworld;

import uk.ac.rhul.cs.dice.starworlds.entities.agent.components.concrete.SimpleComponentKey;
import uk.ac.rhul.cs.dice.starworlds.prolog.termmap.EnumKeyActionMapper;
import uk.ac.rhul.cs.dice.starworlds.prolog.termmap.TermFactory.TermMapException;
import uk.ac.rhul.cs.dice.starworlds.prolog.test.gridworld.MoveAction.Direction;
import alice.tuprolog.Struct;
import alice.tuprolog.Term;

public class GridActionTermMapper extends EnumKeyActionMapper<MoveAction, SimpleComponentKey> {

	public static final String NAME = "move";

	public GridActionTermMapper() {
		super(MoveAction.class, SimpleComponentKey.class, NAME);
	}

	@Override
	public Term toTerm(MoveAction arg) {
		return new Struct("move", new Struct(arg.getDirection().name().toLowerCase()));
	}

	@Override
	public ActionEntry<SimpleComponentKey, MoveAction> fromTerm(Term term) {
		try {
			// SimpleComponentKey key = this.getKeymapper().fromTerm(term);
			// Struct struct = (Struct) term;
			// if (struct.getArity() == 1) {
			Struct action = (Struct) term;
			if (action.getArity() == 1) {
				Direction direction = Direction.valueOf(action.getArg(0).toString().toUpperCase());
				return new ActionEntry<SimpleComponentKey, MoveAction>(SimpleComponentKey.ACTUATOR, new MoveAction(
						direction));
			} else {
				throw new TermMapException("Invalid arity of " + action + " must be 1");
			}
			// } else {
			// throw new TermMapException("Invalid arity of " + struct + " must be 1");
			// }
		} catch (Exception e) {
			throw new TermMapException(new StringBuilder("Invalid term to : ")
					.append(ActionEntry.class.getSimpleName()).append(term).append(" -> (")
					.append(SimpleComponentKey.class).append(":").append(MoveAction.class).append(")").toString(), e);
		}
	}
}
