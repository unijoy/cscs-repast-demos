package stupidmodel.observer;

import java.awt.Color;

import repast.simphony.visualizationOGL2D.DefaultStyleOGL2D;
import stupidmodel.Bug;

public class BugStyleOGL2D extends DefaultStyleOGL2D {

	@Override
	public Color getColor(final Object agent) {
		if (agent instanceof Bug) {
			final Bug bug = (Bug) agent;
			final int strength = (int) Math.max(250 - bug.getSize(), 0);
			return new Color(0xCC, 0xCC, strength);
		}

		return super.getColor(agent);
	}

}
