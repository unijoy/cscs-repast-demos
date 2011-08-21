package gridlock.relogo

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
		
		// Patch types
		addGlobal("roads")
		addGlobal("intersections")
		
		// Create user interface
		addButtonWL("setup", "Setup")
		addToggleButtonWL("go", "Go")
		
		// Must press setup button to see changes
		addSliderWL("numNSRoads", "# N/S Roads", 1, 1, 9, 5) // Number of roads in north-south direction
		addSliderWL("numEWRoads", "# E/W Roads", 1, 1, 9, 5) // Number of roads in east-west direction
		addSliderWL("numCars", "# Cars", 1, 1, 400, 150) // Number of cars
		addChooserWL("signalConfig", "Signal Configuration", ["AllSync", "NSAltEWSync", "NSAltEWAlt", "AllRandom"], 0)
		  // Configuration of traffic lights
		
		// May change during simulation
		addSliderWL("speedLimit", "Speed Limit", 0, 0.1, 1.0, 1) // Maximum speed of cars
		addSliderWL("accel", "Acceleration", 0, 0.01, 0.5, 0.1) // Rate at which cars accelerate or decelerate
		addSwitch("powerQ", true) // 
		addSliderWL("ticksPerCycle", "Ticks Per Cycle", 1, 1, 100, 20) // Number of ticks between light changes
		
	}
}