package randomwalk.relogo

import static repast.simphony.relogo.Utility.*;
import static repast.simphony.relogo.UtilityG.*;
import repast.simphony.relogo.BaseObserver;
import repast.simphony.relogo.Stop;
import repast.simphony.relogo.Utility;
import repast.simphony.relogo.UtilityG;

class UserObserver extends BaseObserver{

	def c
	def setup(){
			clearAll()
			createWalkers(numWalker){
				setxy(0,0)
				setColor(red())
						}
		}
	
		def go(){
			c=0
			ask(walkers()){
				step()
				tick()
			}
			nodesCovered()
		}
		
		def nodesCovered()
		{
			ask(patches())
				{
					colorCovered()
					if(visited)
						c++
				}
				if(c>200)
				{
					ask(patches())
					{
						done=1
					}
					ask(walkers())
					{
						setColor(green())
					}
				}
		} 


}