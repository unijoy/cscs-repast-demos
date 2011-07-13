package bugcatcher.relogo

import static repast.simphony.relogo.Utility.*;
import static repast.simphony.relogo.UtilityG.*;
import repast.simphony.relogo.BasePatch;
import repast.simphony.relogo.BaseTurtle;
import repast.simphony.relogo.Plural;
import repast.simphony.relogo.Stop;
import repast.simphony.relogo.Utility;
import repast.simphony.relogo.UtilityG;

/**
 * 
 * Machine is the turtle which catch bugs. The Machine has a source 
 * which attracts bugs. They ger attracted and are caught in the Moore's
 * Neighborhood of the machine. After being in contact with the machine
 * for a specified time period, bug dies.
 * 
 * Number of Machines can be changed through user interface. Although
 * they are created at random coordinates
 * 
 * See
 *
 * http://code.google.com/p/cscs-repast-demos/wiki/BugCatcher
 *
 * for a full description of the model and details.
 *
 * @author Mudit Raj Gupta
 *
 * */


class Machine extends BaseTurtle {
	
	def step()
	{
		
	}
}
