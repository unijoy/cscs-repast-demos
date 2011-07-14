/*
 * Version info:
 *     $HeadURL$
 *     $LastChangedDate$
 *     $LastChangedRevision$
 *     $LastChangedBy$
 */
package stupidmodel.agents;

import repast.simphony.engine.schedule.ScheduledMethod;
import repast.simphony.random.RandomHelper;
import repast.simphony.util.ContextUtils;
import repast.simphony.valueLayer.GridValueLayer;
import stupidmodel.common.Constants;

/**
 * Habitat cell objects have instance variables for their food availability and
 * maximum food production rate.
 * 
 * @author Richard O. Legendi (richard.legendi)
 * @since 2.0-beta, 2011
 * @since Model 3
 * @version $Id$
 */
public class HabitatCell {

	/** Maximum food production rate is initialized to <code>0.01</code>. */
	private static final double maximumFoodProductionRate = 0.01;

	/**
	 * Represents the actual food availability at this cell, initialized to
	 * <code>0.0</code>.
	 */
	private double foodAvailability = 0.0;

	/** Location of this cell at the grid. */
	private final int x, y;

	/**
	 * Creates a new instance of <code>HabitatCell</code>.
	 * 
	 * @param x
	 *            the specified <code>x</code> coordinate; <i>must be
	 *            non-negative</i>
	 * @param y
	 *            the specified <code>y</code> coordinate; <i>must be
	 *            non-negative</i>
	 */
	public HabitatCell(final int x, final int y) {
		if (x < 0) {
			throw new IllegalArgumentException(String.format(
					"Coordinate x = %d < 0.", x));
		}

		if (y < 0) {
			throw new IllegalArgumentException(String.format(
					"Coordinate y = %d < 0.", y));
		}

		this.x = x;
		this.y = y;
	}

	/**
	 * Returns the current food availability of this cell.
	 * 
	 * @return the <code>foodAvailability</code> of the cell; <i>a non-negative
	 *         value</i>
	 */
	public double getFoodAvailability() {
		return foodAvailability;
	}

	/**
	 * Each time step, food availability is increased by food production. Food
	 * production is a random floating point number between zero and the maximum
	 * food production.
	 * 
	 * <p>
	 * Food production is scheduled before agent actions.
	 * </p>
	 */
	@ScheduledMethod(start = 1, interval = 1, priority = 1)
	public void growFood() {
		foodAvailability += RandomHelper.nextDoubleFromTo(0.0,
				maximumFoodProductionRate);

		final GridValueLayer foodValueLayer = (GridValueLayer) ContextUtils
				.getContext(this).getValueLayer(Constants.FOOD_VALUE_LAYER_ID);

		if (null == foodValueLayer) {
			throw new IllegalStateException(
					"Cannot locate food value layer with ID="
							+ Constants.FOOD_VALUE_LAYER_ID + ".");
		}

		foodValueLayer.set(getFoodAvailability(), x, y);
	}

}
