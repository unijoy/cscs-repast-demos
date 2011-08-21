package ethnocentrism.relogo

import static repast.simphony.relogo.Utility.*;
import static repast.simphony.relogo.UtilityG.*;
import repast.simphony.relogo.BaseTurtle;
import repast.simphony.relogo.BasePatch;
import repast.simphony.relogo.Plural;
import repast.simphony.relogo.Stop;
import repast.simphony.relogo.Utility;
import repast.simphony.relogo.UtilityG;

/**
 * 
 * @author Tim Sweda
 *
 */

class UserTurtle extends BaseTurtle{
	
	def PTR, inGroupCoopQ, outGroupCoopQ
	
	def initialize() {
		// Initialize traits
		color = oneOf(tagColors)
		inGroupCoopQ = (randomFloat(1) < inGroupCoopProb)
		outGroupCoopQ = (randomFloat(1) < outGroupCoopProb)
		// Update stats
		if (inGroupCoopQ) {
			if (outGroupCoopQ) {
				altT++
			}
			else {
				ethT++
			}
		}
		else {
			if (outGroupCoopQ) {
				xenT++
			}
			else {
				egoT++
			}
		}
		displayTraits()
	}
	
	// Interact with neighboring turtles
	def interact() {
		def myCost = 0
		ask (turtlesOn(neighbors())) {
			interactT++
			// Help neighboring turtles based on this turtle's behavior
			if ((color == {color}.of(myself()) && {inGroupCoopQ}.of(myself())) ||
				(color != {color}.of(myself()) && {outGroupCoopQ}.of(myself()))) {
				PTR += benefit
				myCost += cost
				coopT++
			}
		}
		PTR -= myCost
	}
	
	// Reproduce and spawn new turtles on open neighboring patches
	def reproduce() {
		if (randomFloat(1) < PTR) {
			def dest = oneOf(neighbors().with({!anyQ(turtlesHere())}))
			// If there is an open neighboring patch and this turtle is set to reproduce, place the new turtle on the open patch
			if (dest != null) {
				hatch(1) {
					moveTo(dest)
					mutate(myself())
					displayTraits()
				}
			}
		}
	}
	
	// Inherit traits from parent turtle t and mutate according to mutationRate
	def mutate(UserTurtle t) {
		color = {color}.of(t)
		if (randomFloat(1) < mutationRate) {
			while (color == {color}.of(t)) {
				color = oneOf(tagColors)
			}
		}
		if (randomFloat(1) < mutationRate) {
			inGroupCoopQ = !{inGroupCoopQ}.of(t)
		}
		else {
			inGroupCoopQ = {inGroupCoopQ}.of(t)
		}
		if (randomFloat(1) < mutationRate) {
			outGroupCoopQ = !{outGroupCoopQ}.of(t)
		}
		else {
			outGroupCoopQ = {outGroupCoopQ}.of(t)
		}
		// Update stats
		if (inGroupCoopQ) {
			if (outGroupCoopQ) {
				altT++
			}
			else {
				ethT++
			}
		}
		else {
			if (outGroupCoopQ) {
				xenT++
			}
			else {
				egoT++
			}
		}
	}
	
	// Modify how this turtle will appear on the display
	//     Altruist:  circle on gray background
	//     Ethnocentrist:  circle on black background
	//     Xenocentrist:  square on gray background
	//     Egoist:  square on black background
	def displayTraits() {
		size = 0.7
		// If this turtle cooperates with other turtles of the same color, make this turtle's shape a circle
		if (inGroupCoopQ) {
			shape = "circle"
		}
		// Else, make this turtle's shape a square
		else {
			shape = "square"
		}
		// If this turtle does not cooperate with turtles of a different color, shade this turtle's patch black
		if (!outGroupCoopQ) {
			setPcolor(black())
		}
	}

}