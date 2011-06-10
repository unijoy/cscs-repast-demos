/*
 * Version info:
 *     $HeadURL$
 *     $LastChangedDate$
 *     $LastChangedRevision$
 *     $LastChangedBy$
 */
package stupidmodel.agents;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

/**
 * Simple tests for the created {@link Bug} agents.
 * 
 * @author Richard O. Legendi (richard.legendi)
 * @since 2.0-beta, 2011
 * @version $Id$
 */
public class TestBug {

	/**
	 * Making sure if the default comparison works correctly, and sorting
	 * returns an agent list in descending size order.
	 */
	@Test
	public void testComparison() {
		final Bug b1 = new Bug();
		final Bug b2 = new Bug();

		b1.setSize(5.0);
		b2.setSize(10.0);

		final List<Bug> list = Arrays.asList(b1, b2);
		Collections.sort(list);

		Assert.assertEquals(b2, list.get(0));
		Assert.assertEquals(b1, list.get(1));
	}
}
