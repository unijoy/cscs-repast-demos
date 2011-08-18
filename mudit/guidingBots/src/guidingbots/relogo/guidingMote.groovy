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
 * Guiding Motes are more sophisticated agents in the environment and are supposed 
 * to guide other daughter mote. They can transfer data to a daughter mote. A daughter 
 * mote can hatch into a guiding mote and can further guide other daughter motes, although 
 * these motes will have an error due to lack of sophisticated instruments. So, they will
 * again convert back to daughter motes. If such motes guide other daughter motes, the error 
 * is also propogated  
 *    
 * Model can be used to study the time taken to transfer information to all the daughter 
 * notes. It can also be used to calculate the minimum number of guiding motes required 
 * to activate a given number of daughter nodes in a given time. It can also be used to 
 * study the time for completion of a task. A demo plot comes with the model to study the 
 * time taken to accomplish the task, for task view daughter mote description. Plot of 
 * number of daughter motes alive v/s time is available 
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

class guidingMote extends BaseTurtle 
{
	//number of steps a guiding mote can move (which have hatched from daughter motes)
	def move = 0 
	
	//is set if the guiding mote had hatched from a daughter mote 
	def t = 0
	
	//error induced
	def error = 0
	
	//called at each tick
	def step()
	{
		//mainly for hatched mote
		//if the error is in the tolerance range and number of steps moved < permitted
		if(move>0&&error<er)
		{
			//face center with some error
			facexy(0+random(error),0+random(error))
			
			//move forward
			forward(stepSize*0.5)
			
			//decrement steps left
			move--
			
			//increment error
			error++
		}
		
		//if origional guiding mote or about to be dead daughter
		else{
			
			//If temprary mote
			if(t==1)
			{
				//convert into daughter
				hatchDaughterMotes(1)
				{
					setColor(black())
					size = 1
				}
			
			die()
			}
			
			else
			{
				//move to a place where the number of daughter motes is more
				def dir = maxOneOf(neighbors())
				{
					count(daughterMotesHere())
				}
		
		//if in 3 units radius from center				
		if(distancexy(0,0)<=3)
		{
			//face outside
			facexy(0,0)
			left(180)
			
			//move out
			forward(5)
		}
		
		//else go to area which has high density of daughter motes
		else
		{	face(dir)
			forward(stepSize*0.5)
		}
		
		}
	}
}
	
	
	}

