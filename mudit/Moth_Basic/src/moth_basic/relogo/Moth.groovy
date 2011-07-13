package moth_basic.relogo

import static repast.simphony.relogo.Utility.*;
import static repast.simphony.relogo.UtilityG.*;
import repast.simphony.relogo.BasePatch;
import repast.simphony.relogo.BaseTurtle;
import repast.simphony.relogo.Plural;
import repast.simphony.relogo.Stop;
import repast.simphony.relogo.Utility;
import repast.simphony.relogo.UtilityG;

/**
* 
* Each Moth moves randomly in a given area. If a moth encounters a
* light intensity/source it tries to move only in a point where there is 
* light intensity. The moth moves at a faster speed called the Excited 
* Speed when it is in the illuminated region. The moth is illuminated (shown
* by a change in size) when it is in that region. The moth dies if it touches 
* the core of the light source. 
* 
* The Model has a fixed light source at the center (0,0). Although the size and 
* intensity of the source is variable. For multiple light sources, check out : 
* http://code.google.com/p/cscs-repast-demos/wiki/MothAdvance 
* 
* Model can be used to study the rate at which the moth discover light source and
* calculating the average life-cycle of the moth. The model can also be useful to
* study the effect of intensity in the average life cycles.
*
* See
*
* http://code.google.com/p/cscs-repast-demos/wiki/MothBasic
*
* for a full description of the model and details.
*
* @author Mudit Raj Gupta
*
* */

class Moth extends BaseTurtle {
	
	def x
	def y
	
	def step()
	{
	
	//If the moth touches the core of the source
	if(distancexy(0,0)<source*0.3)
	{
			//moth dies
			die()
	}
	
	//If the moth senses light intensity
	if(distancexy(0,0)<intensity+source)
	{
		//moth faces a point in the region where there is light intensity
		
		//x coordinate is any random number radius 
		x=random(intensity+source)
		
		//y is any random number which lies on the circle, using equation of a circle
		y=random(sqrt(((intensity+source)*(intensity+source))-(x*x)))
		
		//Randomly setting x and y as (-)ve or (+)
		if(random(2))
		{
			x=(-1)*x
		}
		if(random(2))
		{
			y=(-1)*y
		}
		
		
		//face the x and y values generated above
		facexy(x,y)
		
		//move forward a distance of excited speed * 0.6
		fd(exSpeed*0.6)
		
		//change in size to demark illumination
		size=1.5	
	}
	
	//If not in the illuminated region
	else
	{
		//Randomly face any coordinate
		x=random(140)
		y=random(140)
		
		//randomly assign sign
		if(random(2))
		{
			x=(-1)*x
		}
		if(random(2))
		{
			y=(-1)*y
		}
		
		//face the coordinate
				facexy(x,y)
				forward(nrmlSpeed*0.1)
	}
	}
		

}
	


