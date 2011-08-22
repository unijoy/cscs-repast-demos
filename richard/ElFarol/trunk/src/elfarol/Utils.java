/*
 * Version info:
 *     $HeadURL$
 *     $LastChangedDate$
 *     $LastChangedRevision$
 *     $LastChangedBy$
 */
package elfarol;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import repast.simphony.engine.environment.RunState;

/**
 * Simple wrapper class for common utility methods for the <i>El Farol</i>
 * simulation.
 * 
 * @author Richard O. Legendi (richard.legendi)
 * @since 2.0-beta, 2011
 * @version $Id$
 */
public class Utils {

	/**
	 * Returns the list of agents containing the {@link Agent} instances
	 * associated to the master (<i>root</i>) context.
	 * 
	 * @return an <i>unmodifiable collection</i> containing all the
	 *         {@link Agent} instances
	 */
	public static List<Agent> getAgentList() {
		@SuppressWarnings("unchecked")
		final Iterable<Agent> agents = RunState.getInstance()
				.getMasterContext().getObjects(Agent.class);

		final ArrayList<Agent> ret = new ArrayList<Agent>();

		for (final Agent agent : agents) {
			ret.add(agent);
		}

		return Collections.unmodifiableList(ret);
	}

	/**
	 * Returns the current attendance level of the bar.
	 * 
	 * @return the number of agents whose attendance flag is <code>true</code>;
	 *         <code>0 <= return value <= number of agents</code>
	 */
	public static int getAttendance() {
		int ret = 0;

		for (final Agent act : getAgentList()) {
			if (act.isAttending()) {
				ret++;
			}
		}

		return ret;
	}

}
