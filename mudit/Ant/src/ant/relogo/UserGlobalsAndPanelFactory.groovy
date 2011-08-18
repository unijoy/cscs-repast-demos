package ant.relogo

import repast.simphony.relogo.factories.AbstractReLogoGlobalsAndPanelFactory

/**
* Creates the User Panel for the model.
*
* Push Buttons (that rebound) for running the setup() function and go() function in
* UserObserver.groovy. Toggle Button to run go() in a loop. User can choose the
* value of various parameters through the available sliders
*
* @author Mudit Raj Gupta
*
*/

public class UserGlobalsAndPanelFactory extends AbstractReLogoGlobalsAndPanelFactory{
	public void addGlobalsAndPanelComponents(){

		//calls setup()
		addButtonWL("setup", "Setup")
		
		//calls go()
		addButtonWL("go","Go Once")
		
		//calls go() iteratively
		addToggleButtonWL("go", "Go")
		
		//Set Parameters
		addSliderWL("numAnts", "Population", 50,1,400,100)
		addSliderWL("numCubes", "Food in each Pile", 10,1,200,50)

	}
}