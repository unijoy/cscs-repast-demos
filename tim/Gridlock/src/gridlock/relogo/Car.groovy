package gridlock.relogo

import static repast.simphony.relogo.Utility.*;
import static repast.simphony.relogo.UtilityG.*;
import repast.simphony.relogo.BasePatch;
import repast.simphony.relogo.BaseTurtle;
import repast.simphony.relogo.Plural;
import repast.simphony.relogo.Stop;
import repast.simphony.relogo.Utility;
import repast.simphony.relogo.UtilityG;

/**
 * @author Tim Sweda
 *
 */

class Car extends BaseTurtle {
	
	def speed = 0
	
	def step() {
		// If at a red light, stop
		if (pcolor == red())
			speed = 0
		else {
			def dx, dy
			if (heading == 0)
				dy = 1.001 // (If 1.0 is used here instead, cars may get stuck on patch borders)
			else
				dy = 0
			dx = 1.001-dy
			def carsAhead = carsAt(dx, dy)
			if (anyQ(carsAhead)) {
				// If blocked by cars heading in the other direction, stop
				if (anyQ(carsAhead.with{heading != {heading}.of(myself())}))
					speed = 0
				// Else, if car ahead is heading in same direction, slow down
				else
					speed = max([oneOf(carsAhead).speed-accel, 0])
			}
			// If road ahead is clear, accelerate
			else
				speed = min([speed+accel, speedLimit])
		}
		fd(speed)
	}

}
