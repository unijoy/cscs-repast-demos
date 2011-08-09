package gameoflife.relogo

import static repast.simphony.relogo.Utility.*;
import static repast.simphony.relogo.UtilityG.*;
import repast.simphony.relogo.BasePatch;
import repast.simphony.relogo.Stop;
import repast.simphony.relogo.Utility;
import repast.simphony.relogo.UtilityG;
import repast.simphony.relogo.ast.Diffusible;

/**
 * 
 * @author Tim Sweda
 *
 */

class UserPatch extends BasePatch{
	
	def life, liveNeighbors
	
	// Set patch to be alive
	def makeLiving() {
		life = 1
		pcolor = liveColor
	}
	
	// Set patch to be dead
	def makeDead() {
		life = 0
		pcolor = deadColor
	}

}