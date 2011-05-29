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

import stupidmodel.Bug;
import stupidmodel.observer.BugStyleOGL2D;

/**
 * Simple tests for the created custom style.
 * 
 * <p>
 * Bug colors shade from white when size is zero to red.
 * </p>
 * 
 * @author Richard O. Legendi (richard.legendi)
 * @since 2.0-beta, 2011
 * @version $Id$
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
		Assert.assertEquals(Color.WHITE, color);
	}

	/**
	 * Test if returned color is white when size is <code>255</code>.
	 */
	@Test
	public void testRedColor() {
		final Bug bug = new Bug();
		bug.setSize(255);

		final Color color = style.getColor(bug);
		Assert.assertEquals(Color.RED, color);
	}

	/**
	 * Test if returned color is white when size is (intermediate state).
	 */
	@Test
	public void testPinkColor() {
		final Bug bug = new Bug();
		bug.setSize(80);

		final Color color = style.getColor(bug);
		Assert.assertEquals(Color.PINK, color);
	}

}
