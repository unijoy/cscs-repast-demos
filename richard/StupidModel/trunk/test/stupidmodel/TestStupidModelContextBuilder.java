/*
 * Version info:
 *     $HeadURL$
 *     $LastChangedDate$
 *     $LastChangedRevision$
 *     $LastChangedBy$
 */
package stupidmodel;

import java.util.Iterator;

import org.junit.Assert;
import org.junit.Test;

import repast.simphony.context.Context;
import repast.simphony.context.DefaultContext;
import repast.simphony.dataLoader.ContextBuilder;
import repast.simphony.engine.environment.RunEnvironment;
import repast.simphony.engine.environment.RunState;
import repast.simphony.engine.schedule.DefaultScheduleFactory;
import repast.simphony.parameter.ParametersCreator;
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
 */
public class TestStupidModelContextBuilder {

	private Context<Object> initContext() {
		final StupidModelContextBuilder contextBuilder = new StupidModelContextBuilder();
		final Context<Object> context = new DefaultContext<Object>();
		RunState.init().setMasterContext(context);

		final ParametersCreator creator = new ParametersCreator();
		creator.addParameter(Constants.PARAMETER_ID_BUG_COUNT, Integer.class,
				0, false);
		creator.addParameter(Constants.PARAMETER_INITIAL_BUG_SIZE_MEAN,
				Double.class, 0.1, false);
		creator.addParameter(Constants.PARAMETER_INITIAL_BUG_SIZE_SD,
				Double.class, 0.03, false);

		RunEnvironment.init(new DefaultScheduleFactory().createSchedule(),
				null, creator.createParameters(), false);

		contextBuilder.build(context);

		return context;
	}

	@Test
	public void testBuildAssignsSpace() {
		final Context<Object> context = initContext();

		Assert.assertNotNull(context.getProjection(Constants.SPACE_ID));
	}

	@Test
	public void testBuildAssignsGrid() {
		final Context<Object> context = initContext();

		Assert.assertNotNull(context.getProjection(Constants.GRID_ID));
	}

	@Test
	public void testBuildAssignsGridValueLayer() {
		final Context<Object> context = initContext();

		Assert.assertNotNull(context
				.getValueLayer(Constants.FOOD_VALUE_LAYER_ID));
	}

	@Test
	public void testBuildCreatesBugs() {
		final StupidModelContextBuilder contextBuilder = new StupidModelContextBuilder();
		final Context<Object> context = new DefaultContext<Object>();

		final int bugNumber = 100;
		RunEnvironment.getInstance().getParameters()
				.setValue(Constants.PARAMETER_ID_BUG_COUNT, bugNumber);

		contextBuilder.build(context);

		@SuppressWarnings({ "unchecked", "rawtypes" })
		final Class<Object> clazz = (Class) Bug.class;
		final Iterator<Object> it = context.getAgentLayer(clazz).iterator();
		int ctr = 0;

		while (it.hasNext()) {
			it.next();
			ctr++;
		}

		Assert.assertEquals(bugNumber, ctr);
	}

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

}
