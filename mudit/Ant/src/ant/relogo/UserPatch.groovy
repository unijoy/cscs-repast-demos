package ant.relogo

import static repast.simphony.relogo.Utility.*;
import static repast.simphony.relogo.UtilityG.*;
import repast.simphony.relogo.BasePatch;
import repast.simphony.relogo.Stop;
import repast.simphony.relogo.Utility;
import repast.simphony.relogo.UtilityG;
import repast.simphony.relogo.ast.Diffusible;

/**
* 
* Different types of patches are creared, each distinguished by a set of variables.
* 
* @author Mudit Raj Gupta
*
*/
	
	class UserPatch extends BasePatch{
		
		//making variables diffusible, i.e. available to others
		@Diffusible
		def level = 0
		def cubes1 = 0
		def cubes2 = 0
		def cubes3 = 0
		
		//variables for setting patch state
		def dropped = 0
		def diffused = 0
		def val=3
		def lim=val
		
		//converts a patch to nest patch
		def makeNest()
		{
			setPcolor(magenta())
		}
		
		//Converts a patch to a Food Pile patch
		def makePile1()
		{
			setPcolor(orange())
			cubes1++
			level=1000
		}
		def makePile2()
		{
			setPcolor(violet())
			cubes2++
			level=1000
		}
		def makePile3()
		{
			setPcolor(yellow())
			cubes3++
			level=1000
		}
		
		//makes the chemical trail die with time
		def evaporate()
		{
			if(diffused)
			{
				//color shading according to value
				pcolor = scaleColor(yellow(),level,0,40)
				level++
				if(level>lim)
				{
					//resets
					setPcolor(black())
					level=0
					diffused=0
					lim=val
				}
			}
			if(dropped)
			{
				pcolor = scaleColor(green(),level,0,120)
				level=level+3
				if(level>(lim*6))
				{
					setPcolor(black())
					level=0
					dropped=0
					lim=val
				}
			}
			
			//shows cubes, if they are left
			if(cubes1>0)
				setPcolor(orange())
			if(cubes2>0)
				setPcolor(violet())
			if(cubes3>0)
				setPcolor(yellow())
		}
		
		//set patch value to a patch where an ant carrying food passed
		def set_main()
		{
			if(dropped)
				level=0
			else
				dropped=1
		}
		
		//set patch value as a neighbor of a patch where ant carrying food passed		
		def set_diffused()
		{
			if(diffused)
				level=0
			else
				diffused=1
		}
		
		//resets a food pile patch
		def chkFood()
		{
			if(cube1==0&&cube2==0&&cube3==0)
			setPcolor(black())
		}
		
		//resets patch variables
		def resetAll()
		{
			 level = 0
			 dropped = 0
			 diffused = 0
			 val=20
			 lim=val
			 cubes1=0
			 cubes2=0
			 cubes3=0
		}
		
}
		
	

