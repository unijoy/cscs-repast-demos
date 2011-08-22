/*
 * Version info:
 *     $HeadURL$
 *     $LastChangedDate$
 *     $LastChangedRevision$
 *     $LastChangedBy$
 */
package elfarol.strategies;

import static elfarol.ParameterWrapper.getMemorySize;
import repast.simphony.random.RandomHelper;

/**
 * Simple subclass of {@link AStrategy} that contains random weights from the
 * interval <code>(-1, +1)</code>.
 * 
 * @author Richard O. Legendi (richard.legendi)
 * @since 2.0-beta, 2011
 * @version $Id: RandomStrategy.java 511 2011-07-08 14:38:37Z
 *          richard.legendi@gmail.com $
 */
public class RandomStrategy extends AStrategy {

	/**
	 * Simple utility function that creates an array of random weights.
	 * 
	 * @return an array with the length of <code>memorySize + 1</code>
	 *         containing random weights from the interval <code>(-1, +1)</code>
	 */
	private static double[] createRandomWeights() {
		final double[] ret = new double[getMemorySize() + 1];

		for (int i = 0; i < ret.length; ++i) {
			ret[i] = RandomHelper.nextDoubleFromTo(-1.0, +1.0);
		}

		return ret;
	}

	/**
	 * Creates a new instance of <code>RandomStrategy</code>.
	 */
	public RandomStrategy() {
		super(createRandomWeights());
	}

}
