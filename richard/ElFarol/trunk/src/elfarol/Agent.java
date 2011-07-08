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
 * @author Richard O. Legendi (richard.legendi)
 * @since 2.0-beta, 2011
 * @version $Id$
 */
public class Agent {
	private final List<AStrategy> strategies = new ArrayList<AStrategy>();

	private AStrategy bestStrategy = null;
	private boolean attend = false;

	public Agent() {
		for (int i = 0, n = getStrategiesNumber(); i < n; ++i) {
			strategies.add(new RandomStrategy());
		}

		bestStrategy = strategies.get(0); // Choose the first one initially
		updateStrategies();
	}

	public boolean isAttending() {
		return attend;
	}

	private double score(final AStrategy strategy) {
		double ret = 0.0;
		for (int i = 0; i < getMemorySize(); ++i) {
			final int week = i + 1;
			final double currentAttendance = History.getInstance()
					.getAttendance(i);
			final double prediction = predictAttendance(strategy, History
					.getInstance().getSubHistory(week));

			ret += Math.abs(currentAttendance - prediction);
		}

		return ret;
	}

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

	public void updateStrategies() {
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

	public void updateAttendance() {
		final double prediction = predictAttendance(bestStrategy, History
				.getInstance().getMemoryBoundedSubHistory());

		attend = (prediction <= getOvercrowdingThreshold());
	}

}
