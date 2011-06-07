/*
 * Version info:
 *     $HeadURL$
 *     $LastChangedDate$
 *     $LastChangedRevision$
 *     $LastChangedBy$
 */
package repast.simphony.query.space.grid;

import repast.simphony.space.grid.GridPoint;

/**
 * Dedicated uninstantiable class for declaring a test utility function that can
 * create a <code>GridCell</code> instance containing objects.
 * 
 * <p>
 * The class was necessary due {@link GridCell#addObject(Object)} has
 * package-private access privilege.
 * </p>
 * 
 * <p>
 * Final class, cannot be extended.
 * </p>
 * 
 * @author Richard O. Legendi (richard.legendi)
 * @since 2.0-beta, 2011
 * @version $Id$
 * @see GridCell
 */
public final class TestGridCellFactory {

	/**
	 * A simple factory method to create properly initialized {@link GridCell}
	 * objects for testing utility methods.
	 * 
	 * @param pt
	 *            Location of the grid cell
	 * @param clazz
	 *            Type of the objects the grid cell will hold
	 * @param objectAtGridCell
	 *            Sequence of objects that the cell actually holds. The number
	 *            of arguments is variable and may be zero.
	 * @return a properly initialized <code>GridCell</code> object containing
	 *         the specified elements
	 */
	public static <T, S extends T> GridCell<T> createGridCellToTest(
			final GridPoint pt, final Class<T> clazz,
			final S... objectAtGridCell) {
		final GridCell<T> ret = new GridCell<T>(pt, clazz);
		for (final S act : objectAtGridCell) {
			ret.addObject(act);
		}
		return ret;
	}

}
