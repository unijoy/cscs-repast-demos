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

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import repast.simphony.random.RandomHelper;

/**
 * A simple utility class that is designed to handle the history (i.e., the
 * attendance level of the previous <code>2 * memorySize</code> week). Only a
 * singleton instance should exist.
 * 
 * @author Richard O. Legendi (richard.legendi)
 * @since 2.0-beta, 2011
 * @version $Id: History.java 514 2011-07-08 14:50:51Z richard.legendi@gmail.com
 *          $
 */
public class History {

	/** A list containing the previous attendance levels. */
	private final LinkedList<Integer> history = new LinkedList<Integer>();

	/**
	 * Reinitializes the history with random elements (with natural random
	 * numbers between <code>[0, agentsNumber]</code>.
	 * 
	 * <p>
	 * It is declared package-private since it is required only during
	 * initialization in the class
	 * {@link ElFarolContextBuilder#build(repast.simphony.context.Context)}.
	 * </p>
	 */
	/* package-private */
	void init() {
		// Dispose any previous elements
		history.clear();

		// Fill up history with random previous elements
		for (int i = 0; i < 2 * getMemorySize(); ++i) {
			history.add(RandomHelper.nextIntFromTo(0, getAgentsNumber()));
		}

	}

	/** Singleton instance. */
	private static final History historyInstance = new History();

	/**
	 * Private constructor that also initializes the previous history elements.
	 */
	private History() {
		init();
	}

	// ------------------------------------------------------------------------

	/**
	 * Return the currently existing <code>History</code> instance.
	 * 
	 * @return the current <code>History</code> instance; not <code>null</code>
	 */
	public static History getInstance() {
		return historyInstance;
	}

	/**
	 * Updating the history appends the actual attendance level to the beginning
	 * of the list and drops the last element to fix the size of history.
	 */
	public void updateHistory() {
		history.removeLast();// Remove last element
		history.addFirst(Utils.getAttendance()); // Add first element
	}

	/**
	 * Returns the attendance level recorded in the history for the specified
	 * week.
	 * 
	 * @param week
	 *            the week whose attendance level should be returned;
	 *            <code>0 <= week <= 2 * memorySize</code>
	 * @return the attendance level of the specified week recorded in the
	 *         history
	 */
	public int getAttendance(final int week) {
		return history.get(week);
	}

	/**
	 * Returns an unmodifiable view of history time window from the specified
	 * week until an agent can remember for it.
	 * 
	 * @param week
	 *            the beginning of the time frame
	 * @return practically the returned time window subview means the sublist of
	 *         history from <code>[week, week + memorySize]</code>
	 */
	public List<Integer> getSubHistory(final int week) {
		return Collections.unmodifiableList(history.subList(week, week
				+ getMemorySize()));
	}

	/**
	 * Returns an unmodifiable view of history time window for the actual week
	 * until an agent can remember for it.
	 * 
	 * @return practically the returned time window subview means the sublist of
	 *         history from <code>[0, 0 + memorySize]</code>
	 */
	public List<Integer> getMemoryBoundedSubHistory() {
		return Collections
				.unmodifiableList(history.subList(0, getMemorySize()));
	}

	// ========================================================================
	// === Super Interface ====================================================

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		// Justs for debugging for a nice output
		return "History [history=" + history + "]";
	}

}
