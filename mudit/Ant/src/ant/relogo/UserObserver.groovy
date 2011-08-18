package ant.relogo

import static repast.simphony.relogo.Utility.*;
import static repast.simphony.relogo.UtilityG.*;
import repast.simphony.relogo.BaseObserver;
import repast.simphony.relogo.Stop;
import repast.simphony.relogo.Utility;
import repast.simphony.relogo.UtilityG;

/**
* 
* In this class, the environment is set up and ants are created. Few variables are 
* used to keep track of food left in each pile. The ants are made to move and the patches 
* are forced to evaporate chemical.
*
* @author Mudit Raj Gupta
*
*/

class UserObserver extends BaseObserver{
	
	//Cubes remaining in each pile
	def rem1=0
	def rem2=0
	def rem3=0
	
	//Called to set-up the world
	def setup()
	{
		//Clears the world
		clearAll()
		
		//resets all patch variables
		ask(patches())
		{
			resetAll()
		}
		
		//loop variables
		def i
		def j
		
		//Making Nest
		for(i=-1;i<3;i++)
		{
			for(j=-1;j<3;j++)
				patch(i,j).makeNest()
		}
		
		//Setting default shape to turtle
		setDefaultShape(Ant, "turtle")
		
		//Creating ants
		createAnts(numAnts)
		{
			setxy(0,0)
			setColor(red())
		}
		
		//Creating food piles	
		for(i=0;i<numCubes;i++)
		{
			patch(-14+random(4),10+random(4)).makePile1()
			patch(8+random(4),0+random(4)).makePile2()
			patch(-14+random(4),-6+random(4)).makePile3()
		}
	}

	def go()
	{
		//resets food remaining
		rem1=0
		rem2=0
		rem3=0
		
		//calls the step() function of all Ant.groovy class for all ants
		ask(ants())
		{
			step()
		}
	
		//calls evaporate function for all parches (UserPatch.groovy)
		ask(patches())
		{
			evaporate()
		}
		
		//sets food left in rem1, rem2 and rem3
		foodLeft()
		
		//increments time
		tick()
	}
	
	//sets food left
	def foodLeft()
	{
		ask(patches())
		{
			rem1=rem1+cubes1
		}
		ask(patches())
		{
			rem2=rem2+cubes2
		}
		ask(patches())
		{
			rem3=rem3+cubes3
		}
	}
	
	//Time Counter
	def timeCount()
	{
		ticks()
	}
}