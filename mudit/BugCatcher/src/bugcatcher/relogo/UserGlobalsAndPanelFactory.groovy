package bugcatcher.relogo

import repast.simphony.relogo.factories.AbstractReLogoGlobalsAndPanelFactory

/**
* Creates the User Panel for the model.
*
* Push Buttons (that rebounds) for running the setup() function and go() function in
* UserObserver.groovy. The model also has a toggle Button to run go() in a loop. 
* User can choose the population of bugs through a slider button. The speed at which 
* bugs move is also controlled through a slider in the user interface. The user can 
* also select the number if catching machines using a slider. The time between getting 
* caught and death is also adjustable through a slider 
*
* @author Mudit Raj Gupta
*
*/

public class UserGlobalsAndPanelFactory extends AbstractReLogoGlobalsAndPanelFactory{
	public void addGlobalsAndPanelComponents(){
	
				//Button to run the Setup function				
		  		addButtonWL("setup","Setup") 	
		  		
				//Button to run the go function in a loop  
		  		addToggleButtonWL("go","Go")	
				  
				//Population value slider		  	
				addSliderWL("numBugs", "Number of Bugs", 1,1,500,250)
				
				//Speed Slider
				addSliderWL("bugSpeed", "Bug Speed", 1,1,5,3)
				
				//Number of Machines Slider
				addSliderWL("numCatcher", "Number of Catchers", 1,1,8,4)
				
				//Slider for changing the time between 
				addSliderWL("tym", "Time", 1,1,25,10)
	}
}