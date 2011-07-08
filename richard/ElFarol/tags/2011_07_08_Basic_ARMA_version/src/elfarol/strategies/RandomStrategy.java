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
 * @author Richard O. Legendi (richard.legendi)
 * @since 2.0-beta, 2011
 * @version $Id$
 */
public class RandomStrategy extends AStrategy {

	private static double[] createRandomWeights() {
		final double[] ret = new double[getMemorySize() + 1]; // CHECKME

		for (int i = 0; i < ret.length; ++i) {
			ret[i] = RandomHelper.nextDoubleFromTo(-1.0, +1.0);
		}

		return ret;
	}

	public RandomStrategy() {
		super(createRandomWeights());
	}

}
