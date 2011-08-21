package randomwalk.relogo

import static repast.simphony.relogo.Utility.*;
import static repast.simphony.relogo.UtilityG.*;
import repast.simphony.relogo.BasePatch;
import repast.simphony.relogo.BaseTurtle;
import repast.simphony.relogo.Plural;
import repast.simphony.relogo.Stop;
import repast.simphony.relogo.Utility;
import repast.simphony.relogo.UtilityG;

class Walker extends BaseTurtle {
	
	def move=numEnergy
	def pen=0
		
	def step()
	{
		if(done)
		{
				ht()
				pen=1
		}
		if(move>0)
		{
			if(pen==0)
				pd()
			face(oneOf(neighbors4()))
			fd(stepSize)
			move--
			visited=1
		}
		else
		{
			setColor(yellow())
		}
		}
	
	}
	

