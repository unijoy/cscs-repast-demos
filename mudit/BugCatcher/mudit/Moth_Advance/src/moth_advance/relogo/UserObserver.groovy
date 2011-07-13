package moth_advance.relogo


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
		
		setDefaultShape(Moth,"default")
		createMoths(numMoths){
			setxy(randomXcor(),randomYcor())
		}
		setDefaultShape(Light,"circle")
		createLights(numLights){
			setxy(randomXcor(),randomYcor())
			
		}
		}
				
		  
		def go(){
			ask(lights()){
				step()
			}
			ask(moths()){
				step()
			}
			}
		}
