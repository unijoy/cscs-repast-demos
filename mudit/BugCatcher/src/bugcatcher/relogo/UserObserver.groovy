package bugcatcher.relogo

import static repast.simphony.relogo.Utility.*;
import static repast.simphony.relogo.UtilityG.*;
import repast.simphony.relogo.BaseObserver;
import repast.simphony.relogo.Stop;
import repast.simphony.relogo.Utility;
import repast.simphony.relogo.UtilityG;

/**
* 
* This class has some important functions like go() and setp().
* Two different types are created through the setup() function.
* First, a turtle type - "bug" is created and then "Machine" patches 
* are created. Both at random coordinates.
*
* @author Mudit Raj Gupta
*
*/


class UserObserver extends BaseObserver{

	//setting up the world
	def setup()
	{
		//clears everything
		clearAll()
		
		//Creating bugs
		setDefaultShape(Bug,"bug")
		createBugs(numBugs){
			setxy(randomXcor(),randomYcor())
			setColor(red())
		}
		
		//setting patches
		def i
		for(i=0;i<numCatcher;i++)
		{
			patch(randomXcor(),randomYcor()).machine=1
			
		}
		
		//setting up patches
		ask(patches())
		{
		setAll()
		}}
			
		//making bugs move  
		def go()
		{
			ask(bugs())
			{
				step()
			}
			
			//incrementing time
			tick()
			
			//counts the number of bugs alive
			BLeft()
		}
		
		//counting the number of bugs left
		def BLeft()
		{
			count(bugs())
		}
		
		//for incrementing time
		def timeCount()
		{
			ticks()
		}

}