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
 * @version $Id$
 * @since Model 3
 */
public class HabitatCell {

	/** Maximum food production rate is initialized to <code>0.01</code>. */
	private static final double maximumFoodProductionRate = 0.01;

	/**
	 * Represents the actual food availability at this cell, initialized to
	 * <code>0.0</code>.
	 */
	private double foodAvailability = 0.0;

	private final int x;

	private final int y;

	/**
	 * Creates a new instance of <code>HabitatCell</code>.
	 */
	public HabitatCell(final int x, final int y) {
		this.x = x;
		this.y = y;
	}

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
	@ScheduledMethod(start = 1, interval = 1, priority = -2)
	public void growFood() {
		foodAvailability += RandomHelper.nextDoubleFromTo(0.0,
				maximumFoodProductionRate);

		final GridValueLayer foodValueLayer = (GridValueLayer) ContextUtils
				.getContext(this).getValueLayer(Constants.FOOD_VALUE_LAYER_ID);

		foodValueLayer.set(getFoodAvailability(), x, y);
	}

}
