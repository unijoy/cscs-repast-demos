package boids_relogo_prealpha.relogo

import static repast.simphony.relogo.Utility.*;
import static repast.simphony.relogo.UtilityG.*;
import repast.simphony.relogo.BaseObserver;
import repast.simphony.relogo.Stop;
import repast.simphony.relogo.Utility;
import repast.simphony.relogo.UtilityG;

class UserObserver extends BaseObserver{

	def setup(){
			clearAll()
			setDefaultShape(Boid,"arrow")
			createBoids(numBoids){
				setxy(randomXcor(),randomYcor())
			}
		}

	
		def go(){
			ask(boids()){
				step()
			}
		}

}