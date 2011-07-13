package moth_advance.relogo


import repast.simphony.relogo.factories.AbstractReLogoGlobalsAndPanelFactory

public class UserGlobalsAndPanelFactory extends AbstractReLogoGlobalsAndPanelFactory{
	public void addGlobalsAndPanelComponents(){
	
		  		addButtonWL("setup","Setup") 	// Button with label ( method name, button label )
		  		
		  		addToggleButtonWL("go","Go")	// Toggle Button with label ( method name, button label )
		  	
				addSliderWL("numMoths", "Number of Moths", 1,1,500,250)
				addSliderWL("nrmlSpeed", "Normal Speed", 1,1,5,3)
				addSliderWL("exSpeed", "Excited Speed", 1,1,5,3)
				addSliderWL("intensity", "Intensity", 1,1,5,3)
				addSliderWL("numLights", "Number of Lights", 1,1,8,4)
				addSliderWL("source", "Source", 1,1,5,2)
	}
}