package ant.relogo

import static repast.simphony.relogo.Utility.*;
import static repast.simphony.relogo.UtilityG.*;
import repast.simphony.relogo.BasePatch;
import repast.simphony.relogo.Stop;
import repast.simphony.relogo.Utility;
import repast.simphony.relogo.UtilityG;
import repast.simphony.relogo.ast.Diffusible;

	
	class UserPatch extends BasePatch{
		
		@Diffusible
		def level = 0
		def cubes = 0
		
		def dropped = 0
		def diffused = 0
		def val=3
		def lim=val
		
		def makeNest()
		{
			setPcolor(magenta())
		}
		
		//Converts a patch to a Food Pile patch
		def makePile()
		{
			setPcolor(orange())
			cubes++
			level=1000
		}
		
		def evaporate()
		{
			if(diffused)
			{
				pcolor = scaleColor(yellow(),level,0,40)
				level++
				if(level>lim)
				{
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
			if(cubes>0)
				setPcolor(orange())
		}
		
		def set_main()
		{
			if(dropped)
				level=0
			else
				dropped=1
		}
		
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
			if(cube==0)
			setPcolor(black())
		}
		def resetAll()
		{
			 level = 0
			 dropped = 0
			 diffused = 0
			 val=20
			 lim=val
			 cubes=0
		}
		
}
		
	

