package uk.ac.rhul.cs.dice.starworlds.prolog.test.gridworld.swi;

import org.jpl7.Atom;
import org.jpl7.Compound;
import org.jpl7.Term;

import uk.ac.rhul.cs.dice.starworlds.entities.agent.components.concrete.SimpleComponentKey;
import uk.ac.rhul.cs.dice.starworlds.prolog.termmap.TermFactory.TermMapException;
import uk.ac.rhul.cs.dice.starworlds.prolog.termmap.enummap.EnumKeyActionMapper;
import uk.ac.rhul.cs.dice.starworlds.prolog.termmap.term.SWITerm;
import uk.ac.rhul.cs.dice.starworlds.prolog.test.gridworld.MoveAction;
import uk.ac.rhul.cs.dice.starworlds.prolog.test.gridworld.MoveAction.Direction;

public class SWIGridActionMapper extends EnumKeyActionMapper<Term, SWITerm, MoveAction, SimpleComponentKey> {

	public static final String NAME = "move";

	public SWIGridActionMapper() {
		super(MoveAction.class, SimpleComponentKey.class, NAME);
	}

	@Override
	public SWITerm toTerm(MoveAction arg) {
		return new SWITerm(new Compound("move", new Term[] { new Atom(arg.getDirection().name().toLowerCase()) }));
	}

	@Override
	public ActionEntry<SimpleComponentKey, MoveAction> fromTerm(SWITerm term) {
		try {
			// SimpleComponentKey key = this.getKeymapper().fromTerm(term);
			// Struct struct = (Struct) term;
			// if (struct.getArity() == 1) {
			Term action = term.getTerm();
			if (action.arity() == 1) {
				Direction direction = Direction.valueOf(action.arg(1).toString().toUpperCase());
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
