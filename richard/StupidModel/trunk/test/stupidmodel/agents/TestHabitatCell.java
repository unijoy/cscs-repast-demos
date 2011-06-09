/*
 * Version info:
 *     $HeadURL$
 *     $LastChangedDate$
 *     $LastChangedRevision$
 *     $LastChangedBy$
 */
package stupidmodel.agents;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import junit.framework.Assert;

import org.junit.Test;

import repast.simphony.context.Context;
import repast.simphony.context.DefaultContext;
import repast.simphony.engine.environment.RunState;
import repast.simphony.random.RandomHelper;
import repast.simphony.valueLayer.GridValueLayer;
import stupidmodel.common.Constants;

/**
 * Simple tests for the created {@link HabitatCell} agents.
 * 
 * @author Richard O. Legendi (richard.legendi)
 * @since 2.0-beta, 2011
 * @since Model 3
 * @version $Id$
 */
public class TestHabitatCell {

	/**
	 * {@link HabitatCell} objects cannot be created with negative
	 * <code>x</code> coordinate.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testInvalidCoordinateX() {
		final int x = RandomHelper.nextIntFromTo(Integer.MIN_VALUE, -1);
		final int y = RandomHelper.nextIntFromTo(0, Integer.MAX_VALUE);

		new HabitatCell(x, y);
	}

	/**
	 * {@link HabitatCell} objects cannot be created with negative
	 * <code>y</code> coordinate.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testInvalidCoordinateY() {
		final int x = RandomHelper.nextIntFromTo(0, Integer.MAX_VALUE);
		final int y = RandomHelper.nextIntFromTo(Integer.MIN_VALUE, -1);

		new HabitatCell(x, y);
	}

	@Test
	public void testHabitatCellCreationCorrectX() {
		final int x = RandomHelper.nextIntFromTo(0, Integer.MAX_VALUE);
		final int y = RandomHelper.nextIntFromTo(0, Integer.MAX_VALUE);

		final HabitatCell cell = new HabitatCell(x, y);
		Assert.assertEquals(x, cell.x);
	}

	@Test
	public void testHabitatCellCreationCorrectY() {
		final int x = RandomHelper.nextIntFromTo(0, Integer.MAX_VALUE);
		final int y = RandomHelper.nextIntFromTo(0, Integer.MAX_VALUE);

		final HabitatCell cell = new HabitatCell(x, y);
		Assert.assertEquals(y, cell.y);
	}

	@Test
	public void testDefaultMaximumFoodProductionRate() {
		final int x = RandomHelper.nextIntFromTo(0, Integer.MAX_VALUE);
		final int y = RandomHelper.nextIntFromTo(0, Integer.MAX_VALUE);

		final HabitatCell cell = new HabitatCell(x, y);

		Assert.assertEquals(0.01, cell.maximumFoodProductionRate,
				Constants.DELTA);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetMaximumFoodProductionRateFailure() {
		final int x = RandomHelper.nextIntFromTo(0, Integer.MAX_VALUE);
		final int y = RandomHelper.nextIntFromTo(0, Integer.MAX_VALUE);

		final HabitatCell cell = new HabitatCell(x, y);

		final double wrongValue = RandomHelper.nextDoubleFromTo(
				-Double.MAX_VALUE, -Double.MIN_VALUE);

		cell.setMaximumFoodProductionRate(wrongValue);
	}

	@Test
	public void testSetMaximumFoodProductionRateWorks() {
		final int x = RandomHelper.nextIntFromTo(0, Integer.MAX_VALUE);
		final int y = RandomHelper.nextIntFromTo(0, Integer.MAX_VALUE);

		final HabitatCell cell = new HabitatCell(x, y);

		final double value = RandomHelper.nextDoubleFromTo(0.0,
				Double.MAX_VALUE);

		cell.setMaximumFoodProductionRate(value);
		Assert.assertEquals(value, cell.getMaximumFoodProductionRate(),
				Constants.DELTA);
	}

	@Test
	public void testDefaultGetFoodAvailability() {
		final int x = RandomHelper.nextIntFromTo(0, Integer.MAX_VALUE);
		final int y = RandomHelper.nextIntFromTo(0, Integer.MAX_VALUE);

		final HabitatCell cell = new HabitatCell(x, y);

		Assert.assertEquals(0.0, cell.getFoodAvailability(), Constants.DELTA);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetFoodAvailabilityFailure() {
		final int x = RandomHelper.nextIntFromTo(0, Integer.MAX_VALUE);
		final int y = RandomHelper.nextIntFromTo(0, Integer.MAX_VALUE);

		final HabitatCell cell = new HabitatCell(x, y);

		final double wrongValue = RandomHelper.nextDoubleFromTo(
				-Double.MAX_VALUE, -Double.MIN_VALUE);

		cell.setFoodAvailability(wrongValue);
	}

	@Test
	public void testSetFoodAvailability() {
		final int x = RandomHelper.nextIntFromTo(0, Integer.MAX_VALUE);
		final int y = RandomHelper.nextIntFromTo(0, Integer.MAX_VALUE);

		final HabitatCell cell = new HabitatCell(x, y);

		final double value = RandomHelper.nextDoubleFromTo(0.0,
				Double.MAX_VALUE);

		cell.setFoodAvailability(value);
		Assert.assertEquals(value, cell.getFoodAvailability(), Constants.DELTA);
	}

	@Test(expected = IllegalStateException.class)
	public void testDefaultGrowFoodWithoutProperGridValueLayer() {
		final int x = RandomHelper.nextIntFromTo(0, Integer.MAX_VALUE);
		final int y = RandomHelper.nextIntFromTo(0, Integer.MAX_VALUE);

		final HabitatCell cell = new HabitatCell(x, y);

		{ // Some workaround to make the test see like if it was running
			final Context<Object> context = new DefaultContext<Object>();

			context.add(cell);
			RunState.init().setMasterContext(context);
		}

		cell.growFood();
	}

	@Test
	public void testDefaultGrowFood() {
		final int x = RandomHelper.nextIntFromTo(0, Integer.MAX_VALUE);
		final int y = RandomHelper.nextIntFromTo(0, Integer.MAX_VALUE);

		final HabitatCell cell = new HabitatCell(x, y);

		final GridValueLayer valueLayer = mock(GridValueLayer.class);
		when(valueLayer.getName()).thenReturn(Constants.FOOD_VALUE_LAYER_ID);

		{ // Some workaround to make the test see like if it was running
			final Context<Object> context = new DefaultContext<Object>();

			context.add(cell);
			context.addValueLayer(valueLayer);
			RunState.init().setMasterContext(context);
		}

		final double prevFood = cell.getFoodAvailability();

		cell.growFood();

		Assert.assertTrue(prevFood <= cell.getFoodAvailability());
		Assert.assertTrue(cell.getFoodAvailability() <= prevFood
				+ cell.getMaximumFoodProductionRate());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testFoodConsumedFailure() {
		final int x = RandomHelper.nextIntFromTo(0, Integer.MAX_VALUE);
		final int y = RandomHelper.nextIntFromTo(0, Integer.MAX_VALUE);

		final HabitatCell cell = new HabitatCell(x, y);
		final double wrongValue = RandomHelper.nextDoubleFromTo(
				-Double.MAX_VALUE, -Double.MIN_VALUE);
		cell.foodConsumed(wrongValue);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testMoreThanAvailableFoodConsumed() {
		final int x = RandomHelper.nextIntFromTo(0, Integer.MAX_VALUE);
		final int y = RandomHelper.nextIntFromTo(0, Integer.MAX_VALUE);

		final HabitatCell cell = new HabitatCell(x, y);

		final double wrongValue = RandomHelper.nextDoubleFromTo(
				cell.getFoodAvailability(), Double.MAX_VALUE);
		cell.foodConsumed(wrongValue);
	}

	@Test
	public void testFoodConsumed() {
		final int x = RandomHelper.nextIntFromTo(0, Integer.MAX_VALUE);
		final int y = RandomHelper.nextIntFromTo(0, Integer.MAX_VALUE);

		final HabitatCell cell = new HabitatCell(x, y);

		final double food = RandomHelper
				.nextDoubleFromTo(0.0, Double.MAX_VALUE);

		final double consumed = RandomHelper.nextDoubleFromTo(0.0, food);

		cell.setFoodAvailability(food);
		cell.foodConsumed(consumed);

		Assert.assertEquals(food - consumed, cell.getFoodAvailability(),
				Constants.DELTA);
	}

	@Test
	public void testToString() {
		final int x = RandomHelper.nextIntFromTo(0, Integer.MAX_VALUE);
		final int y = RandomHelper.nextIntFromTo(0, Integer.MAX_VALUE);

		final HabitatCell cell = new HabitatCell(x, y);
		Assert.assertNotNull(cell.toString());
	}

}
