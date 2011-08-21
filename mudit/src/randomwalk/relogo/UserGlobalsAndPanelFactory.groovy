package randomwalk.relogo

import repast.simphony.relogo.factories.AbstractReLogoGlobalsAndPanelFactory

public class UserGlobalsAndPanelFactory extends AbstractReLogoGlobalsAndPanelFactory{
	public void addGlobalsAndPanelComponents(){
	
	 
		  		addButtonWL("setup","Setup") 	// Button with label ( method name, button label )
	
		  		addToggleButtonWL("go","Go")	// Toggle Button with label ( method name, button label )
		  		
				  
				  addSliderWL("numWalker", "Number of Walkers", 50,1,400,100)
				  addSliderWL("stepSize", "Step Size", 1,1,4,1)
				  addSliderWL("numEnergy", "Energy of Each Walker", 50,1,400,100)
				  addSwitchWL("s","Area Coverage")
		}
}