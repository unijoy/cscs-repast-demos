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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import repast.simphony.context.Context;
import repast.simphony.context.DefaultContext;
import repast.simphony.engine.environment.RunState;
import repast.simphony.random.RandomHelper;
import repast.simphony.space.grid.Grid;
import repast.simphony.space.grid.GridPoint;
import stupidmodel.agents.Bug.BugSizeComparator;
import stupidmodel.common.Constants;

/**
 * Simple tests for the created {@link Bug} agents.
 * 
 * @author Richard O. Legendi (richard.legendi)
 * @since 2.0-beta, 2011
 * @version $Id$
 * @see Bug
 */
public class TestBug {

	/**
	 * Test failure if there is no grid associated to this agent.
	 */
	@Test(expected = IllegalStateException.class)
	public void testNoGrid() {
		// Some workaround to make the test see like if it was running
		final Context<Object> context = new DefaultContext<Object>();
		RunState.init().setMasterContext(context);

		final Bug bug = new Bug();
		context.add(bug);
		bug.getGrid(); // Should fail
	}

	/**
	 * Test if grid is set it is returned by the agent.
	 */
	@Test
	public void testGridQuery() {
		// Some workaround to make the test see like if it was running
		final Context<Object> context = new DefaultContext<Object>();
		RunState.init().setMasterContext(context);

		@SuppressWarnings("unchecked")
		final Grid<Object> grid = mock(Grid.class);
		when(grid.getName()).thenReturn(Constants.GRID_ID);

		context.addProjection(grid);

		final Bug bug = new Bug();
		context.add(bug);
		Assert.assertSame(grid, bug.getGrid());
	}

	/**
	 * Test invalid argument for the {@link Bug#setSize()} function.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testInvalidParameterForSetSize() {
		final Bug bug = new Bug();
		final double wrongValue = RandomHelper.nextDoubleFromTo(
				-Double.MAX_VALUE, -Double.MIN_VALUE);
		bug.setSize(wrongValue);
	}

	/**
	 * Test illegal argument for {@link Bug#compareTo(Bug)}.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testCompareToInvalidFirstParameter() {
		final Bug bug = new Bug();
		new BugSizeComparator().compare(null, bug); // Should fail
	}

	/**
	 * Test illegal argument for {@link Bug#compareTo(Bug)}.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testCompareToInvalidSecondParameter() {
		final Bug bug = new Bug();
		new BugSizeComparator().compare(bug, null); // Should fail
	}

	/**
	 * Making sure if the default comparison works correctly for two agents, and
	 * sorting returns an agent list in descending size order.
	 */
	@Test
	public void testComparisonWithTwoElements() {
		final Bug b1 = new Bug();
		final Bug b2 = new Bug();

		b1.setSize(5.0);
		b2.setSize(10.0);

		final List<Bug> list = Arrays.asList(b1, b2);
		Collections.sort(list, new BugSizeComparator());

		Assert.assertEquals(b2, list.get(0));
		Assert.assertEquals(b1, list.get(1));
	}

	/**
	 * Making sure if the default comparison works correctly for three agents,
	 * and sorting returns an agent list in descending size order.
	 */
	@Test
	public void testComparisonWithThreeElements() {
		final Bug b1 = new Bug();
		final Bug b2 = new Bug();
		final Bug b3 = new Bug();

		b1.setSize(5.0);
		b2.setSize(10.0);
		b3.setSize(2.0);

		final List<Bug> list = Arrays.asList(b1, b2, b3);
		Collections.sort(list, new BugSizeComparator());

		Assert.assertEquals(b2, list.get(0));
		Assert.assertEquals(b1, list.get(1));
		Assert.assertEquals(b3, list.get(2));
	}

	/**
	 * Test if the bug dies it is removed from the context.
	 */
	@Test
	public void testDying() {
		final Bug bug = new Bug();
		final Context<Object> context = new DefaultContext<Object>();
		RunState.init().setMasterContext(context);
		context.add(bug);

		Assert.assertTrue(context.contains(bug));
		bug.die();
		Assert.assertFalse(context.contains(bug));
	}

	/**
	 * Just to suppress <code>toString()</code> coverage noise.
	 */
	@Test
	public void testToStringWithoutGrid() {
		final Bug bug = new Bug();
		Assert.assertNotNull(bug.toString());
	}

	/**
	 * Just to suppress <code>toString()</code> coverage noise.
	 */
	@Test
	public void testToStringWithGrid() {
		final Bug bug = new Bug();
		final int x = RandomHelper.nextIntFromTo(0, Integer.MAX_VALUE);
		final int y = RandomHelper.nextIntFromTo(0, Integer.MAX_VALUE);

		final Context<Object> context = new DefaultContext<Object>();
		RunState.init().setMasterContext(context);

		@SuppressWarnings("unchecked")
		final Grid<Object> grid = mock(Grid.class);
		when(grid.getName()).thenReturn(Constants.GRID_ID);
		when(grid.getLocation(bug)).thenReturn(new GridPoint(x, y));

		context.addProjection(grid);

		context.add(bug);
		Assert.assertNotNull(bug.toString());
	}

}
