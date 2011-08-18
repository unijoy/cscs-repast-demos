package guidingbots.relogo

import static repast.simphony.relogo.Utility.*;
import static repast.simphony.relogo.UtilityG.*;
import repast.simphony.relogo.BaseObserver;
import repast.simphony.relogo.Stop;
import repast.simphony.relogo.Utility;
import repast.simphony.relogo.UtilityG;

/**
*
* This class has some important functions like go() and setup().
* Two different types of turtles are created through the setup() function.
* First, a turtle type - "guidingMotes" is created and then "daughterMotes" 
* is created. Both at random coordinates. Background is also set in this
*
* @author Mudit Raj Gupta
*
*/

class UserObserver extends BaseObserver
{
	
	//sets up the world
	def setup()
	{
		//clears everything
		clearAll()
		
		//creates guiding motes
		setDefaultShape(guidingMote, "pentagon")
		
		createGuidingMotes(gm)
		{
			setxy(randomXcor(),randomYcor())
			setColor(yellow())
			size=2
		}
		
		//creates daughter motes
		setDefaultShape(daughterMote, "pentagon")
		
		createDaughterMotes(dm)
		{
			setxy(randomXcor(),randomYcor())
			setColor(black())
			
		}
		
		//sets background
		ask (patches())
		{
			setBackGround()
		}
	}
	
	def go()
	{
		//ask motes to move
		ask(guidingMotes())
		{
			step()
		}
		ask(daughterMotes())
		{
			step()
		}
		
		//increment tick
		tick()
	}
	
	//counts the number of killed daughter motes
	def killedDaughter (){
	count (daughterMotes())
	}
	
	//increment time
	def timestamp (){
	ticks ()
	}
}