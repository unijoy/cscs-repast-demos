package mousetraps.relogo

import static repast.simphony.relogo.Utility.*;
import static repast.simphony.relogo.UtilityG.*;
import repast.simphony.relogo.BaseObserver;
import repast.simphony.relogo.Stop;
import repast.simphony.relogo.Utility;
import repast.simphony.relogo.UtilityG;

/**
 * Classic experiment for demonstrating how nuclear fission occurs.  Similar to Mousetrap model written
 * using RepastS Java.
 * 
 * @author Tim Sweda
 *
 */

class UserObserver extends BaseObserver{

	def snappedTraps // Counter for number of mousetraps that have been snapped
	
	def setup() {
		clearAll()
		snappedTraps = 0
		ask (patches()) {
			pcolor = brown()+3
		}
		setDefaultShape(Ball, "circle")
		// Create initial ball to snap the first trap
		createBalls(1) {
			initialize()
		}
	}
	
	def go() {
		def snapCount = snappedTraps
		ask (balls()) {
			// If the ball lands on an unsnapped trap, snap the trap and release the balls from that trap
			if (pcolor != red()) {
				pcolor = red()
				snappedTraps++
				hatch(ballsPerTrap) {
					initialize()
					bounce()
				}
				// This ball bounces again
				bounce()
			}
			// Else, this ball stops bouncing
			else
				die()
		}
		// If no new traps have been snapped, then the simulation is over
		if (snapCount == snappedTraps)
			stop()
	}
	
	// Return percentage of traps that have been snapped
	def pctSnapped() {
		return snappedTraps/(worldWidth()*worldHeight())*100
	}

}