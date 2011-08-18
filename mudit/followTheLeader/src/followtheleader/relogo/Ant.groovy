package followtheleader.relogo

import static repast.simphony.relogo.Utility.*;
import static repast.simphony.relogo.UtilityG.*;
import repast.simphony.relogo.BasePatch;
import repast.simphony.relogo.BaseTurtle;
import repast.simphony.relogo.Plural;
import repast.simphony.relogo.Stop;
import repast.simphony.relogo.Utility;
import repast.simphony.relogo.UtilityG;


/*
 * 
 * The model is a simple multi agent model which demonstrates how the ant lines become short as time 
 * passes. The model basically has an ant "nest"  and the "food". The ants are moving from the nest 
 * to the food source. Ants are guided by a set of simple rules, which are as follows :
 * - An Ant can be either a "Leader"(first Ant coming out of the nest) or a "Follower" (rest all Ants)
 * - Leader Ant
 * 		-  It moves towards the food scent 
 * 		-  It turns at random angles (less than specified) and moves forward 
 * 		-  It stops if it finds food. 
 * - Follower Ants
 * 		- They start moving after a specified time delay 
 * 		- They follow the ant infront of them by facing it and moving forward. 
 * 
 * Initially, ants take a longer route from nest to food. (refer to Fig. 4, denoted by red). The following 
 * ants take smaller routes (refer to Fig. 5, denoted by blue) and finally the last ant takes the smallest 
 * route. Various plots and data outputters are added in the demo.
 * 
 * See:
 *
 * http://code.google.com/p/cscs-repast-demos/wiki/followTheLeader
 *
 * for a full description of the model and details, a tutorial on making this model is also available and can
 * be read online here :
 * 
 * http://code.google.com/p/cscs-repast-demos/wiki/followTheLeaderDescription
 * 
 * or get the pdf file here :
 * 
 * http://code.google.com/p/cscs-repast-demos/wiki/followTheLeaderDownload
 * 
 * @author Mudit Raj Gupta
 *
 * */


class Ant extends BaseTurtle 
{
	//variable to set the state of the ant
	def leader=0
	def follower=0
	def moved = 0
	def enable = 0
	def disable=0
	
	//called at each tick		
	def step()
	{
		//If the user wants gradient, i.e. blue lines after every specified number of turtles
		if(select)
		{
			//If this is the turtle that should draw
			if(mod((getWho()-first),grad)==0&&(!leader))
			{
				//set color to blue and pen down to draw
				setColor(blue())
				penDown()
			}
		}
		
		//If it's the last turtle
		if(getWho()==first+numAnts-1)
		{
			//set color to green 
			setColor(green())
			
			//make the pensize thick
			setPenSize(3)
			
			//put the pen down
			penDown()
		}	
		
		//If ant has not reached to the food pile		
		if(!disable)
		{
			//If it's a leader ant
			if(leader)
			{
				//face food
				facexy(8,0)
				
				//put pen down
				penDown()
				
				//If the deviation angle is not random
				if(!ran)
				{
				if(random(2))
					left(dev)
				else
					left(-1*dev)
				}
				
				//If deviation angle is random
				else
				{
					if(random(2))
						left(random(dev))
					else
						left(-1*random(dev))
				}
				
			}
		
		//If it is a follower ant
		else
		{
			//If the turtle infront has already traveled "lapse" number of steps
			if(turtle(getWho()-1).moved>lapse)
			{
				//get out of nest
				enable=1
				
				//become visible
				st()
				
				//face your leader, i.e. the turtle infront
				face(turtle(getWho()-1))
			}	
		}
		
		//If travelling from nest to food
		if(enable||leader)
		{
			//Increment moved variable
			moved++
			
			//move forward
			forward(0.5)
			
			//If food found
			if(patchHere().food)
			{
				    //hide turtle
					ht()
					
					//disable it
					disable=1
					
					//save the value to present, i.e. the last turtle reached food
					present=getWho()
					
					//save the length of route
					patch(0,0).presentRoute=moved
			}
		}
	}
		else
		{
			//If disabled and leader
			if(leader)
			    //save route length
				patch(0,0).firstRoute = moved
		}
	
}

//sets ant as leader 		
def setLeader()
{
	leader=1
	first=getWho()
	present=first
}

//sets ant as follower
def setFollower()
{
	if(!leader)
	{
		ht()
		follower=1
	}
}
}
