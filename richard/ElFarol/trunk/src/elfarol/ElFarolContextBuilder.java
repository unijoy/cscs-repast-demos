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
 * The context builder for the <i>El Farol</i> simulation.
 * 
 * @author Richard O. Legendi (richard.legendi)
 * @since 2.0-beta, 2011
 * @version $Id: ElFarolContextBuilder.java 514 2011-07-08 14:50:51Z
 *          richard.legendi@gmail.com $
 */
public class ElFarolContextBuilder extends DefaultContext<Object> implements
		ContextBuilder<Agent> {

	// ========================================================================
	// === Super Interface ====================================================

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * repast.simphony.dataLoader.ContextBuilder#build(repast.simphony.context
	 * .Context)
	 */
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

	// ========================================================================
	// === Schedule ===========================================================

	/**
	 * Main schedule of the simulation containing the following phases:
	 * 
	 * <ol>
	 * <li><b>First</b>, all agents determine if their attend to the bar based
	 * on their knowledge (<i>strategies</i>) and the prediction</li>
	 * <li><b>Then</b> we update the global history</li>
	 * <li><b>At the end of the turn</b> when the new attendance level is known,
	 * all of the agents update their best strategy</li>
	 * </ol>
	 */
	@ScheduledMethod(start = 1, interval = 1)
	public void schedule() {
		for (final Agent act : Utils.getAgentList()) {
			act.updateAttendance();
		}

		History.getInstance().updateHistory();

		for (final Agent act : Utils.getAgentList()) {
			act.updateBestStrategy();
		}
	}

	// ========================================================================
	// === Observer Methods ===================================================

	/**
	 * Returns the current attendance level of the bar to display in a simple
	 * time series chart on the Repast GUI.
	 * 
	 * @return the current attendance level of the bar
	 */
	public int attendance() {
		return Utils.getAttendance();
	}

}
