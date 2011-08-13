package mousetraps.factories;

import javax.swing.JPanel;
import repast.simphony.relogo.factories.AbstractReLogoPanelCreator;
import mousetraps.relogo.UserGlobalsAndPanelFactory;

public class ReLogoPanelCreator extends AbstractReLogoPanelCreator {

	public void addComponents(JPanel parent) {
		UserGlobalsAndPanelFactory ugpf = new UserGlobalsAndPanelFactory();
		ugpf.initialize(parent);
		ugpf.addGlobalsAndPanelComponents();
	}

}
