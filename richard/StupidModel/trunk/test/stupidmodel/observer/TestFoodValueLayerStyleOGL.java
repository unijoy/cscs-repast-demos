/*
 * Version info:
 *     $HeadURL$
 *     $LastChangedDate$
 *     $LastChangedRevision$
 *     $LastChangedBy$
 */
package stupidmodel.observer;

import org.junit.Assert;
import org.junit.Test;

import stupidmodel.common.Constants;

/**
 * Simple tests for the created custom food value layer style.
 * 
 * <p>
 * Food color shade from white when size is zero to green, when the available
 * food on a cell is <code>255</code> or greater.
 * </p>
 * 
 * @author Richard O. Legendi (richard.legendi)
 * @since 2.0-beta, 2011
 * @version $Id$
 */
public class TestFoodValueLayerStyleOGL {

	/** Style object to test. */
	// TODO Mock out ValueLayer in init
	private final FoodValueLayerStyleOGL style = new FoodValueLayerStyleOGL();

	// ========================================================================
	// === General Tests ======================================================

	@Test(expected = IllegalArgumentException.class)
	public void testInitParameterValidation() {
		style.init(null);
	}

	@Test
	public void testInit() {
		// TODO
		// Assert.assertEquals(stuff, style.layer);
	}

	/**
	 * Test if the returned cell size is the specified constant value.
	 */
	@Test
	public void testCellSize() {
		Assert.assertEquals(Constants.GUI_CELL_SIZE, style.getCellSize(), Constants.DELTA);
	}

	// ========================================================================
	// === Test Color Values ==================================================

	/**
	 * Test if returned color is white when size is <code>0</code>.
	 */
	@Test
	public void testWhiteColor() {
		// TODO
	}

	/**
	 * Test if returned color is green when size is <code>255</code>.
	 */
	@Test
	public void testGreenColor() {
		// TODO
	}

	// No test for the intermediate state, since there is no dedicated constant
	// for it in class java.awt.Color

}
