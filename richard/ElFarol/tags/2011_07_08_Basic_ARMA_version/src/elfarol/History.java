/*
 * Version info:
 *     $HeadURL$
 *     $LastChangedDate$
 *     $LastChangedRevision$
 *     $LastChangedBy$
 */
package elfarol;

import static elfarol.ParameterWrapper.getAgentsNumber;
import static elfarol.ParameterWrapper.getMemorySize;

import java.util.LinkedList;
import java.util.List;

import repast.simphony.random.RandomHelper;

/**
 * @author Richard O. Legendi (richard.legendi)
 * @since 2.0-beta, 2011
 * @version $Id$
 */
public class History {
	private final LinkedList<Integer> history = new LinkedList<Integer>();

	void init() {
		// Dispose any previous elements
		history.clear();

		// Fill up history with random previous elements
		for (int i = 0; i < 2 * getMemorySize(); ++i) { // Check
														// limit,
														// size
			history.add(RandomHelper.nextIntFromTo(0, getAgentsNumber()));
		}

	}

	private History() {
		init();
	}

	// Temporal workarond
	private static final History historyInstance = new History();

	public static History getInstance() {
		return historyInstance;
	}

	public void updateHistory() {
		history.removeLast();// Remove last element
		history.addFirst(Utils.getAttendance()); // Add first element
	}

	public int getAttendance(final int idx) {
		return history.get(idx);
	}

	public List<Integer> getSubHistory(final int week) {
		return history.subList(week, week + getMemorySize()); // OK
	}

	public List<Integer> getMemoryBoundedSubHistory() {
		return history.subList(0, getMemorySize());
	}

	@Override
	public String toString() {
		return "History [history=" + history + "]";
	}

}
