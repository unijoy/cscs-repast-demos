/*
 * Version info:
 *     $HeadURL$
 *     $LastChangedDate$
 *     $LastChangedRevision$
 *     $LastChangedBy$
 */
package apitest;

import junit.framework.Assert;

import org.junit.Test;

import repast.simphony.random.RandomHelper;

/**
 * Simple test cases to make assertions how the <i>Repast Simphony Java API</i>
 * works.
 * 
 * @author Richard O. Legendi (richard.legendi)
 * @since 2.0-beta, 2011
 * @version $Id$
 */
public class TestRepastAPI {

	/**
	 * Number of experiments to perform for each assertion.
	 */
	private static final int EXPERIMENTS = 10 * 1000;

	/**
	 * Assert that by using the default <code>RandomHelper</code> uniform
	 * distribution, the returned values are from the interval
	 * <code>[0,1]</code>.
	 */
	@Test
	public void testDefaultRandomHelperUniformDistribution() {
		for (int i = 0; i < EXPERIMENTS; ++i) {
			final double d = RandomHelper.nextDouble();
			Assert.assertTrue(0.0 <= d);
			Assert.assertTrue(d <= 1.0);
		}
	}

}
