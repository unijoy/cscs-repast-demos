package bugcatcher.relogo

import static repast.simphony.relogo.Utility.*;
import static repast.simphony.relogo.UtilityG.*;
import repast.simphony.relogo.BasePatch;
import repast.simphony.relogo.Stop;
import repast.simphony.relogo.Utility;
import repast.simphony.relogo.UtilityG;
import repast.simphony.relogo.ast.Diffusible;

/**
*
* Machine is a patch which catch bugs. The Machine has a source
* which attracts bugs. They get attracted and are caught in the Moore's
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

class UserPatch extends BasePatch{
	
	@Diffusible
	def machine = 0
	
	//setting machine
	def setAll()
	{
		if(machine==1)
		{
			setPcolor(blue())
			ask(neighbors())
			{
				setN()
			}
		}
	}
	
	//setting catching range
	def setN()
	{
		setPcolor(turquoise())
	}
	
}