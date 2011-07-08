/*
 * Version info:
 *     $HeadURL$
 *     $LastChangedDate$
 *     $LastChangedRevision$
 *     $LastChangedBy$
 */
package elfarol;

import static elfarol.ParameterWrapper.*;
import repast.simphony.context.Context;
import repast.simphony.context.DefaultContext;
import repast.simphony.dataLoader.ContextBuilder;
import repast.simphony.engine.schedule.ScheduledMethod;

/**
 * @author Richard O. Legendi (richard.legendi)
 * @since 2.0-beta, 2011
 * @version $Id$
 */
public class ElFarolContextBuilder extends DefaultContext<Object> implements
		ContextBuilder<Agent> {

	@Override
	public Context<Agent> build(final Context<Agent> context) {
		ParameterWrapper.reinit();
		History.getInstance().init();

		for (int i = 0; i < getAgentsNumber(); ++i) {
			final Agent agent = new Agent();
			context.add(agent);
		}

		return context;
	}

	@ScheduledMethod(start = 1, interval = 1)
	public void schedule() {
		for (final Agent act : Utils.getAgentList()) {
			act.updateAttendance();
		}

		History.getInstance().updateHistory();

		for (final Agent act : Utils.getAgentList()) {
			act.updateStrategies();
		}
	}

	public int attendance() {
		return Utils.getAttendance();
	}

}
