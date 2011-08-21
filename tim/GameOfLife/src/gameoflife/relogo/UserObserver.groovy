package gameoflife.relogo

import static repast.simphony.relogo.Utility.*;
import static repast.simphony.relogo.UtilityG.*;
import repast.simphony.relogo.BaseObserver;
import repast.simphony.relogo.Stop;
import repast.simphony.relogo.Utility;
import repast.simphony.relogo.UtilityG;

/**
 * Adaptation of John Horton Conway's cellular automaton "Life" (1970).
 * 
 * @author Tim Sweda
 *
 */

class UserObserver extends BaseObserver{

	def setup() {
		clearAll()
		liveColor = black()
		deadColor = white()
		// Create initial pattern
		ask (patches()) {
			if (randomFloat(1) < initialDensity) {
				makeLiving()
			}
			else {
				makeDead()
			}
		}
	}
	
	def go() {
		// Determine number of living neighbors for each patch
		ask (patches()) {
			liveNeighbors = sum({life}.of(neighbors()))
		}
		// Determine whether patches live or die according to the following rules:
		//   1.  A living patch with two or three living neighbors remains alive; otherwise, it dies.
		//   2.  A dead patch with exactly three living neighbors becomes alive.
		ask (patches()) {
			if (liveNeighbors == 3) {
				makeLiving()
			}
			else if (liveNeighbors != 2) {
				makeDead()
			}
		}
		// Calculate current density of living patches
		density = sum({life}.of(patches()))/count(patches())
	}
	
	// Return density of living patches
	def currentDensity() {
		return density
	}

}