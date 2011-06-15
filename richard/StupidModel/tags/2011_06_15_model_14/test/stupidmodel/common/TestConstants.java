/*
 * Version info:
 *     $HeadURL$
 *     $LastChangedDate$
 *     $LastChangedRevision$
 *     $LastChangedBy$
 */
package stupidmodel.common;

import java.lang.reflect.Constructor;

import org.junit.Test;

/**
 * A little utility class to help on test coverage on class {@link Constants}.
 * 
 * @author Richard O. Legendi (richard.legendi)
 * @since 2.0-beta, 2011
 * @version $Id: SMUtilsTest.java 183 2011-05-29 17:09:27Z
 *          richard.legendi@gmail.com $
 * @see Constants
 */
public class TestConstants {

	/**
	 * Easy win to get coverage and eliminate noise in the report: putting the
	 * idealism aside the following test does the job.
	 */
	@Test
	public void giveMeCoverageForMyPrivateConstructor() throws Exception {
		final Constructor<?> constructor = Constants.class
				.getDeclaredConstructor();
		constructor.setAccessible(true);
		constructor.newInstance();
	}

}
