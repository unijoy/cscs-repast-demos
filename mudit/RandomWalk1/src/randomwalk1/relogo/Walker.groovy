package randomwalk1.relogo


import static repast.simphony.relogo.Utility.*;
import static repast.simphony.relogo.UtilityG.*;
import repast.simphony.relogo.BasePatch;
import repast.simphony.relogo.BaseTurtle;
import repast.simphony.relogo.Plural;
import repast.simphony.relogo.Stop;
import repast.simphony.relogo.Utility;
import repast.simphony.relogo.UtilityG;

class Walker extends BaseTurtle {
	
	def move=random(numEnergy)
	def cl=140
	def activate=0
		
	def step()
	{
		if(activate)
		{
			
		if(move>0)
		{
			pd()
			face(oneOf(neighbors()))
			fd(0.5*stepSize)
			move--
			if(move%10==0)
			{
				cl=cl-3
				setColor(cl)
			}
			visited=1
		}
		else
		{
			die()
		}
		}
	else
	{
		ask(neighbors())
		{
			
		if(count(walkersOn(it))>0)
			activate=1
		}
	}
	}
}
