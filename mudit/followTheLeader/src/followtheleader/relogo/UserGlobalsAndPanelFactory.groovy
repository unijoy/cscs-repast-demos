package followtheleader.relogo

import repast.simphony.relogo.factories.AbstractReLogoGlobalsAndPanelFactory

public class UserGlobalsAndPanelFactory extends AbstractReLogoGlobalsAndPanelFactory{
	public void addGlobalsAndPanelComponents(){
	
		addButtonWL("setup", "Setup")
		addButtonWL("go","Go Once")
		addToggleButtonWL("go", "Go")
		addSliderWL("numAnts", "Population", 50,1,400,100)
		addSliderWL("lapse", "Time Delay", 1,1,5,2)
		addSliderWL("dev", "Deviation", 1,1,140,90)
		addSwitchWL("select","Gradient On")
		addSliderWL("grad", "Gradient at Interval", 1,1,30,15)
		}
}