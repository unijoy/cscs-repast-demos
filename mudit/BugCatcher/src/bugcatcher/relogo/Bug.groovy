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
 *  
 * Model can be used to study the rate at which bugs get caught and rate
 * at which they die. The variation can be seen by changing the number of
 * bugs and number of catching machine. 
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
	
	def step()
	{
		//bug gets attracted towards the machine 
		def dir = maxOneOf(neighbors()){
					
					count(machinesOn(it))}
					face(dir)
		
		//Bug Speed can be adjusted by the user						
		fd(bugSpeed*0.5)

		//If bug gets caught
		if(count(machinesHere())>0)
		{
			
			timeleft--
			
			//If specified time has lapsed bug dies
			if(timeleft==0)
			{
			
				die()
			}	
		}
	
	}
	
}
