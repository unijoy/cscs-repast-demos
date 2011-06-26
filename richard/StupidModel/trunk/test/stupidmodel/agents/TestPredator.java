/*
 * Version info:
 *     $HeadURL$
 *     $LastChangedDate$
 *     $LastChangedRevision$
 *     $LastChangedBy$
 */
package stupidmodel.agents;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import junit.framework.Assert;

import org.junit.Test;

import repast.simphony.context.Context;
import repast.simphony.context.DefaultContext;
import repast.simphony.engine.environment.RunState;
import repast.simphony.random.RandomHelper;
import repast.simphony.space.grid.Grid;
import repast.simphony.space.grid.GridPoint;
import stupidmodel.common.Constants;

/**
 * Simple tests for the created {@link Predator} agents.
 * 
 * @author Richard O. Legendi (richard.legendi)
 * @since 2.0-beta, 2011
 * @version $Id: TestBug.java 428 2011-06-18 14:19:31Z richard.legendi@gmail.com
 *          $
 * @see Predator
 */
public class TestPredator {

	/**
	 * Just to suppress <code>toString()</code> coverage noise.
	 */
	@Test
	public void testToStringWithoutGrid() {
		final Predator predator = new Predator();
		Assert.assertNotNull(predator.toString());
	}

	/**
	 * Just to suppress <code>toString()</code> coverage noise.
	 */
	@Test
	public void testToStringWithGrid() {
		final Predator predator = new Predator();
		final int x = RandomHelper.nextIntFromTo(0, Integer.MAX_VALUE);
		final int y = RandomHelper.nextIntFromTo(0, Integer.MAX_VALUE);

		final Context<Object> context = new DefaultContext<Object>();
		RunState.init().setMasterContext(context);

		@SuppressWarnings("unchecked")
		final Grid<Object> grid = mock(Grid.class);
		when(grid.getName()).thenReturn(Constants.GRID_ID);
		when(grid.getLocation(predator)).thenReturn(new GridPoint(x, y));

		context.addProjection(grid);

		context.add(predator);
		Assert.assertNotNull(predator.toString());
	}

}
