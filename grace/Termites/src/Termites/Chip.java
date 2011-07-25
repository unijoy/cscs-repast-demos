/**
 * 
 */
package Termites;

import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.grid.Grid;
import repast.simphony.space.grid.GridPoint;

/**
 * Chip
 * @author grace
 *
 */
public class Chip {
	public enum ChipState {PICKED_UP, DROPPED};
	private ChipState state;
	private ContinuousSpace <Object> space;
	private Grid <Object> grid;
	
	public Chip(ContinuousSpace<Object> space, 
			Grid<Object> grid) {
		this.space = space;
		this.grid = grid;
		this.state = ChipState.DROPPED;
	}
	
	public void setState(ChipState s) {
		this.state = s;
	}
	
	public void moveTo(int x, int y) {
		//GridPoint pt = grid.getLocation(this);
		//this.grid.moveTo(this, x-pt.getX(), y-pt.getY());
		this.grid.moveTo(this, x, y);
	}
	
	public String toString() {
		GridPoint pt = grid.getLocation(this);
		return "Chip at location "+pt.getX()+" "+pt.getY();
	}

}
