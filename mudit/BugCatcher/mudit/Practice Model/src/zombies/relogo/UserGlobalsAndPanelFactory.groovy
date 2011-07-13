package zombies.relogo

import repast.simphony.relogo.factories.AbstractReLogoGlobalsAndPanelFactory

public class UserGlobalsAndPanelFactory extends AbstractReLogoGlobalsAndPanelFactory{
	public void addGlobalsAndPanelComponents(){
	
		addButtonWL("setup","Setup")
		addButtonWL("go","Go Once")
		addToggleButtonWL("go","Go")
		addSliderWL("numHumans", "Number of Humans", 1, 1, 100, 50)
		addSliderWL("numZombies", "Number of Zombies", 1, 1, 10, 5)
		addSliderWL("gestationPeriod", "Gestation", 5, 1, 30, 5)
		addMonitorWL("remainingHumans", "Remaining Humans", 5)
		addGlobal("zombieDiffusionRate",0.35)
		addGlobal("zombieEvaporationRate",0.08)
	}
}