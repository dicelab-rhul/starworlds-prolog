package uk.ac.rhul.cs.dice.starworlds.prolog.swi.agent;

import java.util.Collection;

import org.jpl7.Term;

import uk.ac.rhul.cs.dice.starworlds.actions.environmental.CommunicationAction;
import uk.ac.rhul.cs.dice.starworlds.actions.environmental.SenseAction;
import uk.ac.rhul.cs.dice.starworlds.appearances.ActiveBodyAppearance;
import uk.ac.rhul.cs.dice.starworlds.entities.agent.components.AbstractComponent;
import uk.ac.rhul.cs.dice.starworlds.entities.agent.components.ComponentKey;
import uk.ac.rhul.cs.dice.starworlds.perception.CommunicationPerception;
import uk.ac.rhul.cs.dice.starworlds.prolog.entities.agent.mind.PrologAgent;
import uk.ac.rhul.cs.dice.starworlds.prolog.entities.agent.mind.PrologMind;
import uk.ac.rhul.cs.dice.starworlds.prolog.swi.term.CommunicationActionTermFactory;
import uk.ac.rhul.cs.dice.starworlds.prolog.swi.term.CommunicationPerceptionTermFactory;
import uk.ac.rhul.cs.dice.starworlds.prolog.swi.term.GlobalTermFactory;
import uk.ac.rhul.cs.dice.starworlds.prolog.swi.term.SenseActionTermFactory;

public class SWIAgent<K extends ComponentKey> extends PrologAgent<Term, K> {

	static {
		// add the factories for Sensing and Communication action, since they have not been annotated with Termable.
		GlobalTermFactory factory = GlobalTermFactory.initialise();
		factory.declareFactory(SenseAction.class, SenseActionTermFactory.SENSEACTIONTERMNAME,
				new SenseActionTermFactory());
		factory.declareFactory(CommunicationAction.class, CommunicationActionTermFactory.COMMUNICATIONACTIONTERMNAME,
				new CommunicationActionTermFactory());
		factory.declareFactory(CommunicationPerception.class,
				CommunicationPerceptionTermFactory.COMMUNICATIONPERCEPTIONTERMNAME,
				new CommunicationPerceptionTermFactory());
	}

	public SWIAgent(ActiveBodyAppearance<K> appearance, Collection<AbstractComponent<K>> components,
			PrologMind<Term, K> mind) {
		super(appearance, components, mind);
	}

	public SWIAgent(Collection<AbstractComponent<K>> components, PrologMind<Term, K> mind) {
		super(components, mind);
	}
}
