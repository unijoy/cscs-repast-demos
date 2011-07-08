/*
 * Version info:
 *     $HeadURL$
 *     $LastChangedDate$
 *     $LastChangedRevision$
 *     $LastChangedBy$
 */
package elfarol.strategies;

import static elfarol.ParameterWrapper.getMemorySize;

import java.util.Arrays;

/**
 * @author Richard O. Legendi (richard.legendi)
 * @since 2.0-beta, 2011
 * @version $Id$
 */
public abstract class AStrategy {
	protected final double[] weights;

	protected AStrategy(final double[] weights) {
		super();

		assert (getMemorySize() + 1 == weights.length);

		this.weights = weights;
	}

	// Assertative
	public int size() {
		return weights.length;
	}

	public double getWeight(final int i) {
		return weights[i];
	}

	@Override
	public String toString() {
		return "Strategy [weights=" + Arrays.toString(weights) + "]";
	}

}
