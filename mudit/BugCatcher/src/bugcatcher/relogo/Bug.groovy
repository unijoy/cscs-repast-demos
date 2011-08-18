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
 * Each Bug moves moves randomly in a given area. If a bug encounters a 
 * Bug Catching Machine in it's Moore neighborhood it gets caught in the 
 * machine. The bug dies after a specified time in the bug catching machine.
 * Both the bug catching machines and bugs can be created randomly.
 *  
 * Model can be used to study the rate at which bugs get caught and rate
 * at which they die. The variation can be seen by changing the number of
 * bugs and number of catching machine. A demo plot is present in the model 
 * which can be used for finding death rate. (Number of bugs alive v/s time
 * 
 * Check out the http://code.google.com/p/cscs-repast-demos/wiki/MothAdvance for
 * variable Bug catching power. A look at http://code.google.com/p/cscs-repast-demos
 * /wiki/MothBasic will help in better understanding  
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

class Bug extends BaseTurtle 
{
	
	//Time lapse between getting caught and death
	def timeleft=tym
	
	//Set if there is machine patch nearby
	def near=0
	
	//called at each tick
	def step()
	{
		
		//bug gets attracted towards the machine 
		if(patchHere().machine==1)
		{
			setColor(lime())			
			timeleft--
			
			//If specified time has lapsed bug dies
			if(timeleft==0)
			{
				die()
			}
		}
		
		else
		{
			//if machine is in the neighborhood
			ask(neighbors()){
				if(patchHere().machine==1)
				{
						face(patchHere())
				near=1
				}
		}
		
		//It neither caught nor in the neighborhood	
		if(near==0)
			//move randomly
			left(random(180))
		
		//move forward
		fd(bugSpeed*0.5)
		}
	
	}
}
	

