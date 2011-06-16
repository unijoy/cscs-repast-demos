/*
 * Version info:
 *     $HeadURL$
 *     $LastChangedDate$
 *     $LastChangedRevision$
 *     $LastChangedBy$
 */
package stupidmodel.observer;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.awt.Color;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import repast.simphony.random.RandomHelper;
import repast.simphony.valueLayer.ValueLayer;
import stupidmodel.common.Constants;

/**
 * Simple tests for the created custom food value layer style.
 * 
 * <p>
 * Food color shade from black when food level is zero to green, when the
 * available food on a cell is <code>255</code> or greater.
 * </p>
 * 
 * @author Richard O. Legendi (richard.legendi)
 * @since 2.0-beta, 2011
 * @version $Id$
 * @see FoodValueLayerStyleOGL
 */
public class TestFoodValueLayerStyleOGL {

	/** Style object to test, mocked out when verifying behaviour. */
	private FoodValueLayerStyleOGL style;

	/**
	 * Initialize the style object (it may be parameterized differently for each
	 * test).
	 */
	@Before
	public void initTest() {
		style = new FoodValueLayerStyleOGL();
	}

	// ========================================================================
	// === General Tests ======================================================

	/**
	 * Testing {@link FoodValueLayerStyleOGL#init(ValueLayer)} for
	 * <code>null</code> parameter.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testInitParameterValidation() {
		style.init(null);
	}

	/**
	 * Testing {@link FoodValueLayerStyleOGL#init(ValueLayer)} for multiple
	 * initialization calls.
	 */
	@Test(expected = IllegalStateException.class)
	public void testMultipleInit() {
		final ValueLayer layer = mock(ValueLayer.class);
		style.init(layer);
		style.init(layer); // Multiple initialization should fail
	}

	/**
	 * Testing {@link FoodValueLayerStyleOGL#init(ValueLayer)} for proper
	 * initialization by checking the representation directly.
	 */
	@Test
	public void testInitWorksProperly() {
		final ValueLayer layer = mock(ValueLayer.class);
		style.init(layer);
		Assert.assertEquals(layer, style.layer); // Testing representation
	}

	/**
	 * Test if the returned cell size is the specified constant value.
	 */
	@Test
	public void testCellSize() {
		Assert.assertEquals(Constants.GUI_CELL_SIZE, style.getCellSize(),
				Constants.DELTA);
	}

	// ========================================================================
	// === Test Color Values ==================================================

	/**
	 * Test if invalid food value is assigned to a location.
	 */
	@Test(expected = IllegalStateException.class)
	public void testInvalidFoodValue() {
		final int x = RandomHelper.nextIntFromTo(0, Integer.MAX_VALUE);
		final int y = RandomHelper.nextIntFromTo(0, Integer.MAX_VALUE);

		final ValueLayer layer = mock(ValueLayer.class);

		final double wrongValue = RandomHelper.nextDoubleFromTo(
				-Double.MAX_VALUE, -Double.MIN_VALUE);

		when(layer.get(x, y)).thenReturn(wrongValue);
		style.init(layer);

		style.getColor(x, y); // Should fail
	}

	/**
	 * Test if returned color is black when food level is <code>0</code>.
	 */
	@Test
	public void testBlackColor() {
		final int x = RandomHelper.nextIntFromTo(0, Integer.MAX_VALUE);
		final int y = RandomHelper.nextIntFromTo(0, Integer.MAX_VALUE);

		final ValueLayer layer = mock(ValueLayer.class);
		when(layer.get(x, y)).thenReturn(0.0);
		style.init(layer);

		Assert.assertEquals(Color.BLACK, style.getColor(x, y));
	}

	/**
	 * Test if returned color is green when food level is <code>255</code>.
	 */
	@Test
	public void testGreenColor() {
		final int x = RandomHelper.nextIntFromTo(0, Integer.MAX_VALUE);
		final int y = RandomHelper.nextIntFromTo(0, Integer.MAX_VALUE);

		final ValueLayer layer = mock(ValueLayer.class);
		when(layer.get(x, y)).thenReturn(255.0);
		style.init(layer);

		Assert.assertEquals(Color.GREEN, style.getColor(x, y));
	}

	// No test for the intermediate state, since there is no dedicated constant
	// for it in class java.awt.Color

}
