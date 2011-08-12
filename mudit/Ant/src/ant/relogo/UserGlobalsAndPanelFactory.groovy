package ant.relogo

import repast.simphony.relogo.factories.AbstractReLogoGlobalsAndPanelFactory

public class UserGlobalsAndPanelFactory extends AbstractReLogoGlobalsAndPanelFactory{
	public void addGlobalsAndPanelComponents(){
	
		addButtonWL("setup", "Setup")
		addButtonWL("go","Go Once")
		addToggleButtonWL("go", "Go")
		addSliderWL("numAnts", "Population", 50,1,400,100)
		addSliderWL("numCubes", "Food in each Pile", 10,1,200,50)

	}
}