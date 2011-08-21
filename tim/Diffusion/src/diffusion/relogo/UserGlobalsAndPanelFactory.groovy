package diffusion.relogo

import repast.simphony.relogo.factories.AbstractReLogoGlobalsAndPanelFactory

/**
 * 
 * @author Tim Sweda
 *
 */

public class UserGlobalsAndPanelFactory extends AbstractReLogoGlobalsAndPanelFactory{
	public void addGlobalsAndPanelComponents(){
	
		/**
		 * Uncomment by removing "//" below to include ReLogo Tick Display
		 */
		//addReLogoTickCountDisplay()
		
		/**
		 * Place custom panels and globals below, for example:
		 * 
		  		addButtonWL("setup","Setup") 	// Button with label ( method name, button label )
		  		addButton("action1")			// Button without label ( method name )
		  		addToggleButtonWL("go","Go")	// Toggle Button with label ( method name, button label )
		  		addToggleButton("action2")		// Toggle Button without label  ( method name )
		  		addGlobal("globalVariable1")	// Globally accessible variable ( variable name)
		  		// Slider with label ( variable name, slider label, minimum value, increment, maximum value, initial value )
		        addSliderWL("sliderVariable", "Slider Variable", 0, 1, 10, 5)
		        // Slider without label ( variable name, minimum value, increment, maximum value, initial value )
		        addSlider("sliderVariable2", 0.2, 0.1, 0.8, 0.5)
		        // Chooser with label  ( variable name, chooser label, list of choices , zero-based index of initial value )
		        addChooserWL("chooserVariable", "Chooser Variable", ["yes","no","maybe"], 2)
		        // Chooser without label  ( variable name, list of choices , zero-based index of initial value )
		        addChooser("chooserVariable2", [1, 66, "seven"], 0)
		 */
		
		addGlobal("inkValue") // Color of patches with positive concentration (double)
		
		// Create user interface
		addChooserWL("inkColor", "Color", ["gray", "red", "orange", "brown", "yellow", "green", "lime", "turquoise",
			"cyan", "sky", "blue", "violet", "magenta", "pink"], 0) // Color of patches with positive concentration (string)
		addChooserWL("pattern", "Pattern", ["Random100", "Random300", "Random500", "OppositeEdges", "AdjacentEdges",
			"Spot", "Grid"], 0) // Initial pattern of patches to be colored
		addButtonWL("setup", "Setup")
		addToggleButtonWL("go", "Go")
		addButtonWL("darken", "Darken")
		addSliderWL("diffusionRate", "Diffusion Rate", 0.01, 0.01, 1, 0.05) // Rate of diffusion
		addMonitor("maxConcentration", 1)
		addMonitor("minConcentration", 1)

	}
}