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
 * Each mote tries to move in an area where there are less number 
 * of motes. This results in expansions. The initial positioning of the 
 * motes can be selected by choosing any of the preset regular shapes - 
 * Circle, Square, Line and Hexagon.
 * 
 * Model can be used to study the density of motes at different region,
 * the effect of initial geometry on the spread and rate at which the 
 * expansion is taking place. As a demo the graph for calculating spread 
 * rate is plotted. (Number of motes Outside 14 units radius v/s Time)
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
	
    //set if the mote is outside, i.e. outside 14 units radius from the center
	def O=0
	
	def step()
	{
		//setting color according to the distance from the center
		if(distancexy(0,0)==0)
			setColor(white())
		if(distancexy(0,0)>0)
			setColor(yellow())
		if(distancexy(0,0)>8)
			setColor(orange())
		if(distancexy(0,0)>10)
			setColor(red())
		if(distancexy(0,0)>12)
			setColor(magenta())
		if(distancexy(0,0)>14)
		{
			O=1
			setColor(violet())
		}
		else
		 O=0
		 
		//Finds the direction witch points to least number of motes
		def dir = minOneOf(neighbors()) 
		{
			count(motesOn(it))
		}
		
		//Faces the direction witch points to least number of motes
		face(dir)
		
		//Moves forward number of steps as specified by the user
		forward(stepSize*0.5)
		
		}
	
		
		
}
