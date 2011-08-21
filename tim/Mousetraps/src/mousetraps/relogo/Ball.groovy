package mousetraps.relogo

import static repast.simphony.relogo.Utility.*;
import static repast.simphony.relogo.UtilityG.*;
import repast.simphony.relogo.BasePatch;
import repast.simphony.relogo.BaseTurtle;
import repast.simphony.relogo.Plural;
import repast.simphony.relogo.Stop;
import repast.simphony.relogo.Utility;
import repast.simphony.relogo.UtilityG;

/**
*
* @author Tim Sweda
*
*/

class Ball extends BaseTurtle {
	
	def initialize() {
		color = white()
		size = 0.75
	}
	
	// Move this ball in a random direction and for a random distance (up to maxDistance)
	def bounce() {
		right(randomFloat(360))
		def dist = randomFloat(maxDistance)
		// If destination is within the grid boundaries, move this Ball to the destination
		if (abs(xcor+dist*cos(heading)) < worldWidth()/2 && abs(ycor+dist*sin(heading)) < worldHeight()/2)
			forward(dist)
		// Else, remove this ball from the simulation (since it is "out of bounds")
		else
			die()
	}

}
