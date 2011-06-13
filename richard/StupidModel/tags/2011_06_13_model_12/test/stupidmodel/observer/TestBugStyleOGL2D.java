/*
 * Version info:
 *     $HeadURL$
 *     $LastChangedDate$
 *     $LastChangedRevision$
 *     $LastChangedBy$
 */
package stupidmodel.observer;

import java.awt.Color;

import org.junit.Assert;
import org.junit.Test;

import stupidmodel.agents.Bug;

/**
 * Simple tests for the created custom style.
 * 
 * <p>
 * Bug colors shade from white when size is zero to red.
 * </p>
 * 
 * @author Richard O. Legendi (richard.legendi)
 * @since 2.0-beta, 2011
 * @version $Id: TestBugStyleOGL2D.java 183 2011-05-29 17:09:27Z
 *          richard.legendi@gmail.com $
 */
public class TestBugStyleOGL2D {

	/** Style object to test. */
	private final BugStyleOGL2D style = new BugStyleOGL2D();

	/**
	 * Test if returned color is white when size is <code>0</code>.
	 */
	@Test
	public void testWhiteColor() {
		final Bug bug = new Bug();
		bug.setSize(0);

		final Color color = style.getColor(bug);

		// Color "white" was modified to be able to see zero-sized bug agents on
		// empty cells (which are also white)
		Assert.assertEquals(new Color(255, 200, 200), color);
	}

	/**
	 * Test if returned color is red when size is <code>255</code>.
	 */
	@Test
	public void testRedColor() {
		final Bug bug = new Bug();
		bug.setSize(255);

		final Color color = style.getColor(bug);
		Assert.assertEquals(Color.RED, color);
	}

	/**
	 * Test if returned color is pink when size is (intermediate state).
	 */
	@Test
	public void testPinkColor() {
		final Bug bug = new Bug();
		// Results in color strength 175, which is color pink
		bug.setSize(1.25);

		final Color color = style.getColor(bug);
		Assert.assertEquals(Color.PINK, color);
	}

}
