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
 * @author Richard O. Legendi (richard.legendi)
 * @since 2.0-beta, 2011
 * @since Model 16
 * @version $Id$
 */
public class Predator {
	public Predator() {
		super();
	}

	/**
	 * Scheduled after all the {@link Bug} actions.
	 */
	@ScheduledMethod(start = 1, interval = 1, priority = -1)
	public void hunt() {
		// Reference for the used grid
		final Grid<Object> grid = getGrid();
		// Get the grid location of this Bug
		final GridPoint location = grid.getLocation(this);

		// TODO Make constants

		// First, a predator looks through a shuffled list of its immediately
		// neighboring cells (including its own cell).
		// TODO Test if 1 neighbourhood not 0
		final List<GridCell<HabitatCell>> cellNeighborhood = new GridCellNgh<HabitatCell>(
				grid, location, HabitatCell.class, 1, 1).getNeighborhood(true);

		SimUtilities.shuffle(cellNeighborhood, RandomHelper.getUniform());

		// As soon as the predator finds a bug in one of these cells it "kills"
		// the bug and moves into the cell.

		for (final GridCell<HabitatCell> cell : cellNeighborhood) {
			if (hasAgent(grid, cell, Bug.class)) {
				// (However, if the cell already contains a predator, the
				// hunting predator simply quits and remains at its current
				// location.)
				if (hasAgent(grid, cell, Predator.class)) {
					return;
				}

				killBugAt(grid, cell);
				moveTo(grid, cell);
				return;
			}
		}

		// If these cells contain no bugs, the predator moves randomly to one of
		// them.
		final GridCell<HabitatCell> randomCell = SMUtils
				.randomElementOf(cellNeighborhood);
		moveTo(grid, randomCell);
	}

	private <T> boolean hasAgent(final Grid<Object> grid,
			final GridCell<HabitatCell> cell, final Class<T> clazz) {
		assert (grid != null);
		assert (cell != null);

		final List<GridCell<T>> neighborhood = new GridCellNgh<T>(grid,
				cell.getPoint(), clazz, 0, 0).getNeighborhood(true);

		assert (neighborhood != null);
		assert (1 == neighborhood.size());

		final int ctr = neighborhood.get(0).size();

		return (ctr > 0);
	}

	private void killBugAt(final Grid<Object> grid,
			final GridCell<HabitatCell> cell) {
		assert (grid != null);
		assert (cell != null);
		assert (hasAgent(grid, cell, Bug.class));

		final List<GridCell<Bug>> bugsAt = new GridCellNgh<Bug>(grid,
				cell.getPoint(), Bug.class, 0, 0).getNeighborhood(true);

		assert (1 == bugsAt.size());
		bugsAt.get(0).items().iterator().next().die();
	}

	private void moveTo(final Grid<Object> grid,
			final GridCell<HabitatCell> cell) {
		assert (grid != null);
		assert (cell != null);

		// We have our new GridPoint to move to, so relocate agent
		final GridPoint newGridPoint = cell.getPoint();
		grid.moveTo(this, newGridPoint.getX(), newGridPoint.getY());
	}

	/**
	 * Returns a reference to the grid on which the agent is located at.
	 * 
	 * @return the <code>Grid</code> on which the agent is located; <i>cannot be
	 *         <code>null</code></i>
	 * @since Model 2
	 */
	// TODO Generalize this
	protected Grid<Object> getGrid() {
		@SuppressWarnings("unchecked")
		final Grid<Object> grid = (Grid<Object>) ContextUtils.getContext(this)
				.getProjection(Constants.GRID_ID);

		if (null == grid) {
			throw new IllegalStateException("Cannot locate grid in context.");
		}

		return grid;
	}

}
