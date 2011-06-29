/*
 * Version info:
 *     $HeadURL$
 *     $LastChangedDate$
 *     $LastChangedRevision$
 *     $LastChangedBy$
 */
package stupidmodel.common;

import static stupidmodel.common.TestUtils.TEST_GRID_SIZE;
import repast.simphony.context.Context;
import repast.simphony.context.DefaultContext;
import repast.simphony.context.space.grid.GridFactoryFinder;
import repast.simphony.engine.environment.RunState;
import repast.simphony.random.RandomHelper;
import repast.simphony.space.grid.Grid;
import repast.simphony.space.grid.GridBuilderParameters;
import repast.simphony.space.grid.GridPointTranslator;
import repast.simphony.space.grid.SimpleGridAdder;
import repast.simphony.space.grid.StrictBorders;
import stupidmodel.agents.HabitatCell;

/**
 * Defines a set of constants and utility functions required only during the
 * testing.
 * 
 * @author Richard O. Legendi (richard.legendi)
 * @since 2.0-beta, 2011
 * @version $Id$
 */
public class TestUtils {

	/** Grid size used for testing. */
	public static final int TEST_GRID_SIZE = 10;

	/**
	 * Minor utility function to transform the coordinates in the test for a
	 * toric world.
	 * 
	 * @param i
	 *            coordinate to translate
	 * @return translated coordinate, ensured to be on the interval
	 *         <code>[0, TEST_GRID_SIZE)</code>
	 */
	public static int tr(int i) {
		if (i < 0) {
			return i += TEST_GRID_SIZE;
		}

		if (TEST_GRID_SIZE <= i) {
			return i -= TEST_GRID_SIZE;
		}

		return i;
	}

	/**
	 * Create a dummy context used for testing.
	 * 
	 * @return an initialized context object to use (an instance of
	 *         <code>DefaultContext</code>)
	 */
	public static Context<Object> initContext() {
		final Context<Object> context = new DefaultContext<Object>();
		RunState.init().setMasterContext(context);
		return context;
	}

	/**
	 * Creates a properly initialized grid used by the simulation, but
	 * <i>without</i> any other agents associated, using strict border rules by
	 * default.
	 * 
	 * @param context
	 *            the context to assign the grid instance to
	 * @return a grid instance used by the simulation, without any agent
	 *         instances on it
	 */
	public static Grid<Object> initEmptyGrid(final Context<Object> context) {
		return initEmptyGrid(new StrictBorders(), context);
	}

	/**
	 * Creates a properly initialized grid used by the simulation, but
	 * <i>without</i> any other agents associated, using the specified grid
	 * point translator for the borders.
	 * 
	 * @param context
	 *            the context to assign the grid instance to
	 * @param borders
	 *            the current grid point translator to use
	 * @return a grid instance used by the simulation, without any agent
	 *         instances on it
	 */
	public static Grid<Object> initEmptyGrid(final GridPointTranslator borders,
			final Context<Object> context) {
		final Grid<Object> grid = GridFactoryFinder.createGridFactory(null)
				.createGrid(
						Constants.GRID_ID,
						context,
						new GridBuilderParameters<Object>(borders,
								new SimpleGridAdder<Object>(), true,
								TEST_GRID_SIZE, TEST_GRID_SIZE));
		return grid;
	}

	/**
	 * Initializes a grid used by the simulation populated with
	 * {@link HabitatCell} instances.
	 * 
	 * @param context
	 *            the context to assign the grid instance to
	 * @return a grid instance used by the simulation, populated with cell agent
	 *         instances on it
	 */
	public static Grid<Object> initGrid(final Context<Object> context) {
		return initGrid(new StrictBorders(), context);
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
	public static Grid<Object> initGrid(final GridPointTranslator borders,
			final Context<Object> context) {
		final Grid<Object> grid = initEmptyGrid(borders, context);

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
}
