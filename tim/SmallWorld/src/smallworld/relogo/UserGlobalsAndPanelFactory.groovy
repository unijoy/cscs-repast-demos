package smallworld.relogo

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
		
		addGlobal("nextNode") // Next node to be rewired
		addGlobal("rewiredColor") // Color of links that are rewired
		addGlobal("avgPathLength") // Average path length between two randomly selected nodes
		
		// Create user interface
		addButtonWL("setup", "Setup")
		addButtonWL("rewireOne", "Rewire One Node")
		addButtonWL("rewireAll", "Rewire All Nodes")
		addSliderWL("numNodes", "# Nodes", 10, 1, 200, 35) // Number of nodes
		addSliderWL("initialNodeDegree", "Initial Node Degree", 4, 2, 20, 4) // Number of links to each node in initial network
		addSliderWL("rewireProb", "Rewiring Probability", 0, 0.0001, 1, 0.1) // Probability that a link is rewired
		addButtonWL("reportStats", "Statistics") // Report average path length and clustering coefficient

	}
}