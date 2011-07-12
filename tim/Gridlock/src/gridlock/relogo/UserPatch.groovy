package gridlock.relogo

import static repast.simphony.relogo.Utility.*;
import static repast.simphony.relogo.UtilityG.*;
import repast.simphony.relogo.BasePatch;
import repast.simphony.relogo.Stop;
import repast.simphony.relogo.Utility;
import repast.simphony.relogo.UtilityG;
import repast.simphony.relogo.ast.Diffusible;

/**
 * @author Tim Sweda
 *
 */

class UserPatch extends BasePatch{
	
	def greenNSQ, row, col
	
	// Set signal colors at intersection
	def setSignal() {
		def NSColor = white()
		def EWColor = white()
		if (powerQ)
			if (greenNSQ) {
				NSColor = green()
				EWColor = red()
			}
			else {
				NSColor = red()
				EWColor = green()
			}
		ask (patchAt(0, -1)) {
			pcolor = NSColor
		}
		ask (patchAt(-1, 0)) {
			pcolor = EWColor
		}
	}

}