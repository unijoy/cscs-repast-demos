package guidingbots.relogo

import static repast.simphony.relogo.Utility.*;
import static repast.simphony.relogo.UtilityG.*;
import repast.simphony.relogo.BasePatch;
import repast.simphony.relogo.Stop;
import repast.simphony.relogo.Utility;
import repast.simphony.relogo.UtilityG;
import repast.simphony.relogo.ast.Diffusible;

/**
*
* This class is used to set the patch properties and thus helps in making
* the background.
*
* @author Mudit Raj Gupta
*
*/

class UserPatch extends BasePatch{
	
	//for storing the distance from(0,0)
	def dis
	
	def setBackGround ()
	{ 
		//set the background to represent a gradient towards the center
		dis=distancexy(0,0)
		pcolor = scaleColor(blue(),dis,0,20)
	} 

}