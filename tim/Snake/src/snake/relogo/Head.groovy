package snake.relogo

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

class Head extends BaseTurtle {
	
	def step() {
		def headPcolor = {pcolor}.of(headPatch)
		// If snake is scheduled to turn, turn then update schedule
		if (count(turnSchedule) > 0) {
			right(first(turnSchedule))
			turnSchedule = butFirst(turnSchedule)
		}
		// Move snake forward
		forward(1)
		// If snake eats food, increment player's score and create a new food patch elsewhere
		if (getPcolor() == foodColor) {
			setPcolor(foodColor+4)
			score++
			ask (oneOf(patches().with({pcolor == neutralColor}))) {
				pcolor = foodColor
			}
		}
		// Update snake's body
		ask (headPatch) {
			pcolor = obstacleColor
		}
		body = fput(headPatch, body)
		headPatch = patchHere()
		// If snake just ate food, increase its body's length by 1
		if (headPcolor != foodColor+4) {
			ask (last(body)) {
				pcolor = neutralColor
			}
			body = butLast(body)
		}
		// If snake ran into an obstacle, then game over
		if ({pcolor}.of(headPatch) == obstacleColor) {
			setShape("x")
			userMessage("Game Over")
			observerStop()
		}
	}

}
