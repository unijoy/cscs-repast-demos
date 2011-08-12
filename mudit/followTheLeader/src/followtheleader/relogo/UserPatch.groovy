package followtheleader.relogo

import static repast.simphony.relogo.Utility.*;
import static repast.simphony.relogo.UtilityG.*;
import repast.simphony.relogo.BasePatch;
import repast.simphony.relogo.Stop;
import repast.simphony.relogo.Utility;
import repast.simphony.relogo.UtilityG;
import repast.simphony.relogo.ast.Diffusible;

class UserPatch extends BasePatch
{
	@Diffusible
	def food=0
	def nest=0
	def first=-1
	def present=-1
	
	def setFood()
	{
		if(getPxcor()==8&&getPycor()==0)
		{
			food=1
			setPcolor(green())
		}
	}
	
	def setNest()
	{
		if(getPxcor()==-8&&getPycor()==0)
		{
			nest=1
			setPcolor(yellow())
		}
	}
}