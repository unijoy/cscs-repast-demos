/*
 * Version info:
 *     $HeadURL$
 *     $LastChangedDate$
 *     $LastChangedRevision$
 *     $LastChangedBy$
 */
package usecases;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;

import repast.simphony.context.Context;
import repast.simphony.context.DefaultContext;
import repast.simphony.context.space.grid.GridFactoryFinder;
import repast.simphony.engine.environment.RunState;
import repast.simphony.random.RandomHelper;
import repast.simphony.space.grid.Grid;
import repast.simphony.space.grid.GridBuilderParameters;
import repast.simphony.space.grid.GridPoint;
import repast.simphony.space.grid.GridPointTranslator;
import repast.simphony.space.grid.SimpleGridAdder;
import repast.simphony.space.grid.StrictBorders;
import repast.simphony.space.grid.WrapAroundBorders;
import stupidmodel.agents.Bug;
import stupidmodel.agents.HabitatCell;
import stupidmodel.common.CellData;
import stupidmodel.common.Constants;
import stupidmodel.common.SMUtils;

/**
 * DOCME
 * 
 * @author Richard O. Legendi (richard.legendi)
 * @since 2.0-beta, 2011
 * @version $Id$
 */
public class TestBugMovement {

	/**
	 * Create a dummy context used for testing.
	 * 
	 * @return an initialized context object to use
	 */
	private Context<Object> initContext() {
		final Context<Object> context = new DefaultContext<Object>();
		RunState.init().setMasterContext(context);
		return context;
	}

	/**
	 * Create a dummy grid used for testing.
	 * 
	 * @param borders
	 *            border logic for the grid
	 * @param context
	 *            the created context to use for the grid
	 * @return an initialized grid populated with cell objects
	 */
	private Grid<Object> initGrid(final GridPointTranslator borders,
			final Context<Object> context) {
		final Grid<Object> grid = GridFactoryFinder.createGridFactory(null)
				.createGrid(
						Constants.GRID_ID,
						context,
						new GridBuilderParameters<Object>(borders,
								new SimpleGridAdder<Object>(), true,
								TEST_GRID_SIZE, TEST_GRID_SIZE));

		for (int i = 0; i < TEST_GRID_SIZE; ++i) {
			for (int j = 0; j < TEST_GRID_SIZE; ++j) {
				final HabitatCell cell = new HabitatCell(new CellData(i, j,
						RandomHelper.nextDouble()));

				context.add(cell);
				grid.moveTo(cell, i, j);
			}
		}

		return grid;
	}

	// ========================================================================

	/**
	 * Test if a {@link Bug} movement performed correctly when every cell in the
	 * neighbourhood is empty.
	 */
	private void testMovementWhenNothingNearbyExperiment(
			final GridPointTranslator borders) {
		final int experimentCount = Constants.BUG_VISION_RANGE
				* Constants.BUG_VISION_RANGE;

		for (int i = 0; i < experimentCount; ++i) {
			testMovementWhenNothingNearby(borders);
		}
	}

	/**
	 * Test movement with empty neighbourhood using strict world borders.
	 */
	@Test
	public void testMovementWhenNothingNearbyExperimentWithStrictBorders() {
		testMovementWhenNothingNearbyExperiment(new StrictBorders());
	}

	/**
	 * Test movement with empty neighbourhood using a toroidal world.
	 */
	@Test
	public void testMovementWhenNothingNearbyExperimentWithWrapAroundBorders() {
		testMovementWhenNothingNearbyExperiment(new WrapAroundBorders());
	}

	/** Grid size used for testing. */
	private static final int TEST_GRID_SIZE = 10;

	/**
	 * Test a random move action: the result location may be anything within a
	 * distance specified by {@link Constants#BUG_VISION_RANGE} <i>except</i>
	 * the original location.
	 */
	private void testMovementWhenNothingNearby(final GridPointTranslator borders) {
		// Given
		final Context<Object> context = initContext();
		final Grid<Object> grid = initGrid(borders, context);

		final int bugX = RandomHelper.nextIntFromTo(0, TEST_GRID_SIZE - 1);
		final int bugY = RandomHelper.nextIntFromTo(0, TEST_GRID_SIZE - 1);

		final Bug bug = new Bug();
		context.add(bug);
		grid.moveTo(bug, bugX, bugY);

		// When
		bug.step();

		// Then
		final GridPoint location = grid.getLocation(bug);

		final ArrayList<GridPoint> expectedLocations = new ArrayList<GridPoint>();
		for (int i = bugX - Constants.BUG_VISION_RANGE; i <= bugX
				+ Constants.BUG_VISION_RANGE; ++i) {
			for (int j = bugY - Constants.BUG_VISION_RANGE; j <= bugY
					+ Constants.BUG_VISION_RANGE; ++j) {
				// Skip original location (since there are plenty of locations
				// to move at)
				if (bugX == i && bugY == j) {
					continue;
				}

				final GridPoint act = new GridPoint(tr(i), tr(j));
				expectedLocations.add(act);
			}
		}

		Assert.assertTrue(location + " not in " + expectedLocations,
				expectedLocations.contains(location));
	}

	private int tr(int i) {
		if (i < 0) {
			return i += TEST_GRID_SIZE;
		}

		if (TEST_GRID_SIZE <= i) {
			return i -= TEST_GRID_SIZE;
		}

		return i;
	}

	/**
	 * Test if a {@link Bug} movement performed correctly when every cell in the
	 * neighbourhood is occupied.
	 */
	private void testMovementWithNoFreeCellsWithinRangeExperiment(
			final GridPointTranslator borders) {
		final int experimentCount = Constants.BUG_VISION_RANGE
				* Constants.BUG_VISION_RANGE;

		for (int i = 0; i < experimentCount; ++i) {
			testMovementWithNoFreeCellsWithinRange(borders);
		}
	}

	/**
	 * Test movement with full neighbourhood using strict world borders.
	 */
	@Test
	public void testMovementWithNoFreeCellsWithinRangeExperimentWithStrictBorders() {
		testMovementWithNoFreeCellsWithinRangeExperiment(new StrictBorders());
	}

	/**
	 * Test movement with full neighbourhood using a toroidal world.
	 */
	@Test
	public void testMovementWithNoFreeCellsWithinRangeExperimentWithWrapAroundBorders() {
		testMovementWithNoFreeCellsWithinRangeExperiment(new WrapAroundBorders());
	}

	/**
	 * Test if there is no location nearby (all of them is occupied), stay in
	 * current position, {@link Bug} agent stays in its original position.
	 */
	private void testMovementWithNoFreeCellsWithinRange(
			final GridPointTranslator borders) {
		// Given
		final Context<Object> context = initContext();
		final Grid<Object> grid = initGrid(borders, context);

		final ArrayList<Bug> bugs = new ArrayList<Bug>();

		for (int i = 0; i < TEST_GRID_SIZE; ++i) {
			for (int j = 0; j < TEST_GRID_SIZE; ++j) {
				final Bug act = new Bug();

				context.add(act);
				grid.moveTo(act, i, j);
				bugs.add(act);
			}
		}

		final Bug bug = SMUtils.randomElementOf(bugs); // Bug to test
		final GridPoint prevLocation = grid.getLocation(bug);

		// When
		bug.step();

		// Then
		final GridPoint newLocation = grid.getLocation(bug);

		// When there's no location nearby, stay in current position
		Assert.assertEquals(prevLocation, newLocation);
	}

}
