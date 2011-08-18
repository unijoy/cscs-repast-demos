package guidingbots.relogo   

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
 * This class defines a predefined task of daughter motes. All daughter motes 
 * have to reach at the center of the world, where they die. They do not have
 * good positioning instruments, so they rely on guiding motes.
 * 
 * Such models are useful in swarm robotics and mobile wireless sensor networks
 * 
 * See
 *
 * http://code.google.com/p/cscs-repast-demos/wiki/guidingBots
 *
 * for a full description of the model and details.
 *
 * @author Mudit Raj Gupta
 *
 * */


class daughterMote extends BaseTurtle 
{
	def val=0
	def step()
	{ 
		if(distancexy(0,0)>3)
		{
		def dir = maxOneOf(neighbors())
		
		{
			count(guidingMotesHere())
		}
		
	//	face(dir)
		
	//	forward(stepSize)
		
		if(count(inRadius(guidingMotes(),1))>=1)
		{
			val = oneOf(inRadius(guidingMotes(),1))
			hatchGuidingMotes(1)
			{
				setColor(yellow())
				size = 2
				error=val.error
				t=1
				move=er
				createDisplaceTo(val)
				{
					setColor(violet())
				}
			}	
		die()
		}
		}
		else{
			die()
		}
	}
}
