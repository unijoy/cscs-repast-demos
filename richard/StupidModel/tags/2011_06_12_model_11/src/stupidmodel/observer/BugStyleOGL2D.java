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
 * @version $Id: BugStyleOGL2D.java 183 2011-05-29 17:09:27Z
 *          richard.legendi@gmail.com $
 */
public class BugStyleOGL2D extends DefaultStyleOGL2D {

	/**
	 * {@inheritDoc}
	 * 
	 * <p>
	 * Returns a modified color value for an agent. Bit tricky, though, contains
	 * a minor hex-magic to determine the actual color value.
	 * </p>
	 * 
	 * @param agent
	 *            the agent whose color should be determined; <i>may be null</i>
	 * @return the color for the specified {@link Bug} agent created as
	 *         <code>new Color(0xFF, strength, strength)</code> (where
	 *         <code>strength = 255 - size of the bug</code>); <i>or
	 *         <code>Color.BLUE</code> if the parameter was <code>null</code>
	 *         </i>
	 * @see repast.simphony.visualizationOGL2D.DefaultStyleOGL2D#getColor(java.lang.Object)
	 */
	@Override
	public Color getColor(final Object agent) {
		if (agent instanceof Bug) {
			final Bug bug = (Bug) agent;

			if (bug.getSize() < 0) {
				throw new IllegalStateException(
						String.format(
								"An agent's size property should be non-negative, but its current value is %f.",
								bug.getSize()));
			}

			final int strength = (int) Math.max(200 - 20 * bug.getSize(), 0);
			return new Color(0xFF, strength, strength); // 0xFFFFFF - white,
														// 0xFF0000 - red
		}

		return super.getColor(agent);
	}

}
