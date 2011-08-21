package diffusion.relogo

import static repast.simphony.relogo.Utility.*;
import static repast.simphony.relogo.UtilityG.*;
import repast.simphony.relogo.BaseObserver;
import repast.simphony.relogo.Stop;
import repast.simphony.relogo.Utility;
import repast.simphony.relogo.UtilityG;

/**
 * Simple demonstration of diffuse methods for patches.
 * 
 * @author Tim Sweda
 *
 */

class UserObserver extends BaseObserver{

	def setup() {
		clearAll()
		// Initialize patches to have none of the diffusible variable (concentration)
		ask (patches()) {
			concentration = 0
			pcolor = white()
		}
		// Set color of patches with positive concentrations
		if (inkColor == "gray") {
			inkValue = gray() // = 5.0
		}
		else if (inkColor == "red") {
			inkValue = red() // = 15.0
		}
		else if (inkColor == "orange") {
			inkValue = orange() // = 25.0
		}
		else if (inkColor == "brown") {
			inkValue = brown() // = 35.0
		}
		else if (inkColor == "yellow") {
			inkValue = yellow() // = 45.0
		}
		else if (inkColor == "green") {
			inkValue = green() // = 55.0
		}
		else if (inkColor == "lime") {
			inkValue = lime() // = 65.0
		}
		else if (inkColor == "turquoise") {
			inkValue = turquoise() // = 75.0
		}
		else if (inkColor == "cyan") {
			inkValue = cyan() // = 85.0
		}
		else if (inkColor == "sky") {
			inkValue = sky() // = 95.0
		}
		else if (inkColor == "blue") {
			inkValue = blue() // = 105.0
		}
		else if (inkColor == "violet") {
			inkValue = violet() // = 115.0
		}
		else if (inkColor == "magenta") {
			inkValue = magenta() // = 125.0
		}
		else {
			inkValue = pink() // = 135.0
		}
		// Determine patches in initial pattern to be colored
		def coloredPatches
		if (pattern == "Random100") {
			coloredPatches = nOf(100, patches())
		}
		else if (pattern == "Random300") {
			coloredPatches = nOf(300, patches())
		}
		else if (pattern == "Random500") {
			coloredPatches = nOf(500, patches())
		}
		else if (pattern == "OppositeEdges") {
			coloredPatches = patches().with({abs(pxcor) > 12})
		}
		else if (pattern == "AdjacentEdges") {
			coloredPatches = patches().with({pxcor < -12 || pycor > 12})
		}
		else if (pattern == "Spot") {
			coloredPatches = patches().with({abs(pxcor) < 4 && abs(pycor) < 4})
		}
		else {
			coloredPatches = patches().with({mod(pxcor, 3) == 1 && mod(pycor, 3) == 1})
		}
		// Color patches in initial pattern
		ask (coloredPatches) {
			concentration = 1
			pcolor = inkValue+4.9-8.9*concentration
		}
	}
	
	def go() {
		// Diffuse concentration and update patch colors
		diffuse("concentration", diffusionRate)
		ask (patches()) {
			pcolor = inkValue+4.9-8.9*concentration
		}
		// Stop simulation once diffusion is complete (within epsilon = 0.001)
		if (maxConcentration()-minConcentration() < 0.001)
			stop()
	}
	
	// Darken display by increasing patch concentrations by 10%
	def darken() {
		if (maxConcentration() < 0.9)
			diffusibleMultiply("concentration", 1.1)
	}
	
	// Return highest concentration value among all patches
	def maxConcentration() {
		return max({concentration}.of(patches()))
	}
	
	// Return lowest concentration value among all patches
	def minConcentration() {
		return min({concentration}.of(patches()))
	}

}