/*
 * Version info:
 *     $HeadURL$
 *     $LastChangedDate$
 *     $LastChangedRevision$
 *     $LastChangedBy$
 */
package stupidmodel;

import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.InOrder;

import repast.simphony.batch.BatchScheduleRunner;
import repast.simphony.context.Context;
import repast.simphony.context.DefaultContext;
import repast.simphony.dataLoader.ContextBuilder;
import repast.simphony.engine.environment.RunEnvironment;
import repast.simphony.engine.environment.RunState;
import repast.simphony.engine.environment.Runner;
import repast.simphony.engine.schedule.DefaultScheduleFactory;
import repast.simphony.parameter.IllegalParameterException;
import repast.simphony.parameter.ParametersCreator;
import repast.simphony.random.RandomHelper;
import repast.simphony.space.grid.Grid;
import repast.simphony.space.grid.GridPoint;
import stupidmodel.agents.Bug;
import stupidmodel.agents.HabitatCell;
import stupidmodel.agents.Predator;
import stupidmodel.common.Constants;

/**
 * Tests created to verify the {@link ContextBuilder} implementation.
 * 
 * @author Richard O. Legendi (richard.legendi)
 * @since 2.0-beta, 2011
 * @version $Id$
 * @see StupidModelContextBuilder
 */
public class TestStupidModelContextBuilder {

	// ========================================================================
	// === Test Utilities =====================================================

	/**
	 * Creates a context with no bugs by default.
	 * 
	 * @return a properly initialized context with
	 *         {@link TestStupidModelContextBuilder}
	 */
	private Context<Object> initContext() {
		return initContext(0);
	}

	/**
	 * Creates a context with the specified number of bugs by default with a new
	 * context builder instance.
	 * 
	 * @param bugCount
	 *            the number of bugs to create
	 * @return a properly initialized context with
	 *         {@link TestStupidModelContextBuilder}
	 */
	private Context<Object> initContext(final int bugCount) {
		final StupidModelContextBuilder contextBuilder = new StupidModelContextBuilder();
		return initContext(bugCount, contextBuilder);
	}

	/**
	 * Creates a context with the specified number of bugs by default with the
	 * specified context builder.
	 * 
	 * @param bugCount
	 *            the number of bugs to create
	 * @param contextBuilder
	 *            the context builder instance to use for creation
	 * @return a properly initialized context with
	 *         {@link TestStupidModelContextBuilder}
	 */
	private Context<Object> initContext(final int bugCount,
			final StupidModelContextBuilder contextBuilder) {
		return initContext(bugCount, contextBuilder, null);
	}

	/**
	 * Creates a context with the specified number of bugs by default with the
	 * specified context builder.
	 * 
	 * @param bugCount
	 *            the number of bugs to create
	 * @param contextBuilder
	 *            the context builder instance to use for creation
	 * @param runner
	 *            the <code>Runner</code> instance to use for the creation
	 *            (otherwise it is <code>null</code>)
	 * @return a properly initialized context with
	 *         {@link TestStupidModelContextBuilder}
	 */
	private Context<Object> initContext(final int bugCount,
			final StupidModelContextBuilder contextBuilder, final Runner runner) {
		final Context<Object> context = new DefaultContext<Object>();
		RunState.init().setMasterContext(context);

		final ParametersCreator creator = new ParametersCreator();
		creator.addParameter(Constants.PARAMETER_ID_BUG_COUNT, Integer.class,
				bugCount, false);
		creator.addParameter(Constants.PARAMETER_INITIAL_BUG_SIZE_MEAN,
				Double.class, 0.1, false);
		creator.addParameter(Constants.PARAMETER_INITIAL_BUG_SIZE_SD,
				Double.class, 0.03, false);

		RunEnvironment.init(new DefaultScheduleFactory().createSchedule(),
				runner, creator.createParameters(), false);

		contextBuilder.build(context);

		return context;
	}

	// ========================================================================
	// === Test cases =========================================================

	/**
	 * Test if the created context assigns the space.
	 */
	@Test
	public void testBuildAssignsSpace() {
		final Context<Object> context = initContext();

		Assert.assertNotNull(context.getProjection(Constants.SPACE_ID));
	}

