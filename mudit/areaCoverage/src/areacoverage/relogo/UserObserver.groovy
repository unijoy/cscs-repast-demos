package areacoverage.relogo

import static repast.simphony.relogo.Utility.*;
import static repast.simphony.relogo.UtilityG.*;
import repast.simphony.relogo.BaseObserver;
import repast.simphony.relogo.Stop;
import repast.simphony.relogo.Utility;
import repast.simphony.relogo.UtilityG;

/** 
* Different Initial Geometries by generating a random number for X coordinate,
* setting the limits depending on the initial side length or radius. Y coordinate 
* is randomly generated and the limit is set by calculating the valid range of 
* Y coordinates (at the generated X-coordinate) using trigonometry.    
* 
* @author Mudit Raj Gupta
*
*/

class UserObserver extends BaseObserver
{
	def numO =0 // number of motes outside 14 units radius
	def setup()
	{
		def x 
		def y
		def theta // Half of the angle made at the center by a chord of a circle
		def lnth  // Length of the side of a Hexagon cut at a specified distance 
		
		//Clears the Space
		clearAll()
		
		//Sets shape of the Motes to be Pentagon
		setDefaultShape(Mote, "pentagon")
		
		if(shape=="Line")
		{
		
		//Creates the number of motes specified by the user
		createMotes(numMotes)
		{
			//Chooses a X coordinate less than equals to the Side Length
			x=random(initVal)
			
			//Chooses a Y coordinate less than equals to the Side Length
			y=0
			
			//Sets the coordinate where the mote will be created
			setxy(x-(initVal/2),y)
			
			setColor(yellow())
		}
		}
		
		//If initial geometry is a square
				
		if(shape=="Square")
		{
		
		//Creates the number of motes specified by the user
		createMotes(numMotes)
		{   
			//Chooses a X coordinate less than equals to the Side Length
			x=random(initVal)
			
			//Chooses a Y coordinate less than equals to the Side Length
			y=random(initVal)
			
			//Sets the coordinate where the mote will be created
				setxy(x-(initVal/2),y-(initVal/2))
				setColor(yellow())
		}
		}
		 
		//If initial geometry is a Circle
		   
		if(shape=="Circle")
		{
		
		//Creates the number of motes specified by the user
		createMotes(numMotes)
		{
			
			x=random((initVal/2)+1)
			theta=((initVal/2)-x)
			y =random(sqrt(((initVal/2)*(initVal/2))-(theta*theta))+1)
			x=x-(initVal/2)
			if(random(2))
			{
				x=(-1)*x
			}
			if(random(2))
			{
				y=(-1)*y
			}

			//Sets the coordinate where the mote will be created
			setxy(x,y)
			setColor(yellow())
		}
		}
		
		//If initial geometry is a Hexagon
		if(shape=="Hexagon")
		{
		
		//Creates the number of motes specified by the user
		createMotes(numMotes)
		{
			//
			x=random((initVal*sin(60)+1))
			
			//Randomly generates the Y coordinate depending on the value of side
			y =random(((x*tan(30))+(initVal/2))+1)
			x=x-(initVal*sin(60))						
			if(random(2))
			{
				x=(-1)*x
			}
			if(random(2))
			{
				y=(-1)*y
			}

			//Sets the coordinate where the mote will be created
			setxy(x,y)
			setColor(yellow())
		}
		}
	}
	
	//For making the mote execute it's behavior
	def go()
	{  
		numO=0
		//Calls the step() function of motes()
		ask(motes())
		{
			step()
		}
		outside()
		tick()
	}
	
	//counts number of motes outside 14 units radius
	def outside(){
			ask(motes())
			{
				if(O==1)
				 numO++
			}
		}
		
	//increments time
	def timestamp (){
	ticks ()
	}
}
	