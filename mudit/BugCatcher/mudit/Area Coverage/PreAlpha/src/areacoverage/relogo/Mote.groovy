package areacoverage.relogo

import static repast.simphony.relogo.Utility.*;
import static repast.simphony.relogo.UtilityG.*;
import repast.simphony.relogo.BasePatch;
import repast.simphony.relogo.BaseTurtle;
import repast.simphony.relogo.Plural;
import repast.simphony.relogo.Stop;
import repast.simphony.relogo.Utility;
import repast.simphony.relogo.UtilityG;


/**
 * Each mote is tries to move in an area where there are less number 
 * of motes. This results in expansions. The initial positioning of the 
 * motes can be selected by choosing any of the three regular shapes - 
 * Circle, Square and Hexagon.
 * 
 * Model can be used to study the density of motes at different region,
 * the effect of initial geometry on the spread and rate at which the 
 * expansion is taking place.
 *  	
 * See
 * 
 * http://code.google.com/p/cscs-repast-demos/wiki/AreaCoverage
 *   
 * for a full description of the model and details. 
 *  
 * @author Mudit Raj Gupta
 *
 * */

class Mote extends BaseTurtle 
{
	//For giving behavior to a mote
	def step()
	{
		//Finds the direction witch points to least number of motes
		def dir = minOneOf(neighbors()) 
		{
			count(motesOn(it))
		}
		
		//Faces the direction witch points to least number of motes
		face(dir)
		
		//Moves forward number of steps as specified by the user
		forward(stepSize)
		
		}
		
		
}
