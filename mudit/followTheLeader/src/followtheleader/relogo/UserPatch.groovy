package followtheleader.relogo

import static repast.simphony.relogo.Utility.*;
import static repast.simphony.relogo.UtilityG.*;
import repast.simphony.relogo.BasePatch;
import repast.simphony.relogo.Stop;
import repast.simphony.relogo.Utility;
import repast.simphony.relogo.UtilityG;
import repast.simphony.relogo.ast.Diffusible;

/**
*
* In this class, The food and nest is set up and various variables to keep track of the
* first and present ants and their routes are used.
*
* @author Mudit Raj Gupta
*
*/

class UserPatch extends BasePatch
{
	
	//sets diffusible variable which can be modified by other classes
	@Diffusible
	def food=0
	def nest=0
	def first=0
	def present=0
	
	//Sets route length
	def firstRoute = 0 
	def presentRoute = 0
	
	//sets food patches
	def setFood()
	{
		if(getPxcor()==8&&getPycor()==0)
		{
			food=1
			setPcolor(green())
		}
	}
	
	//sets nest patches
	def setNest()
	{
		if(getPxcor()==-8&&getPycor()==0)
		{
			nest=1
			setPcolor(yellow())
		}
	}
}