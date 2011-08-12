package followtheleader.relogo

import static repast.simphony.relogo.Utility.*;
import static repast.simphony.relogo.UtilityG.*;
import repast.simphony.relogo.BasePatch;
import repast.simphony.relogo.BaseTurtle;
import repast.simphony.relogo.Plural;
import repast.simphony.relogo.Stop;
import repast.simphony.relogo.Utility;
import repast.simphony.relogo.UtilityG;

class Ant extends BaseTurtle 
{
	def leader=0
	def follower=0
	def moved = 0
	def enable = 0
	def disable=0
	
	def step()
	{
		if(select)
		{
			if(mod((getWho()-first),grad)==0&&(!leader))
			{
				setColor(blue())
				penDown()
			}
		}
		
		if(getWho()==first+numAnts-1)
		{
			setColor(green())
			setPenSize(3)
			penDown()
		}	
		
		if(!disable)
		{
			if(leader)
			{
				facexy(8,0)
				penDown()
				if(random(2))
					left(random(dev))
				else
					left(-1*random(dev))
		}
		
		else
		{
			if(turtle(getWho()-1).moved>lapse)
			{
				enable=1
				st()
				face(turtle(getWho()-1))
			}	
		}
		
		if(enable||leader)
		{
			moved++
			forward(0.5)
			if(patchHere().food)
			{
					ht()
					disable=1
					present=getWho()
			}
		}
	}
}
	
def setLeader()
{
	leader=1
	first=getWho()
}

def setFollower()
{
	if(!leader)
	{
		ht()
		follower=1
	}
}
}
