package uk.ac.rhul.cs.dice.starworlds.prolog.test.gridworld.swi;

import uk.ac.rhul.cs.dice.starworlds.actions.environmental.SenseAction;
import uk.ac.rhul.cs.dice.starworlds.entities.agent.components.concrete.SimpleComponentKey;
import uk.ac.rhul.cs.dice.starworlds.environment.ambient.filter.FilterChain;
import uk.ac.rhul.cs.dice.starworlds.prolog.entities.agent.mind.SWIPrologAgentMind;
import uk.ac.rhul.cs.dice.starworlds.prolog.test.gridworld.GridAmbient;
import uk.ac.rhul.cs.dice.starworlds.prolog.test.gridworld.GridAmbient.Grid;
import uk.ac.rhul.cs.dice.starworlds.prolog.test.gridworld.GridAttributeFilter;
import uk.ac.rhul.cs.dice.starworlds.prolog.test.gridworld.GridAttributeFilter.GridView;

public class SWIGridAgentMind extends SWIPrologAgentMind<SimpleComponentKey> {
	
	public SWIGridAgentMind(String module) {
		super(module);
		this.termFactory.addMapper(new SWIGridActivePerceptionMapper());
		this.termFactory.addMapper(new SWIGridActionMapper());
	}

	@Override
	public void execute() {
		FilterChain<Grid, GridView> chain = FilterChain.start(GridAmbient.GRIDKEY).append(new GridAttributeFilter());
		actions.put(SimpleComponentKey.ACTIVESENSOR, new SenseAction(chain));
		super.execute();
	}
}
