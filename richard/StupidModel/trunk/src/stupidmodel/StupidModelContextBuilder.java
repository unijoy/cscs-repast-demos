/*
 * Version info:
 *     $HeadURL$
 *     $LastChangedDate$
 *     $LastChangedRevision$
 *     $LastChangedBy$
 */
package stupidmodel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import repast.simphony.context.Context;
import repast.simphony.context.DefaultContext;
import repast.simphony.context.space.continuous.ContinuousSpaceFactoryFinder;
import repast.simphony.context.space.grid.GridFactoryFinder;
import repast.simphony.dataLoader.ContextBuilder;
import repast.simphony.engine.environment.RunEnvironment;
import repast.simphony.engine.environment.RunState;
import repast.simphony.engine.schedule.ScheduledMethod;
import repast.simphony.parameter.Parameters;
import repast.simphony.random.RandomHelper;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.continuous.NdPoint;
import repast.simphony.space.continuous.RandomCartesianAdder;
import repast.simphony.space.continuous.StrictBorders;
import repast.simphony.space.grid.Grid;
import repast.simphony.space.grid.GridBuilderParameters;
import repast.simphony.space.grid.SimpleGridAdder;
import repast.simphony.space.grid.WrapAroundBorders;
import repast.simphony.valueLayer.GridValueLayer;
import stupidmodel.agents.Bug;
import stupidmodel.agents.Bug.BugSizeComparator;
import stupidmodel.agents.HabitatCell;
import stupidmodel.agents.Predator;
import stupidmodel.common.CellData;
import stupidmodel.common.Constants;
import stupidmodel.common.SMUtils;
import cern.jet.random.Normal;

/**
 * Custom {@link ContextBuilder} implementation for <i>StupidModel</i> versions.
 * 
 * @author Richard O. Legendi (richard.legendi)
 * @since 2.0-beta, 2011
 * @version $Id: StupidModelContextBuilder.java 150 2011-05-26 19:06:40Z
 *          richard.legendi@gmail.com $
 */
