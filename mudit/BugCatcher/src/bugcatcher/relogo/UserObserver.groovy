package bugcatcher.relogo

import static repast.simphony.relogo.Utility.*;
import static repast.simphony.relogo.UtilityG.*;
import repast.simphony.relogo.BaseObserver;
import repast.simphony.relogo.Stop;
import repast.simphony.relogo.Utility;
import repast.simphony.relogo.UtilityG;

/**
* 
* This class has some important functions like go() and setp().
* Two different turtle types are created through the setup() function.
* First, a dynamic turtle type - "bug" is created and then static 
* "Machine" is created. Both at random coordinates.
*
* @author Mudit Raj Gupta
*
*/


class UserObserver extends BaseObserver{

	def setup(){
		
		clearAll()
		
		setDefaultShape(Bug,"bug")
		createBugs(numBugs){
			setxy(randomXcor(),randomYcor())
		}
		setDefaultShape(Machine,"circle")
		createMachines(numCatcher){
			setxy(randomXcor(),randomYcor())
			size=3
		}
		}
				
		  
		def go(){
			ask(bugs()){
				step()
			}
		}

}