package ant.relogo

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
	def inNest=1
	def away=1
	def gotCube=0
	def nearChem=0
	def Xi = 0
	def Yi = 0
	def Xf = 0
	def Yf = 0
		
	def step()
	{	
		Xi = getXcor()
		Yi = getYcor()
		foundCube()
		foundNest()
		checkChem()
		def dist = distancexy(0,0)
		if(gotCube||getXcor()>15||getYcor()>15||getXcor()<-15||getYcor()<-15)
			away=0
		if(inNest)
			away=1
		if(gotCube)
			drop()
		decide()
		Xf=getXcor()
		Yf=getYcor()
		if(Xi==Xf&&Yi==Yf)
		{
			nearChem=0
			patchHere().level-=2
			decide()
		}
		
	}
	def decide()
	{
		if(!nearChem)
		{
			if(away)
				goAway()
			else
				comeBack()
		}
	}
	def goAway()
	{
		facexy(0,0)
		left(180)
		move()
	}
	def comeBack()
	{
		facexy(0,0)
		move()
	}
	def move()
	{
		if(random(2))
			left(random(90))
		else
			left(-1*random(90))
		forward(0.8)
	}
	def foundCube()
	{
		if(patchHere().cubes>0)
		{
				setColor(blue())
				gotCube=1
				patchHere().cubes--
		}
	}
	def foundNest()
	{
		if(distancexy(0,0)<4)
		{
			setColor(red())
			label=""
			gotCube=0
			inNest=1
		}
		else
		{
			inNest=0
		}
		
	}
	/*def carryCube(cube)
	{
		cube.carried = 1
	}*/
	def drop()
	{
		ask(patchHere())
		{
			set_main()
		}
		ask(neighbors())
		{
			ask(patchHere())
			{
				set_diffused()
			}
		}
		
		//hatchActiveChems(1)

	}
	def checkChem()
	{
				if((patchHere().level>0)&&(!gotCube))
		{
			nearChem=1
			uphill("level")
		}		
		else
			nearChem=0
	}
	
}