public class StupidModelContextBuilder extends DefaultContext<Object> implements
		ContextBuilder<Object> {

	/**
	 * {@inheritDoc}
	 * 
	 * <p>
	 * We perform the initialization, specifically: (i) parse the cell data
	 * file, (ii) create the used space, grid instances, and initialize the
	 * value layer associated with the displayed grid, (iii) populate the grid
	 * with both {@link Bug} and {@link HabitatCell} agents.
	 * </p>
	 * 
	 * <p>
	 * Below <i>Model 15</i>, the world was an idealized toric world. Version
	 * Model 15 and 16 uses real data, and toroidal borders were eliminated and
	 * replaced by strict borders.
	 * </p>
	 * 
	 * @see repast.simphony.dataLoader.ContextBuilder#build(repast.simphony.context.Context)
	 */
	@Override
	public Context<Object> build(final Context<Object> context) {
		// Set a specified context ID
		context.setId(Constants.CONTEXT_ID);

		// ------------------------------------------------------------------------
		// Model 15 extension: read the cell data from a specified file

		final List<CellData> cellData = SMUtils
				.readDataFile(Constants.STUPID_CELL_DATA_FILE);

		// The first model specified a two-dimensional grid 100 * 100, but from
		// Model 15 it has to be evaluated from the file input

		// The +1s are added because the grid is indexed from 0, so we have to
		// increase the actual size by 1

		final int gridSizeX = Collections.max(cellData,
				CellData.CELL_DATA_X_COMPARATOR).getX() + 1;

		final int gridSizeY = Collections.max(cellData,
				CellData.CELL_DATA_Y_COMPARATOR).getY() + 1;

		// ------------------------------------------------------------------------

		// Create a space with random positioning with the specified
		// dimensions. From Model 15 we left the default WrapAroundBorders
		// point translator.

		final ContinuousSpace<Object> space = ContinuousSpaceFactoryFinder
				.createContinuousSpaceFactory(null) // No hints
				.createContinuousSpace(Constants.SPACE_ID, context,
						new RandomCartesianAdder<Object>(),
						// From Model 15, stop using toroidal space
						new StrictBorders(), gridSizeX, gridSizeY);

		// Create a space on which agents and cells located at
		final Grid<Object> grid = GridFactoryFinder.createGridFactory(null)
				.createGrid(Constants.GRID_ID, context,
						new GridBuilderParameters<Object>(
						// From Model 15, stop using toroidal space
								new repast.simphony.space.grid.StrictBorders(),
								// This is a simple implementation of an adder
								// that doesn't perform any action
								new SimpleGridAdder<Object>(),
								// Each cell in the grid is multi-occupancy
								true,
								// Size of the grid (as read from file)
								gridSizeX, gridSizeY));

		// Create a background layer for the displayed grid that represents the
		// available (grown) food amount
		final GridValueLayer foodValueLayer = new GridValueLayer(
				Constants.FOOD_VALUE_LAYER_ID, // Access layer through context
				true, // Densely populated
				// FIXME Using an instance of
				// repast.simphony.space.grid.StrictBorders() here results in an
				// exception when rendering
				new WrapAroundBorders(), // Not toroidal
				// Size of the grid (defined constants)
				gridSizeX, gridSizeY);

		context.addValueLayer(foodValueLayer);

		createBugs(context, space, grid);
		createPredators(context, space, grid);
		createHabitatCells(context, cellData, grid, foodValueLayer);

		// The model stopping rule is changed: the model stops after 1000 time
		// steps have been executed... [*] (see activateAgents())
		RunEnvironment.getInstance().endAt(Constants.DEFAULT_END_AT);

		return context;
	}

	/**
	 * Create the specified number of Bug agents and assign them to the space
	 * and the grid.
	 * 
	 * @param context
	 *            context to assign for the agent; <i>should not be
	 *            <code>null</code></i>
	 * @param space
	 *            space to assign for the agent; <i>should not be
	 *            <code>null</code></i>
	 * @param grid
	 *            grid to assign for the agent; <i>should not be
	 *            <code>null</code></i>
	 * @since Model 15
	 */
	private void createBugs(final Context<Object> context,
			final ContinuousSpace<Object> space, final Grid<Object> grid) {
		assert (context != null);
		assert (space != null);
		assert (grid != null);

		final Parameters parameters = RunEnvironment.getInstance()
				.getParameters();

		// Parameter usage I: Parameter is declared on the graphical user
		// interface
		final int bugCount = ((Integer) parameters
				.getValue(Constants.PARAMETER_ID_BUG_COUNT)).intValue();

		// Model 14 defines a new random normal distribution to use for the
		// initially created agent sizes
		final double initialBugSizeMean = ((Double) parameters
				.getValue(Constants.PARAMETER_INITIAL_BUG_SIZE_MEAN))
				.doubleValue();

		final double initialBugSizeSD = ((Double) parameters
				.getValue(Constants.PARAMETER_INITIAL_BUG_SIZE_SD))
				.doubleValue();

		final Normal normal = RandomHelper.createNormal(initialBugSizeMean,
				initialBugSizeSD);

		// Create Bug agents and add them to the context and to the grid as
		// placed randomly by the RandomCartesianAdder of the space
		for (int i = 0; i < bugCount; ++i) {
			final Bug bug = new Bug();
			bug.setInitialSize(normal);

			context.add(bug);
			final NdPoint pt = space.getLocation(bug);
			grid.moveTo(bug, (int) pt.getX(), (int) pt.getY());
		}
	}

	// DOCME
	private void createPredators(final Context<Object> context,
			final ContinuousSpace<Object> space, final Grid<Object> grid) {
		assert (context != null);
		assert (space != null);
		assert (grid != null);

		for (int i = 0; i < Constants.PREDATOR_COUNT; ++i) {
			final Predator predator = new Predator();

			context.add(predator);
			final NdPoint pt = space.getLocation(predator);
			grid.moveTo(predator, (int) pt.getX(), (int) pt.getY());
		}
	}

	/**
	 * Fill up the context with cells, and add them to the created grid. Also
	 * set the initial food values for the value layer.
	 * 
	 * @param context
	 *            context to assign for the cell; <i>should not be
	 *            <code>null</code></i>
	 * @param cellData
	 *            parsed cell data from the specified input file to create the
	 *            cell agents; <i>should not be <code>null</code></i>
	 * @param grid
	 *            grid to assign for the cell; <i>should not be
	 *            <code>null</code></i>
	 * @param foodValueLayer
	 *            value layer to assign for the cell; <i>should not be
	 *            <code>null</code></i>
	 * @since Model 15
	 */
	private void createHabitatCells(final Context<Object> context,
			final List<CellData> cellData, final Grid<Object> grid,
			final GridValueLayer foodValueLayer) {
		assert (context != null);
		assert (cellData != null);
		assert (grid != null);
		assert (foodValueLayer != null);

		for (final CellData act : cellData) {
			final HabitatCell cell = new HabitatCell(act);
			context.add(cell); // First add it to the context
			grid.moveTo(cell, act.getX(), act.getY());
			foodValueLayer.set(cell.getFoodAvailability(), act.getX(),
					act.getY());
		}
	}

	/**
	 * In Model 9, we have to create a randomized order of agent execution.
	 * <b>Please note</b> it is the default behaviour in the previous model
	 * versions: methods scheduled for the same time steps with the same
	 * priority are executed in a random order by default.
	 * 
	 * <p>
	 * This method gathers the current {@link Bug} agent list, randomizes its
	 * order, and calls their {@link Bug#step() step()} and {@link Bug#grow()
	 * grow()} functions sequentially after each other.
	 * </p>
	 * 
	 * <p>
	 * Using the annotation {@link ScheduledMethod} makes this method executed
	 * from the first simulation tick, and with specifying interval it is
	 * executed each tick afterwards.
	 * </p>
	 * 
	 * @since Model 9, Model 10, Model 12
	 */
	@ScheduledMethod(start = 1, interval = 1, priority = 0)
	public void activateAgents() {
		final ArrayList<Bug> bugList = getBugList();

		// Model 9 requires random agent activation
		// SimUtilities.shuffle(bugList, RandomHelper.getUniform());

		// Model 10 requires sorted agent activation
		Collections.sort(bugList, new BugSizeComparator());

		for (final Bug bug : bugList) {
			bug.step();
		}

		for (final Bug bug : bugList) {
			bug.grow();
		}

		// Model 12: Added mortality, scheduled after the bug move and grow
		for (final Bug bug : bugList) {
			bug.mortality();
		}

		// Model 12: [*] ... or when the number of bugs reaches zero.
		// We have to re-collect all of the bug agents, since during mortality
		// it may be heavily modified (e.g. several new agents may
		// born or die)
		if (0 == getBugList().size()) {
			System.out.println("All agents dead, terminating simulation.");
			RunEnvironment.getInstance().endRun();
		}
	}

	/**
	 * Returns the current active {@link Bug} agent list.
	 * 
	 * <p>
	 * This would have done easier by keeping a simple reference for the context
	 * built during {@link #build(Context)}, <i>or</i> by keeping a reference
	 * for the created agents. However, we show here a general solution that may
	 * be used all of the time without any external modification to access all
	 * of the agents from a specified subclass.
	 * </p>
	 * 
	 * @return list of bugs associated with the master (<i>root</i>) context
	 * @since Model 9
	 */
	private ArrayList<Bug> getBugList() {
		@SuppressWarnings("unchecked")
		final Iterable<Bug> bugs = RunState.getInstance().getMasterContext()
				.getObjects(Bug.class);
		final ArrayList<Bug> bugList = new ArrayList<Bug>();

		for (final Bug bug : bugs) {
			bugList.add(bug);
		}

		return bugList;
	}

	/**
	 * A simple method to get the total number of current {@link Bug} population
	 * which is used to create the <i>Population Abundance Graph</i>.
	 * 
	 * @return the current number of existing {@link Bug} agents in the root
	 *         context; <i>or <code>0</code> if the simulation is not yet
	 *         initialized or no master context is available</i>
	 * @since Model 13
	 */
	public int bugCount() {
		final RunState runState = RunState.getInstance();

		// If simulation is not yet started or initialized correctly
		if (null == runState) {
			return 0;
		}

		@SuppressWarnings("unchecked")
		final Context<Object> masterContext = runState.getMasterContext();

		// If simulation is not initialized correctly and there is no root
		// context
		if (null == masterContext) {
			return 0;
		}

		return masterContext.getObjects(Bug.class).size();
	}

}
