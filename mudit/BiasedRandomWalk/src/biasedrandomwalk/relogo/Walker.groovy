package biasedrandomwalk.relogo



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
			
	def step()
	{
		if(move>0)
		{
			
			pd()
			if(random(bias)!=1)
					face(oneOf(neighbors()))
			else 
					facexy(8,getYcor())
			fd(0.5*stepSize)
			move--
			if(move%10==0)
			{
				cl=cl-3
				setColor(cl)
			}
			visited++
		}
		else
			die()
		}
}
