package snake.relogo

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
		
		addGlobal("obstacleColor") // Color of walls and snake's body
		addGlobal("foodColor") // Color of food
		addGlobal("neutralColor") // Color of patches without obstacles or food
		addGlobal("turnSchedule") // Schedule of turns that snake will make
		addGlobal("headPatch") // Patch where the snake's head is
		addGlobal("body") // Patch set representing the snake's body
		addGlobal("score") // Player's score
		
		// Create user interface
		addButtonWL("setup", "Setup")
		addToggleButtonWL("go", "Go")
		addButtonWL("turnLeft", "Turn Left") // Turn snake left
		addButtonWL("turnRight", "Turn Right") // Turn snake right
		addSliderWL("difficulty", "Difficulty Level", 1, 1, 10, 4) // Difficulty level (higher level = faster gameplay)
		addMonitor("myScore", 1) // Display player's score

	}
}