	/**
	 * Test if the created context the grid.
	 */
	@Test
	public void testBuildAssignsGrid() {
		final Context<Object> context = initContext();

		Assert.assertNotNull(context.getProjection(Constants.GRID_ID));
	}

	/**
	 * Test if the created context the value layer.
	 */
	@Test
	public void testBuildAssignsGridValueLayer() {
		final Context<Object> context = initContext();

		Assert.assertNotNull(context
				.getValueLayer(Constants.FOOD_VALUE_LAYER_ID));
	}

	/**
	 * Test initialization if invalid bug count is assigned.
	 */
	@Test(expected = IllegalParameterException.class)
	public void testNegativeBugCountParameter() {
		final int wrongValue = RandomHelper
				.nextIntFromTo(Integer.MIN_VALUE, -1);

		initContext(wrongValue); // Should fail
	}

	/**
	 * Test number of bugs when there is no proper <code>RunState</code>
	 * initialized.
	 */
	@Test
	public void testUninitializedRunStateBugCount() {
		final int bugCount = RandomHelper.nextIntFromTo(0, 100);

		// When no RunInstance is given, return 0 by default
		final StupidModelContextBuilder contextBuilder = new StupidModelContextBuilder();
		initContext(bugCount, contextBuilder);
		// No RunState instance
		RunState.staticInstance = null;

		Assert.assertEquals(0, contextBuilder.bugCount());
	}

	/**
	 * Test number of bugs when there is no proper root context initialized.
	 */
	@Test
	public void testUnassignedContextBugCount() {
		final int bugCount = RandomHelper.nextIntFromTo(0, 100);

		final StupidModelContextBuilder contextBuilder = new StupidModelContextBuilder();
		initContext(bugCount, contextBuilder);
		// No root context has been set
		RunState.init().setMasterContext(null);
		Assert.assertEquals(0, contextBuilder.bugCount());
	}

	/**
	 * Test the {@link StupidModelContextBuilder#bugCount() bugCount()} if the
	 * model is properly initialized.
	 */
	@Test
	public void testBugCount() {
		final int bugCount = RandomHelper.nextIntFromTo(0, 200);

		final StupidModelContextBuilder contextBuilder = new StupidModelContextBuilder();
		initContext(bugCount, contextBuilder);
		Assert.assertEquals(bugCount, contextBuilder.bugCount());
	}

	/**
	 * Test the {@link StupidModelContextBuilder#getBugList() getBugList()} if
	 * the model is properly initialized.
	 */
	@Test
	public void testBugListSize() {
		final int bugCount = RandomHelper.nextIntFromTo(0, 200);

		final StupidModelContextBuilder contextBuilder = new StupidModelContextBuilder();
		initContext(bugCount, contextBuilder);
		Assert.assertEquals(bugCount, contextBuilder.getBugList().size());
	}

	/**
	 * Test the bug instances of {@link StupidModelContextBuilder#getBugList()
	 * getBugList()} if the model is properly initialized.
	 */
	@Test
	public void testBugListComponents() {
		final int bugCount = RandomHelper.nextIntFromTo(0, 200);

		final StupidModelContextBuilder contextBuilder = new StupidModelContextBuilder();
		initContext(bugCount, contextBuilder);
		final ArrayList<Bug> bugList = contextBuilder.getBugList();
		final HashSet<Bug> bugSet = new HashSet<Bug>(bugList);

		// Check if there are unique Bug instances only
		Assert.assertEquals(bugList.size(), bugSet.size());
	}

	/**
	 * Test if the proper number of {@link Bug} agents created if the model is
	 * properly initialized.
	 */
	@Test
	public void testBuildCreatesBugs() {
		final int bugCtr = 100;
		final Context<Object> context = initContext(bugCtr);

		@SuppressWarnings({ "unchecked", "rawtypes" })
		final Class<Object> clazz = (Class) Bug.class;
		final Iterator<Object> it = context.getAgentLayer(clazz).iterator();
		int ctr = 0;

		while (it.hasNext()) {
			it.next();
			ctr++;
		}

		Assert.assertEquals(bugCtr, ctr);
	}

