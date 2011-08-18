package followtheleader.relogo

import repast.simphony.relogo.factories.AbstractReLogoGlobalsAndPanelFactory

/**
* Creates the User Panel for the model.
*
* Push Buttons (that rebound) for running the setup() function and go() function in
* UserObserver.groovy. Toggle Button to run go() in a loop. User can choose the
* value of various parameters through the available sliders. Switches are also given to 
* enable and disable various behaviors
*
* @author Mudit Raj Gupta
*
*/


public class UserGlobalsAndPanelFactory extends AbstractReLogoGlobalsAndPanelFactory{
	public void addGlobalsAndPanelComponents(){
	 
		//calls the setup() function
		addButtonWL("setup", "Setup")
		
		//calls the go() function
		addButtonWL("go","Go Once")
		
		//calls go() iteratively
		addToggleButtonWL("go", "Go")
		
		//set various parameters
		
		addSliderWL("numAnts", "Population", 50,1,400,100)
		addSliderWL("lapse", "Time Delay", 1,1,5,2)
		addSwitchWL("ran","Deviation Random")
		addSliderWL("dev", "Deviation", 1,1,140,90)
		addSwitchWL("select","Gradient On")
		addSliderWL("grad", "Gradient at Interval", 1,1,30,15)
		}
}