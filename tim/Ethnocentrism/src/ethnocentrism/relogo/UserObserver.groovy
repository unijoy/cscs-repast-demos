package ethnocentrism.relogo

import static repast.simphony.relogo.Utility.*;
import static repast.simphony.relogo.UtilityG.*;
import repast.simphony.relogo.BaseObserver;
import repast.simphony.relogo.Stop;
import repast.simphony.relogo.Utility;
import repast.simphony.relogo.UtilityG;

/**
 * Adaptation of ethnocentrism model found in
 * 
 * Hammond, R. A., & Axelrod, R. (2006). The Evolution of Ethnocentrism. Journal of Conflict Resolution 50(6), 926-936.
 * 
 * @author Tim Sweda
 *
 */

class UserObserver extends BaseObserver{

	def setupQ = false
	def tickCounter
	
	def setup() {
		clearAll()
		setupQ = true
		tickCounter = 0
		// Define set of possible turtle colors
		tagColors = [red(), yellow(), blue(), magenta()]
		ask (patches()) {
			setPcolor(gray()+2)
		}
		def zeros = []
		while (length(zeros) < 100) {
			zeros = lput(0, zeros)
		}
		// Initialize statistics
		altruists = zeros
		ethnocentrists = zeros
		xenocentrists = zeros
		egoists = zeros
		cooperation = zeros
		altT = 0
		ethT = 0
		xenT = 0
		egoT = 0
	}
	
	def go() {
		tickCounter++
		coopT = 0
		interactT = 0
		def openPatches = patches().with({!anyQ(turtlesHere())})
		// New turtles immigrate
		createTurtles(immigrationRate) {
			moveTo(oneOf(openPatches))
			initialize()
		}
		// Reset PTR of all turtles
		ask (turtles()) {
			PTR = basePTR
		}
		// Turtles interact with each other, offering and receiving help
		ask (turtles()) {
			interact()
		}
		// Turtles reproduce
		ask (turtles()) {
			reproduce()
		}
		// Turtles die
		ask (turtles()) {
			if (randomFloat(1) < deathRate) {
				setPcolor(gray()+2)
				// Update stats
				if (inGroupCoopQ) {
					if (outGroupCoopQ) {
						altT--
					}
					else {
						ethT--
					}
				}
				else {
					if (outGroupCoopQ) {
						xenT--
					}
					else {
						egoT--
					}
				}
				die()
			}
		}
		// Update statistics
		def totalPop = max([1, count(turtles())])
		altruists = butFirst(lput(altT/totalPop, altruists))
		ethnocentrists = butFirst(lput(ethT/totalPop, ethnocentrists))
		xenocentrists = butFirst(lput(xenT/totalPop, xenocentrists))
		egoists = butFirst(lput(egoT/totalPop, egoists))
		cooperation = butFirst(lput(coopT/max([1, interactT]), cooperation))
	}
	
	// Calculate and report prevalence of each behavior type and percent of interactions that are cooperative, using average of last 100 ticks (if available)
	def reportStats() {
		// If Setup button has not been pressed, there is nothing to report
		if (!setupQ) {
			setup()
			userMessage("No statistics to report (simulation has not yet begun).")
		}
		// Else if simulation has run for at least 100 ticks, report averages of last 100 ticks
		else if (tickCounter > 100) {
			def alt = 100*mean(altruists)+"%\n"
			def eth = 100*mean(ethnocentrists)+"%\n"
			def xen = 100*mean(xenocentrists)+"%\n"
			def ego = 100*mean(egoists)+"%\n\n"
			def coop = 100*mean(cooperation)+"%"
			userMessage("Altruists:  "+alt+"Ethnocentrists:  "+eth+"Xenocentrists:  "+xen+"Egoists:  "+ego+"Cooperation:  "+coop)
		}
		// Else if simulation has run for at least 1 tick, report average for the entire simulation so far
		else if (tickCounter > 0) {
			def i = 0
			while (item(i, altruists)+item(i, ethnocentrists)+item(i, xenocentrists)+item(i, egoists) == 0) {
				i++
			}
			def alt = 100*mean(sublist(altruists, i, 100))+"%\n"
			def eth = 100*mean(sublist(ethnocentrists, i, 100))+"%\n"
			def xen = 100*mean(sublist(xenocentrists, i, 100))+"%\n"
			def ego = 100*mean(sublist(egoists, i, 100))+"%\n\n"
			def coop = 100*mean(sublist(cooperation, i, 100))+"%"
			userMessage("Altruists:  "+alt+"Ethnocentrists:  "+eth+"Xenocentrists:  "+xen+"Egoists:  "+ego+"Cooperation:  "+coop)
		}
		// Otherwise, there are no statistics to report
		else {
			userMessage("No statistics to report (simulation has not yet begun).")
		}
	}

}