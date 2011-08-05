package ethnocentrism.relogo

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
		
		addGlobal("tagColors") // Array of possible turtle colors
		
		// Arrays for stat recording
		addGlobal("altruists")
		addGlobal("ethnocentrists")
		addGlobal("xenocentrists")
		addGlobal("egoists")
		addGlobal("cooperation")
		
		// Stats during each tick
		addGlobal("altT")
		addGlobal("ethT")
		addGlobal("xenT")
		addGlobal("egoT")
		addGlobal("coopT")
		addGlobal("interactT")
		
		// Create user interface
		addButton("setup")
		addToggleButton("go")
		addButtonWL("go", "goOnce") // Execute 'go' method just once
		addButton("reportStats") // Report prevalence of each behavior type and percent of interactions that are cooperative
		addSlider("cost", 0, 0.01, 1, 0.01) // Cost of giving help
		addSlider("benefit", 0, 0.01, 1, 0.03) // Benefit of receiving help
		addSlider("basePTR", 0, 0.01, 1, 0.12) // Turtle's potential to reproduce (PTR) at the beginning of a tick
		addSlider("mutationRate", 0, 0.005, 1, 0.005) // Probability that an offspring's trait differs from that of the parent
		addSlider("deathRate", 0, 0.01, 1, 0.10) // Probability that a turtle dies at the end of a tick
		addSlider("immigrationRate", 1, 1, 20, 1) // Number of new turtles that arrive at the beginning of a tick
		addSlider("inGroupCoopProb", 0, 0.01, 1, 0.5) // Probability that a new turtle will help other turtles of a similar color
		addSlider("outGroupCoopProb", 0, 0.01, 1, 0.5) // Probability that a new turtle will help other turtles of a different color

	}
}