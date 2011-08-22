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
 * In the current implementation of the model, each agent has a set of randomly
 * initialized autoregressive models (sequences of AR coefficients) that are not
 * fitted.
 * 
 * @author Richard O. Legendi (richard.legendi)
 * @since 2.0-beta, 2011
 * @version $Id: AStrategy.java 514 2011-07-08 14:50:51Z
 *          richard.legendi@gmail.com $
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
