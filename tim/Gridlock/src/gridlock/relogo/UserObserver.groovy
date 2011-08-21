package gridlock.relogo

import static repast.simphony.relogo.Utility.*;
import static repast.simphony.relogo.UtilityG.*;
import com.sun.org.apache.xalan.internal.xsltc.compiler.FloorCall;
import repast.simphony.relogo.BaseObserver;
import repast.simphony.relogo.Stop;
import repast.simphony.relogo.Utility;
import repast.simphony.relogo.UtilityG;

/**
 * Simple traffic simulator to demonstrate how gridlock can occur at intersections.  Based on
 * 
 * Wilensky, U. (2003). NetLogo Traffic Grid model. http://ccl.northwestern.edu/netlogo/models/TrafficGrid.
 *   Center for Connected Learning and Computer-Based Modeling, Northwestern University, Evanston, IL.
 * 
 * @author Tim Sweda
 *
 */

class UserObserver extends BaseObserver{

	def deltaX, deltaY, phase
	
	def setup() {
	    clearAll()
	    phase = 0
	    // Create road network
		deltaX = worldWidth()/numNSRoads
	    deltaY = worldHeight()/numEWRoads
		setupPatches()
	    setDefaultShape(Car, "car")
		// If user has specified more cars than there are road patches, print error message
		if (numCars > count(roads))
	        userMessage("ERROR:  Number of cars exceeds number of road patches.")
		// Else, place cars on roads
	    else {
			createCars(numCars) {
				moveTo(oneOf(roads.with({!anyQ(carsOn(it))})))
				if (floor(mod(pxcor+maxPxcor+deltaX/2+1, deltaX)) == 0)
					heading = 0
				else
					heading = 90
			}
	    }
	}

	def go() {
	    // Set signals if phase is back to 0
		if (phase == 0)
			ask (intersections) {
				greenNSQ = !greenNSQ
				setSignal()
			}
		// Move cars
		ask (cars()) {
	        step()
	    }
	    phase = mod(phase+1, ticksPerCycle)
	}

	// Create roads, intersections, and traffic signals
	def setupPatches() {
	    ask (patches()) {
	        greenNSQ = false
	        row = -1
	        col = -1
	        pcolor = gray()
	    }
	    roads = patches().with({floor(mod(pxcor+maxPxcor+deltaX/2+1, deltaX)) == 0 || floor(mod(pycor+maxPycor+deltaY/2+1, deltaY)) == 0})
		intersections = roads.with({floor(mod(pxcor+maxPxcor+deltaX/2+1, deltaX)) == 0 && floor(mod(pycor+maxPycor+deltaY/2+1, deltaY)) == 0})
		ask (roads) {
			pcolor = white()
	    }
		// Set signals at intersections based on signalConfig
		ask (intersections) {
	        row = floor((pycor+maxPycor+1)/deltaY+0.5)
	        col = floor((pxcor+maxPxcor+1)/deltaX+0.5)
			if (signalConfig == "AllSync")
				greenNSQ = true
			else if (signalConfig == "NSAltEWSync") {
				if (mod(row, 2) == 0)
					greenNSQ = true
			}
			else if (signalConfig == "NSAltEWAlt") {
				if (mod(row+col, 2) == 0)
					greenNSQ = true
			}
			else {
				if (random(2) == 0)
					greenNSQ = true
			}
	        setSignal()
	    }
	}

}