	/**
	 * Test if the proper number of {@link Predator} agents created if the model
	 * is properly initialized.
	 */
	@Test
	public void testBuildCreatesPredators() {
		final Context<Object> context = initContext();

		@SuppressWarnings({ "unchecked", "rawtypes" })
		final Class<Object> clazz = (Class) Predator.class;
		final Iterator<Object> it = context.getAgentLayer(clazz).iterator();
		int ctr = 0;

		while (it.hasNext()) {
			it.next();
			ctr++;
		}

		Assert.assertEquals(Constants.PREDATOR_COUNT, ctr);
	}

	/**
	 * Test if the proper number of {@link HabitatCell} agents created if the
	 * model is properly initialized (also check if one grid cell contains only
	 * one of the instances).
	 */
	@Test
	public void testBuildCreatesCells() {
		final Context<Object> context = initContext();

		@SuppressWarnings({ "unchecked", "rawtypes" })
		final Class<Object> clazz = (Class) HabitatCell.class;

		@SuppressWarnings({ "unchecked", "rawtypes" })
		final Iterator<HabitatCell> it = (Iterator) context
				.getAgentLayer(clazz).iterator();

		@SuppressWarnings("unchecked")
		final Grid<Object> grid = (Grid<Object>) context
				.getProjection(Constants.GRID_ID);

		int ctr = 0;

		final boolean[][] cells = new boolean[grid.getDimensions().getWidth()][grid
				.getDimensions().getHeight()];

		while (it.hasNext()) {
			final GridPoint location = grid.getLocation(it.next());

			Assert.assertFalse(cells[location.getX()][location.getY()]);
			cells[location.getX()][location.getY()] = true;

			ctr++;
		}

		Assert.assertEquals(grid.getDimensions().getWidth()
				* grid.getDimensions().getHeight(), ctr);

		for (int i = 0; i < cells.length; ++i) {
			for (int j = 0; j < cells[j].length; ++j) {
				Assert.assertTrue(cells[i][j]);
			}
		}
	}

	/**
	 * Check if the actual execution of the agent actions fit the specification.
	 */
	@Test
	public void testActivateAgentsOrdering() {
		final StupidModelContextBuilder contextBuilder = new StupidModelContextBuilder();
		final Context<Object> context = initContext(0, contextBuilder);

		final Bug bugWithSize5 = mock(Bug.class);
		final Bug bugWithSize10 = mock(Bug.class);

		when(bugWithSize5.getSize()).thenReturn(5.0);
		when(bugWithSize10.getSize()).thenReturn(10.0);

		context.add(bugWithSize5);
		context.add(bugWithSize10);

		final InOrder order = inOrder(bugWithSize5, bugWithSize10);

		contextBuilder.activateAgents();

		verify(bugWithSize5, atLeastOnce()).getSize();
		verify(bugWithSize10, atLeastOnce()).getSize();

		order.verify(bugWithSize10).step();
		order.verify(bugWithSize5).step();

		order.verify(bugWithSize10).grow();
		order.verify(bugWithSize5).grow();

		order.verify(bugWithSize10).mortality();
		order.verify(bugWithSize5).mortality();
	}

	/**
	 * Check if the simulation is stopped if all of the {@link Bug} agents
	 * disappear.
	 */
	@Test
	public void testSimulationEndsIfAgents() {
		final StupidModelContextBuilder contextBuilder = new StupidModelContextBuilder();
		final BatchScheduleRunner runner = new BatchScheduleRunner();
		initContext(0, contextBuilder, runner);

		try {
			final Field stopField = repast.simphony.engine.environment.AbstractRunner.class
					.getDeclaredField("stop");
			stopField.setAccessible(true);

			contextBuilder.activateAgents();

			final boolean isStopped = stopField.getBoolean(runner);
			Assert.assertTrue(isStopped);
		} catch (final Exception e) {
			Assert.fail(e.getMessage());
		}
	}

}
