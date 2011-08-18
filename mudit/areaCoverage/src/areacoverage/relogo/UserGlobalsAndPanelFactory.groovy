package areacoverage.relogo

import repast.simphony.relogo.factories.AbstractReLogoGlobalsAndPanelFactory

/**
* Creates the User Panel for the model.
* 
* Push Buttons (that rebound) for running the setup() function and go() function in
* UserObserver.groovy. * Toggle Button to run go() in a loop. User can choose the 
* initial geometry. User can select "Number of Motes", "Step Size" and "Inital Side 
* Length/Radius"  
* 
* @author Mudit Raj Gupta
*
*/
public class UserGlobalsAndPanelFactory extends AbstractReLogoGlobalsAndPanelFactory
{
	//Adds componets to the user panel
	public void addGlobalsAndPanelComponents()
	{
		def shps = ["Square","Circle","Hexagon","Line"]
		
		//Calls the setup() function in UserObserver.groovy
		addButtonWL("setup", "Setup")
		
		//Call the go() function in UserObserver.groovy Once
		addButtonWL("go","Go Once")
		
		//Calls the go() function in UserObserver.groovy till its not Toggled again
		addToggleButtonWL("go", "Go")
		
		//Gets the value of Step Size in stepSize
		addSliderWL("stepSize", "Step Size", 1,1,4,2)
		
		//Gets the user's selection of initial geometry in shape
		addChooserWL("shape","Shape",shps)
		
		//Gets the value of Number of Motes in numMotes
		addSliderWL("numMotes", "Number of Motes", 1,1,1000,500)
		
		//Gets the value of Parameter of the initial geometry in initVal
		addSliderWL("initVal", "Initial Side Length/Radius", 1,1,20,10)
	}
}