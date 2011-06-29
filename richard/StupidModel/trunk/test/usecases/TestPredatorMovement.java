/*
 * Version info:
 *     $HeadURL$
 *     $LastChangedDate$
 *     $LastChangedRevision$
 *     $LastChangedBy$
 */
package usecases;

import static stupidmodel.common.TestUtils.TEST_GRID_SIZE;
import static stupidmodel.common.TestUtils.initContext;
import static stupidmodel.common.TestUtils.initGrid;
import static stupidmodel.common.TestUtils.tr;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;

import repast.simphony.context.Context;
import repast.simphony.random.RandomHelper;
import repast.simphony.space.grid.Grid;
import repast.simphony.space.grid.GridPoint;
import repast.simphony.space.grid.GridPointTranslator;
import repast.simphony.space.grid.StrictBorders;
import repast.simphony.space.grid.WrapAroundBorders;
import stupidmodel.agents.Bug;
import stupidmodel.agents.Predator;
import stupidmodel.common.Constants;

/**
 * Minimal artificial simulation snapshots to check if the {@link Predator}
 * actions like hunting works properly.
 * 
 * @author Richard O. Legendi (richard.legendi)
 * @since 2.0-beta, 2011
 * @version $Id$
 */
public class TestPredatorMovement {

	/**
	 * Test if a {@link Predator} movement performed correctly when every cell
	 * in the neighbourhood is empty.
	 */
	private void testMovementWhenNothingNearbyExperiment(
			final GridPointTranslator borders) {
		final int experimentCount = 9 * Constants.PREDATOR_VISION_RANGE;

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

	/**
	 * Test a random move action: the result location may be anything within a
	 * distance specified by {@link Constants#PREDATOR_VISION_RANGE}
	 * <i>except</i> the original location.
	 */
	private void testMovementWhenNothingNearby(final GridPointTranslator borders) {
		// Given
		final Context<Object> context = initContext();
		final Grid<Object> grid = initGrid(borders, context);

		final int bugX = RandomHelper.nextIntFromTo(0, TEST_GRID_SIZE - 1);
		final int bugY = RandomHelper.nextIntFromTo(0, TEST_GRID_SIZE - 1);

		final Predator predator = new Predator();
		context.add(predator);
		grid.moveTo(predator, bugX, bugY);

		// When
		predator.hunt();

		// Then
		final GridPoint location = grid.getLocation(predator);

		final ArrayList<GridPoint> expectedLocations = new ArrayList<GridPoint>();
		for (int i = bugX - Constants.PREDATOR_VISION_RANGE; i <= bugX
				+ Constants.PREDATOR_VISION_RANGE; ++i) {
			for (int j = bugY - Constants.PREDATOR_VISION_RANGE; j <= bugY
					+ Constants.PREDATOR_VISION_RANGE; ++j) {
				final GridPoint act = new GridPoint(tr(i), tr(j));
				expectedLocations.add(act);
			}
		}

		Assert.assertTrue(location + " not in " + expectedLocations,
				expectedLocations.contains(location));
	}

	/**
	 * Test a {@link Predator} hunt action when there is a bug nearby.
	 */
	@Test
	public void testMovementWhenBugIsNereby() {
		// Given
		final Context<Object> context = initContext();
		final Grid<Object> grid = initGrid(new StrictBorders(), context);

		final Predator predator = new Predator();
		context.add(predator);
		grid.moveTo(predator, 5, 5);

		final Bug bug = new Bug();
		context.add(bug);
		grid.moveTo(bug, 4, 4);

		final GridPoint bugLocation = grid.getLocation(bug);

		// When
		predator.hunt();

		// Then
		final GridPoint newLocation = grid.getLocation(predator);

		// Predator should hunt down the bug
		Assert.assertEquals(bugLocation, newLocation);
		Assert.assertFalse(context.contains(bug));
	}

	/**
	 * Test a {@link Predator} hunt action when there no free cell nereby.
	 */
	@Test
	public void testMovementWhenNoEmptyPlaceNearby() {
		// Given
		final Context<Object> context = initContext();
		final Grid<Object> grid = initGrid(new StrictBorders(), context);

		final Predator predator = new Predator();
		context.add(predator);
		grid.moveTo(predator, 5, 5);

		for (int i = 4; i <= 6; ++i) {
			for (int j = 4; j <= 6; ++j) {
				if (5 == i && 5 == j) {
					continue;
				}

				final Predator act = new Predator();
				context.add(act);
				grid.moveTo(act, i, j);
			}
		}

		final GridPoint prevLocation = grid.getLocation(predator);

		// When
		predator.hunt();

		// Then
		final GridPoint newLocation = grid.getLocation(predator);

		// Predator should hunt down the bug
		Assert.assertEquals(prevLocation, newLocation);
	}

	/**
	 * Test a {@link Predator} hunt action when there is nothing nearby.
	 */
	@Test
	public void testMovementWhenNothingNereby() {
		// Given
		final Context<Object> context = initContext();
		final Grid<Object> grid = initGrid(new StrictBorders(), context);

		final Predator predator = new Predator();
		context.add(predator);
		grid.moveTo(predator, 5, 5);

		// When
		predator.hunt();

		// Then
		final GridPoint newLocation = grid.getLocation(predator);
		final int x = newLocation.getX();
		final int y = newLocation.getY();

		// Predator should move to any random position within range
		Assert.assertTrue(4 <= x && x <= 6 && 4 <= y && y <= 6);
	}

}
