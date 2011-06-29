/*
 * Version info:
 *     $HeadURL$
 *     $LastChangedDate$
 *     $LastChangedRevision$
 *     $LastChangedBy$
 */
package stupidmodel.common;

import junit.framework.Assert;

import org.junit.Test;

import repast.simphony.random.RandomHelper;

/**
 * Simple tests for the created {@link CellData} wrapper class that encapsulates
 * data read directly from the specified input file.
 * 
 * @author Richard O. Legendi (richard.legendi)
 * @since 2.0-beta, 2011
 * @version $Id$
 * @see CellData
 */
public class TestCellData {

	/**
	 * Test if the <code>equals()</code> implementation fulfills its contract.
	 */
	@Test
	public void testEquals() {
		final CellData c1 = new CellData(1, 2, 3.0);
		final CellData c2 = new CellData(1, 2, 3.0);

		Assert.assertEquals(c1, c2);
		Assert.assertEquals(c1.hashCode(), c2.hashCode());
	}

	/**
	 * Test <code>x.equals(null)</code> returns <code>false</code>.
	 */
	@Test
	public void testNotEqualsForNull() {
		final CellData cell = new CellData(1, 2, 3.0);

		Assert.assertFalse(cell.equals(null));
	}

	/**
	 * Test <code>x.equals()</code> on any other object returns
	 * <code>false</code>.
	 */
	@Test
	public void testNotEqualsForAnythingElse() {
		final CellData cell = new CellData(1, 2, 3.0);

		Assert.assertFalse(cell.equals("Beavis"));
	}

	/**
	 * Test if difference in the <code>x</code> coordinates returns
	 * <code>false</code>.
	 */
	@Test
	public void testNotEquals1() {
		final CellData c1 = new CellData(1, 2, 3.0);
		final CellData c2 = new CellData(2, 2, 3.0);

		Assert.assertFalse(c1.equals(c2));
	}

	/**
	 * Test if difference in the <code>y</code> coordinates returns
	 * <code>false</code>.
	 */
	@Test
	public void testNotEquals2() {
		final CellData c1 = new CellData(1, 2, 3.0);
		final CellData c2 = new CellData(1, 1, 3.0);

		Assert.assertFalse(c1.equals(c2));
	}

	/**
	 * Test if difference in the <code>foodProductionRate</code> coordinates
	 * returns <code>false</code>.
	 */
	@Test
	public void testNotEquals3() {
		final CellData c1 = new CellData(1, 2, 3.0);
		final CellData c2 = new CellData(1, 2, 1.0);

		Assert.assertFalse(c1.equals(c2));
	}

	/**
	 * Test if <code>hashCode()</code> returns <code>true</code> for equal
	 * instances.
	 */
	@Test
	public void testHashCode() {
		final CellData c1 = new CellData(1, 2, 3.0);
		final CellData c2 = new CellData(1, 2, 3.0);

		Assert.assertTrue(c1.hashCode() == c2.hashCode());
	}

	/**
	 * Just to suppress <code>toString()</code> coverage noise.
	 */
	@Test
	public void testToStringWithGrid() {
		final int x = RandomHelper.nextIntFromTo(0, Integer.MAX_VALUE);
		final int y = RandomHelper.nextIntFromTo(0, Integer.MAX_VALUE);
		final double foodProductionRate = RandomHelper.nextDoubleFromTo(0,
				Double.MAX_VALUE);

		final CellData data = new CellData(x, y, foodProductionRate);

		Assert.assertNotNull(data.toString());
	}

}
