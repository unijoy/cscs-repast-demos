package randomwalk1.relogo


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
			createWalkers(1){
				setxy(randomXcor(),randomYcor())
				setColor(139)
				activate=1
						}
			createWalkers(numWalker-1){
				setxy(randomXcor(),randomYcor())
				setColor(139)
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
					if(visited)
						c++
				}
		}


}