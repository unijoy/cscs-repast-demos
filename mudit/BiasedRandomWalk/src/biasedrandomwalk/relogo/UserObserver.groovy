package biasedrandomwalk.relogo



import static repast.simphony.relogo.Utility.*;
import static repast.simphony.relogo.UtilityG.*;
import repast.simphony.relogo.BaseObserver;
import repast.simphony.relogo.Stop;
import repast.simphony.relogo.Utility;
import repast.simphony.relogo.UtilityG;

class UserObserver extends BaseObserver{

	def c=0
	def setup(){
			clearAll()
			createWalkers(numWalker){
				setxy(-8,0)
				setColor(139)
						}
			ask(patches())
			{
				setBiasPoint()
			}
		}
	
		def go(){
			c=0
			ask(walkers()){
				step()
			}
			tick()
			biasRegion()
		}
		
		def biasRegion()
		{
			def i
			def j
			for(i=9-(stepSize*0.5);i<9+(stepSize*0.5);i++)
			{
				for(j=-16;j<16;j++)
					c=c+patch(i,j).visited
			}	
		}


}