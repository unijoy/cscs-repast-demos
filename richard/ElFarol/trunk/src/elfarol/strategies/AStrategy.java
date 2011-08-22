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
 * Abstract superclass of agent strategies.
 * 
 * <p>
 * In the current implementation of the model, each agent has a set of randomly
 * initialized autoregressive models (sequences of autoregressive coefficients)
 * that are not fitted.
 * </p>
 * 
 * @author Richard O. Legendi (richard.legendi)
 * @since 2.0-beta, 2011
 * @version $Id: AStrategy.java 514 2011-07-08 14:50:51Z
 *          richard.legendi@gmail.com $
 */
public abstract class AStrategy {

	/** Weights used by the current strategy. */
	protected final double[] weights;

	/**
	 * Creates a new instance by setting the weights of the class.
	 * 
	 * @param weights
	 *            the array containing the weights of the strategy; should
	 *            contain exactly <code>memorySize + 1</code> elements
	 */
	protected AStrategy(final double[] weights) {
		super();
		assert (getMemorySize() + 1 == weights.length);

		this.weights = weights;
	}

	/**
	 * Returns the size of the associated weights.
	 * 
	 * <i>Used only for assert statements to ensure correct program state.</i>
	 * 
	 * @return the length of the associated <code>weights</code> array;
	 *         non-negative
	 */
	public int size() {
		return weights.length;
	}

	/**
	 * Returns the weight to use for the prediction of the specified week.
	 * 
	 * @param week
	 *            the number of week whose weight should be returned;
	 *            <code>0 <= week <= size()</code>
	 * @return the associated AR weight value for the specified week
	 */
	public double getWeight(final int week) {
		return weights[week];
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		// Just for a nice output when debugging
		return "Strategy [weights=" + Arrays.toString(weights) + "]";
	}

}
