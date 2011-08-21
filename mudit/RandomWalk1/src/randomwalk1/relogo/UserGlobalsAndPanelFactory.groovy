package randomwalk1.relogo


import repast.simphony.relogo.factories.AbstractReLogoGlobalsAndPanelFactory

public class UserGlobalsAndPanelFactory extends AbstractReLogoGlobalsAndPanelFactory{
	public void addGlobalsAndPanelComponents(){
	
	 
		  		addButtonWL("setup","Setup") 	// Button with label ( method name, button label )
	
		  		addToggleButtonWL("go","Go")	// Toggle Button with label ( method name, button label )
		  		
				  
				  addSliderWL("numWalker", "Number of Walkers", 1,1,40,10)
				  addSliderWL("stepSize", "Step Size", 1,1,4,1)
				  addSliderWL("numEnergy", "Maximum Energy of a Walker", 50,1,400,100)
				  
		}
}