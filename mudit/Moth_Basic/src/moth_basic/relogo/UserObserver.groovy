package moth_basic.relogo

import static repast.simphony.relogo.Utility.*;
import static repast.simphony.relogo.UtilityG.*;
import repast.simphony.relogo.BaseObserver;
import repast.simphony.relogo.Stop;
import repast.simphony.relogo.Utility;
import repast.simphony.relogo.UtilityG;

/**
*
* This class has some important functions like go() and setup().
* Moths are created according to the population entered through
* the user interface. The motes are placed randomly.  
*
* @author Mudit Raj Gupta
*
*/


class UserObserver extends BaseObserver{

	def setup(){
		
		//Clears everything
		clearAll()
		
		//creating Moths
		setDefaultShape(Moth,"default")
		createMoths(numMoths){
			setxy(randomXcor(),randomYcor())
		}
		}
					
	    //go() function calls step() 
		def go(){
			ask(moths()){
				step()
			}
		}

}