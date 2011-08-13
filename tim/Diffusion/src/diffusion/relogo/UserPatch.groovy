package diffusion.relogo

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
	
	// Declare concentration as diffusible patch variable
	@Diffusible
	def concentration

}