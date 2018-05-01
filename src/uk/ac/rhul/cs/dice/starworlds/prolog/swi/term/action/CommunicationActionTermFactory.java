package uk.ac.rhul.cs.dice.starworlds.prolog.swi.term.action;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.jpl7.Atom;
import org.jpl7.Compound;
import org.jpl7.Term;

import uk.ac.rhul.cs.dice.starworlds.actions.environmental.CommunicationAction;
import uk.ac.rhul.cs.dice.starworlds.prolog.swi.term.SWITermFactory;
import uk.ac.rhul.cs.dice.starworlds.prolog.utils.SWIUtils;
import uk.ac.rhul.cs.dice.starworlds.prolog.utils.SWIUtils.StringCollector;

public class CommunicationActionTermFactory extends SWITermFactory {

	public static final String COMMUNICATIONACTIONTERMNAME = "message";

	@Override
	public Term toTerm(Object arg) {
		CommunicationAction action = (CommunicationAction) arg;
		Set<String> recipients = action.getRecipientsIds();
		if (!recipients.isEmpty()) {
			return new Compound(COMMUNICATIONACTIONTERMNAME, new Term[] { new Atom(action.getMessage().toString()),
					SWIUtils.toList(recipients.stream().map(Atom::new).collect(Collectors.toList())) });
		} else {
			return new Compound(COMMUNICATIONACTIONTERMNAME, new Term[] { new Atom(action.getMessage().toString()) });
		}
	}

	@Override
	public Object fromTerm(Term term) {
		if (term.arity() > 1) {
			;
			return new CommunicationAction(term.arg(1).toString(), SWIUtils.visitList(term.arg(2),
					new StringCollector<HashSet<String>>(new HashSet<>())));
		} else {
			return new CommunicationAction(term.arg(1).toString());
		}
	}

	@Override
	public Term toTerm(Object arg, Class<?>[] typeinfo) {
		return toTerm(arg);
	}

	@Override
	public Object fromTerm(Term term, Class<?>[] typeinfo) {
		return fromTerm(term);
	}

	@Override
	public Class<?> getObjectClass() {
		return CommunicationAction.class;
	}
}
