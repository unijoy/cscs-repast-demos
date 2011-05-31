package boids_relogo_prealpha.relogo

import repast.simphony.relogo.factories.AbstractReLogoGlobalsAndPanelFactory

public class UserGlobalsAndPanelFactory extends AbstractReLogoGlobalsAndPanelFactory{
	public void addGlobalsAndPanelComponents(){
	
		addButtonWL("setup","Setup") 	// Button with label ( method name, button label )
		addButtonWL("go","Go Once") 	// Button with label ( method name, button label )
		addToggleButtonWL("go","Go")	// Toggle Button with label ( method name, button label )
		 		// Slider with label ( variable name, slider label, minimum value, increment, maximum value, initial value )
		addSliderWL("numBoids", "Number of Boids", 2, 2, 200, 50)

	}
}