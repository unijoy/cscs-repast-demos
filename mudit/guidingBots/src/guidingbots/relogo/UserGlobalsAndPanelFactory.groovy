package guidingbots.relogo

import repast.simphony.relogo.factories.AbstractReLogoGlobalsAndPanelFactory

public class UserGlobalsAndPanelFactory extends AbstractReLogoGlobalsAndPanelFactory{
	public void addGlobalsAndPanelComponents(){
	
		
			//Calls the setup() function in UserObserver.groovy
			addButtonWL("setup", "Setup")
			
			//Call the go() function in UserObserver.groovy Once
			addButtonWL("go","Go Once")
			
			//Calls the go() function in UserObserver.groovy till its not Toggled again
			addToggleButtonWL("go", "Go")
			
			//Gets the value of Number of Motes in numMotes
			addSliderWL("gm", "Number of Guiding Motes", 1,1,100,50)
			
			//Gets the value of Number of Motes in numMotes
			addSliderWL("dm", "Number of Daughter Motes", 1,1,1000,500)
			
			//Gets the value of Step Size in stepSize
			addSliderWL("stepSize", "Step Size", 1,1,6,3)
			
			addSliderWL("er", "Error Tolerance", 1,1,15,5)
			
	}
}