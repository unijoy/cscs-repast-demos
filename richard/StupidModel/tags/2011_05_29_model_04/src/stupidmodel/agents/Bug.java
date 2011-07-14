/*
 * Version info:
 *     $HeadURL$
 *     $LastChangedDate$
 *     $LastChangedRevision$
 *     $LastChangedBy$
 */
package stupidmodel.agents;

import java.util.List;

import repast.simphony.engine.schedule.ScheduledMethod;
import repast.simphony.parameter.Parameter;
import repast.simphony.query.space.grid.GridCell;
import repast.simphony.query.space.grid.GridCellNgh;
import repast.simphony.random.RandomHelper;
import repast.simphony.space.grid.Grid;
import repast.simphony.space.grid.GridPoint;
import repast.simphony.util.ContextUtils;
import repast.simphony.util.SimUtilities;
import stupidmodel.common.Constants;
import stupidmodel.common.SMUtils;

/**
 * Bug agent implementation for <i>StupidModel 1</i>.
 * 
 * <p>
 * Agents in this model are very simple: they can access their neihgbourhood at
 * a grid and move to a random position within sight range (that is a fixed
 * constant for now, see {@link Constants#BUG_VISION_RANGE}.
 * </p>
 * 
 * <p>
 * If there is no free cell around the agent, it cannot move, so it stays in its
 * current position.
 * </p>
 * 
 * @author Richard O. Legendi (richard.legendi)
 * @since 2.0-beta, 2011
 * @version $Id$
 */
public class Bug {

	/**
	 * Bugs have an instance variable for their size, which is initialized to
	 * 1.0.
	 * 
	 * @since Model 2
	 */
	private double size = 1.0;

	/**
	 * Creates a new instance of <code>Bug</code> associated with the specified
	 * {@link Grid}.
	 */
	public Bug() {
		super();
	}

	/**
	 * Returns a reference to the grid on which the agent is located at.
	 * 
	 * @return the <code>Grid</code> on which the agent is located; <i>cannot be
	 *         <code>null</code></i>
	 * @since Model 2
	 */
	public Grid<Object> getGrid() {
		@SuppressWarnings("unchecked")
		final Grid<Object> grid = (Grid<Object>) ContextUtils.getContext(this)
				.getProjection(Constants.GRID_ID);

		if (null == grid) {
			throw new IllegalStateException("Cannot locate grid in context.");
		}

		return grid;
	}

	/**
	 * Returns the size of the bug.
	 * 
	 * @return the size of the bug
	 * @since Model 2
	 */
	@Parameter(displayName = "Bug Size", usageName = "size")
	public double getSize() {
		return size;
	}

	/**
	 * Sets the size of the bug, <i>used for testing</i>.
	 * 
	 * @param size
	 *            the new size of the bug
	 * @since Model 2
	 */
	public void setSize(final double size) {
		if (size < 0) {
			throw new IllegalArgumentException(String.format(
					"Parameter foodAvailability = %f < 0.", size));
		}

		this.size = size;
	}

	/**
	 * Implementation of the agent activity in each turn.
	 * 
	 * <p>
	 * Using the annotation {@link ScheduledMethod} makes this
	 * <code>step()</code> method executed from the first simulation tick, and
	 * with specifying interval it is executed each tick afterwards.
	 * </p>
	 * 
	 * <p>
	 * Agents work in a very simple way: they gather their neighbourhood and
	 * check for empty locations. If any is found, one of them is randomly
	 * chosen and the agent is relocated to that location.
	 * </p>
	 */
	@ScheduledMethod(start = 1, interval = 1, priority = 0)
	public void step() {
		// Get the grid location of this Bug
		final GridPoint location = getGrid().getLocation(this);

		// We use the GridCellNgh class to create GridCells for the surrounding
		// neighborhood. It contains the locations, and a list of objects from
		// the specified class which is accessible from that location

		final List<GridCell<Bug>> bugNeighborhood = new GridCellNgh<Bug>(
				getGrid(), location, Bug.class, Constants.BUG_VISION_RANGE,
				Constants.BUG_VISION_RANGE).getNeighborhood(false);

		// We have a utility function that returns the filtered list of empty
		// GridCells objects
		final List<GridCell<Bug>> freeCells = SMUtils
				.getFreeGridCells(bugNeighborhood);

		// Model specifies if there is no empty location in vision range, the
		// Bug agent cannot move
		if (freeCells.isEmpty()) {
			return;
		}

		// CHECKME Is it needed?
		SimUtilities.shuffle(freeCells, RandomHelper.getUniform());

		// Get a random free location within sight range
		final GridCell<Bug> chosenFreeCell = SMUtils.randomElementOf(freeCells);

		// We have our new GridPoint to move to, so relocate agent
		final GridPoint newGridPoint = chosenFreeCell.getPoint();
		getGrid().moveTo(this, newGridPoint.getX(), newGridPoint.getY());
	}

	/**
	 * Each time step, a bug grows by a fixed amount, <code>1.0</code>, and this
	 * action is scheduled after the <code>move()</code> action.
	 * 
	 * @since Model 2
	 */
	@ScheduledMethod(start = 1, interval = 1, priority = -1)
	public void grow() {
		size += Constants.BUG_GROWTH_RATE;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		// This may happen when testing
		final String location = (ContextUtils.getContext(this) != null) ? getGrid()
				.getLocation(this).toString() : "[?, ?]";

		// Override default Java implementation just to have a nicer
		// representation
		return String.format("Bug @ location %s, size=%f", location, size);
	}

}
