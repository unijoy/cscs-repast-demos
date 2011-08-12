package ant.relogo

import static repast.simphony.relogo.Utility.*;
import static repast.simphony.relogo.UtilityG.*;
import repast.simphony.relogo.BaseObserver;
import repast.simphony.relogo.Stop;
import repast.simphony.relogo.Utility;
import repast.simphony.relogo.UtilityG;

class UserObserver extends BaseObserver{
	def setup()
	{
		clearAll()
		ask(patches())
		{
			resetAll()
		}
		def i
		def j
		for(i=-1;i<3;i++)
		{
			for(j=-1;j<3;j++)
				patch(i,j).makeNest()
		}
		setDefaultShape(Ant, "turtle")
		createAnts(numAnts)
		{
			setxy(0,0)
			setColor(red())
		}
		setDefaultShape(Cube, "square")
		createCubes(50)
		{
			setxy(-10+random(4),10+random(4))
			setColor(orange())
		}
		createCubes(50)
		{
			setxy(8+random(4),0+random(4))
			setColor(pink())
		}
		createCubes(50)
		{
			setxy(-8+random(4),-4+random(4))
			setColor(turquoise())
		}
		
		setDefaultShape(ActiveChem, "square")
				
	}

	def go()
	{
		ask(activeChems())
		{
			step()
		}
		ask(ants())
		{
			step()
		}
		ask(cubes())
		{
			step()
		}
		ask(patches())
		{
			evaporate()
		}
			
	}
}