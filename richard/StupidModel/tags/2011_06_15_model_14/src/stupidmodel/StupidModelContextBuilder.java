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

import cern.jet.random.Normal;

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
import repast.simphony.space.grid.Grid;
import repast.simphony.space.grid.GridBuilderParameters;
import repast.simphony.space.grid.SimpleGridAdder;
import repast.simphony.space.grid.WrapAroundBorders;
import repast.simphony.valueLayer.GridValueLayer;
import stupidmodel.agents.Bug;
import stupidmodel.agents.Bug.BugSizeComparator;
import stupidmodel.agents.HabitatCell;
import stupidmodel.common.Constants;

/**
 * Custom {@link ContextBuilder} implementation for the <i>StupidModel 1</i>.
 * 
 * @author Richard O. Legendi (richard.legendi)
 * @since 2.0-beta, 2011
 * @version $Id: StupidModelContextBuilder.java 150 2011-05-26 19:06:40Z
 *          richard.legendi@gmail.com $
 */
public class StupidModelContextBuilder extends DefaultContext<Object> implements
		ContextBuilder<Object> {

	// FIXME How to infer generic types?
	@Override
	public Context<Object> build(final Context<Object> context) {
		// Set a specified context ID
		context.setId(Constants.CONTEXT_ID);
		final Parameters parameters = RunEnvironment.getInstance()
				.getParameters();

		// Create a toridal space with random positioning with the specified
		// dimensions
		final ContinuousSpace<Object> space = ContinuousSpaceFactoryFinder
				.createContinuousSpaceFactory(null) // No hints (?)
				.createContinuousSpace(
						Constants.SPACE_ID,
						context,
						new RandomCartesianAdder<Object>(),
						new repast.simphony.space.continuous.WrapAroundBorders(),
						Constants.GRID_SIZE, Constants.GRID_SIZE);

		// Create a toridal space on which agents and cells located at
		final Grid<Object> grid = GridFactoryFinder
				.createGridFactory(null)
				.createGrid(
						Constants.GRID_ID,
						context,
						new GridBuilderParameters<Object>(
								new repast.simphony.space.grid.WrapAroundBorders(),
								// This is a simple implementation of an adder
								// that doesn't perform any action
								new SimpleGridAdder<Object>(),
								// Each cell in the grid is multi-occupancy
								true,
								// Size of the grid (defined constants)
								Constants.GRID_SIZE, Constants.GRID_SIZE));

		// ---------------------------------------------------------------------
		// Create the specified number of Bug agents and assign them to the
		// space and the grid

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

		// ---------------------------------------------------------------------

		// Create Bug agents and add them to the context and to the grid as
		// placed randomly by the RandomCartesianAdder of the space
		for (int i = 0; i < bugCount; ++i) {
			final Bug bug = new Bug();
			bug.setInitialSize(normal);

			context.add(bug);
			final NdPoint pt = space.getLocation(bug);
			grid.moveTo(bug, (int) pt.getX(), (int) pt.getY());
		}

		// Create a background layer for the displayed grid that represents the
		// available (grown) food amount
		final GridValueLayer foodValueLayer = new GridValueLayer(
				Constants.FOOD_VALUE_LAYER_ID, // Access layer through context
				true, // Densely populated
				new WrapAroundBorders(), // Toric world
				// Size of the grid (defined constants)
				Constants.GRID_SIZE, Constants.GRID_SIZE);

		context.addValueLayer(foodValueLayer);

		// Fill up the context with cells, and set the initial food values for
		// the new layer. Also add them to the created grid.
		for (int i = 0; i < Constants.GRID_SIZE; ++i) {
			for (int j = 0; j < Constants.GRID_SIZE; ++j) {
				final HabitatCell cell = new HabitatCell(i, j);
				context.add(cell); // First add it to the context
				grid.moveTo(cell, i, j);
				foodValueLayer.set(cell.getFoodAvailability(), i, j);
			}
		}

		// The model stopping rule is changed: the model stops after 1000 time
		// steps have been executed... [*] (see activateAgents())
		RunEnvironment.getInstance().endAt(Constants.DEFAULT_END_AT);

		return context;
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
