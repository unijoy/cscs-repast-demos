/*
 * Version info:
 *     $HeadURL$
 *     $LastChangedDate$
 *     $LastChangedRevision$
 *     $LastChangedBy$
 */
package elfarol;

import java.util.ArrayList;
import java.util.List;

import repast.simphony.engine.environment.RunState;

/**
 * @author Richard O. Legendi (richard.legendi)
 * @since 2.0-beta, 2011
 * @version $Id$
 */
public class Utils {

	public static List<Agent> getAgentList() {
		@SuppressWarnings("unchecked")
		final Iterable<Agent> agents = RunState.getInstance()
				.getMasterContext().getObjects(Agent.class);

		final ArrayList<Agent> ret = new ArrayList<Agent>();

		for (final Agent agent : agents) {
			ret.add(agent);
		}

		return ret;
	}

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
