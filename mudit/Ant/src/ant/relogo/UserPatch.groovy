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
		
		def dropped = 0
		def diffused = 0
		def val=10
		def lim=val
		
		def makeNest()
		{
			setPcolor(magenta())
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
				if(level>(lim*3))
				{
					setPcolor(black())
					level=0
					dropped=0
					lim=val
				}
			}
		}
		
		def set_main()
		{
			if(dropped)
				lim=lim+val
			else
				dropped=1
		}
		
		def set_diffused()
		{
			if(diffused)
				lim=lim+val
			else
				diffused=1
		}
		def resetAll()
		{
			 level = 0
			 dropped = 0
			 diffused = 0
			 val=20
			 lim=val
		}
		
}
		
	

