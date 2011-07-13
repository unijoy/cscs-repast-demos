package moth_basic.relogo

import repast.simphony.relogo.factories.AbstractReLogoGlobalsAndPanelFactory

/**
* 
* Creates the User Panel for the model.
*
* Push Buttons (that rebounds) for running the setup() function and go() function in
* UserObserver.groovy. The model also has a toggle Button to run go() in a loop.
* User can choose the population of moths through a slider button. The speed at which
* moths move is also controlled through a slider in the user interface (both for normal
* and excited condition). The user can also select the intensity and size of the source.
*
* @author Mudit Raj Gupta
*
*/

public class UserGlobalsAndPanelFactory extends AbstractReLogoGlobalsAndPanelFactory{
	public void addGlobalsAndPanelComponents(){
	
		
		        //Button for Setup()
				addButtonWL("setup","Setup") 	
		  		
				//Toggle Button for calling Go in a loop
				addToggleButtonWL("go","Go")	
				
				//Slider for population  
			    addSliderWL("numMoths", "Number of Moths", 1, 10, 500, 250)
				
				//Slider for setting Excited Speed
				addSliderWL("exSpeed", "Excited Speed", 1, 1, 6, 3)
				
				//Slider for Normal Speed
				addSliderWL("nrmlSpeed", "Normal Speed", 1, 1, 6, 3)
				
				//Slider for source size
				addSliderWL("source", "Source", 1, 1, 5, 3)
				
				//Slider for intensity
				addSliderWL("intensity", "Intensity", 1, 1, 5,3)
		       }
}