/*
 * Version info:
 *     $HeadURL$
 *     $LastChangedDate$
 *     $LastChangedRevision$
 *     $LastChangedBy$
 */
package elfarol;

import repast.simphony.engine.environment.RunEnvironment;
import repast.simphony.parameter.Parameters;

/**
 * @author Richard O. Legendi (richard.legendi)
 * @since 2.0-beta, 2011
 * @version $Id$
 */
public final class ParameterWrapper {
	private static int agentsNumber = 100;
	private static int memorySize = 5;
	private static int strategiesNumber = 10;
	private static int overcrowdingThreshold = 60;

	public static int getAgentsNumber() {
		return agentsNumber;
	}

	public static int getMemorySize() {
		return memorySize;
	}

	public static int getStrategiesNumber() {
		return strategiesNumber;
	}

	public static int getOvercrowdingThreshold() {
		return overcrowdingThreshold;
	}

	public static void reinit() {
		final Parameters parameters = RunEnvironment.getInstance()
				.getParameters();

		agentsNumber = ((Integer) parameters.getValue("agentsNumber"))
				.intValue();

		memorySize = ((Integer) parameters.getValue("memorySize")).intValue();

		strategiesNumber = ((Integer) parameters.getValue("strategiesNumber"))
				.intValue();

		overcrowdingThreshold = ((Integer) parameters
				.getValue("overcrowdingThreshold")).intValue();
	}

	// ========================================================================

	private ParameterWrapper() {
		;
	}
}
