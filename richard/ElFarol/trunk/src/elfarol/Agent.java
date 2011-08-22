/*
 * Version info:
 *     $HeadURL$
 *     $LastChangedDate$
 *     $LastChangedRevision$
 *     $LastChangedBy$
 */
package elfarol;

import static elfarol.ParameterWrapper.*;

import java.util.ArrayList;
import java.util.List;

import elfarol.strategies.AStrategy;
import elfarol.strategies.RandomStrategy;

/**
 * Defines an agent of the <i>El Farol</i> simulation.
 * 
 * <p>
 * Agents have a list of strategies (see {@link AStrategy}) and each turn they
 * try to predict the attendance level of the bar for the current time step, and
 * determine if they attend to the bar or not.
 * </p>
 * 
 * @author Richard O. Legendi (richard.legendi)
 * @since 2.0-beta, 2011
 * @version $Id: Agent.java 1092 2011-08-22 08:35:16Z richard.legendi@gmail.com
 *          $
 * @see AStrategy
 */
public class Agent {

	// ========================================================================
	// === Members ============================================================

	/** The list of strategy objects this agent may use for prediction. */
	private final List<AStrategy> strategies = new ArrayList<AStrategy>();

	/**
	 * The best strategy used so far.
	 * 
	 * <p>
	 * Initially it is set to the first one.
	 * </p>
	 */
	private AStrategy bestStrategy = null;

	/**
	 * A boolean flag that shows if the agent is attending the bar in the
	 * current time step.
	 */
	private boolean attend = false;

	/**
	 * Initializes a new agent instance with the
	 */
	public Agent() {
		for (int i = 0, n = getStrategiesNumber(); i < n; ++i) {
			strategies.add(new RandomStrategy());
		}

		bestStrategy = strategies.get(0); // Choose the first one initially
		updateBestStrategy();
	}

	/**
	 * Returns the value of <code>attend</code>.
	 * 
	 * @return <code>true</code> if the agent attends the bar in the current
	 *         time step (<i>if</i> called after the {@link #updateAttendance()}
	 *         function); <code>false</code> otherwise
	 */
	public boolean isAttending() {
		return attend;
	}

	// ========================================================================
	// === Utility Functions ==================================================

	/**
	 * Evaluates the fitness value of the specified strategy based on the
	 * current knowledge of the agent (i.e., based on the current state recorded
	 * in the memory of the agent).
	 * 
	 * <p>
	 * <i>The smaller value returned by this function the better the strategy
	 * is.</i> The value is constructed by summing up the differences between
	 * the actual and predicted value the past <code>memorySize</code> weeks
	 * based on the current strategy. The predicted value is determined by the
	 * {@link #predictAttendance(AStrategy, List)} function.
	 * </p>
	 * 
	 * @param strategy
	 *            the strategy to evaluate; <i>cannot be <code>null</code></i>
	 * @return the difference between the predicted and actual attendance level
	 *         of the last <code>memorySize</code> weeks; non-negative
	 */
	private double score(final AStrategy strategy) {
		if (null == strategy) {
			throw new IllegalArgumentException("strategy == null");
		}

		double ret = 0.0;
		for (int i = 0; i < getMemorySize(); ++i) {
			final int week = i + 1;
			final double currentAttendance = History.getInstance()
					.getAttendance(i);
			final double prediction = predictAttendance(strategy, History
					.getInstance().getSubHistory(week));

			ret += Math.abs(currentAttendance - prediction);
		}

		assert (ret >= 0);
		return ret;
	}

	/**
	 * Returns the predicted attendance level of the bar with the specified
	 * strategy for the given history time window.
	 * 
	 * <p>
	 * It uses an autoregressive model with <code>c = 1</code>. Prediction is
	 * determined by the function requires the current strategy and the list of
	 * attendance values preceding the week<sup>th</sup> element of history.
	 * Formally, the function should return the following value
	 * <code>p(t)</code> prediction described in the original model:
	 * </p>
	 * 
	 * <pre>
	 * p(t) = w(t) + sum_{i=t-1}^{t-M}w(i)*a(i-1)
	 * </pre>
	 * 
	 * <p>
	 * where <code>t</code> is the current time, <code>w(i)</code> is the
	 * weight, <code>a(i)</code> is the attendance level and <code>M</code> is
	 * the memory size.
	 * <p>
	 * 
	 * @param strategy
	 *            the strategy to predict the attendance with
	 * @param subhistory
	 *            the time window of the history to create a prediction from
	 * @return the prediction based on the previous attendance levels based on
	 *         the described formulae
	 */
	private double predictAttendance(final AStrategy strategy,
			final List<Integer> subhistory) {
		assert (strategy.size() - 1 == subhistory.size());

		// Last one is considered with a weight of 1.0
		double ret = strategy.getWeight(0);

		// Start from the second one (where index is 1)
		for (int i = 1; i < strategy.size(); ++i) {
			ret += strategy.getWeight(i) * subhistory.get(i - 1);
		}

		return ret;
	}

	// ========================================================================
	// === Public Interface ===================================================

	/**
	 * Makes the agent evaluate all the strategies and if any of them is better
	 * than the previously used one it is updated. A threshold level of
	 * <code>memorySize * agentsNumber + 1</code> is also considered.
	 */
	public void updateBestStrategy() {
		// Defined threshold level
		double minScore = getMemorySize() * getAgentsNumber() + 1;

		for (final AStrategy strategy : strategies) {
			final double score = score(strategy);
			if (score < minScore) {
				minScore = score;
				bestStrategy = strategy;
			}
		}
	}

	/**
	 * Makes the agent update its attendance level based on its attendance
	 * prediction by its best evaluated strategy.
	 */
	public void updateAttendance() {
		final double prediction = predictAttendance(bestStrategy, History
				.getInstance().getMemoryBoundedSubHistory());

		attend = (prediction <= getOvercrowdingThreshold());
	}

}
