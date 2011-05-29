/*
 * Version info:
 *     $HeadURL$
 *     $LastChangedDate$
 *     $LastChangedRevision$
 *     $LastChangedBy$
 */
package stupidmodel.observer;

import java.awt.Color;

import repast.simphony.visualizationOGL2D.DefaultStyleOGL2D;
import stupidmodel.agents.Bug;

/**
 * A simple custom color implementation for {@link Bug} agents.
 * 
 * <p>
 * A bug's color on the display is shaded to reflect their size: it shades from
 * white (when size is zero) to red (when size is 10 or greater).
 * </p>
 * 
 * @author Richard O. Legendi (richard.legendi)
 * @since 2.0-beta, 2011
 * @version $Id$
 */
public class BugStyleOGL2D extends DefaultStyleOGL2D {

	/**
	 * Returns a modified color value for an agent. Bit tricky, though.
	 * 
	 * @see repast.simphony.visualizationOGL2D.DefaultStyleOGL2D#getColor(java.lang.Object)
	 */
	@Override
	public Color getColor(final Object agent) {
		if (agent instanceof Bug) {
			final Bug bug = (Bug) agent;

			assert (bug.getSize() >= 0) : String
					.format("An agent's size property should be non-negative, but its current value is %d.",
							bug.getSize());

			final int strength = (int) Math.max(255 - bug.getSize(), 0);
			return new Color(0xFF, strength, strength); // 0xFFFFFF - white,
														// 0xFF0000 - red
		}

		return super.getColor(agent);
	}
}
