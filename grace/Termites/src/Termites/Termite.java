/**
 * 
 */
package Termites;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import repast.simphony.context.Context;
import repast.simphony.engine.schedule.ScheduledMethod;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.grid.Grid;
import repast.simphony.space.grid.GridDimensions;
import repast.simphony.space.grid.GridPoint;
import repast.simphony.util.ContextUtils;


/**
 * Termite
 * @author grace
 *
 */
public class Termite {

	public enum TermiteState {NO_CHIP, HAS_CHIP};
	private TermiteState state;
	private TermiteState oldState;
	private ContinuousSpace <Object> space;
	private Grid <Object> grid;
	private boolean DEBUG = false;
	
	public Termite(ContinuousSpace<Object> space, 
			Grid<Object> grid) {
		this.space = space;
		this.grid = grid;
		this.state = TermiteState.NO_CHIP;
		this.oldState = TermiteState.NO_CHIP;
	}
	
	/**
	 * Provides a list of adjacent empty sites in the cell's Moore neighborhood.  
	 * The list of sites is shuffled.
	 * 
	 * @return the list of adjacent sites that are either empty or has a chip
	 */
	/*
	private List<GridPoint> findEmptyAndChipSites(){
		List<GridPoint> emptySites = new ArrayList<GridPoint>();
		Context context = ContextUtils.getContext(this);
		//Grid grid = (Grid) context.getProjection("Grid");
		
		GridPoint pt = this.grid.getLocation(this);
		int xc = pt.getX();
		int yc = pt.getY();
		
		GridDimensions dims = grid.getDimensions();
		int height = dims.getHeight();
		int width = dims.getWidth();
		
		// Find empty Moore neighbors
		for (int x=xc-1; x<=xc+1; x++) {
			for (int y=yc-1; y<=yc+1; y++) {
				if (x==xc && y==yc)
					continue;
				if (x>=0 && x<width && y>=0 && y<height) {
					if (!grid.getObjectsAt(x,y).iterator().hasNext() ||
						(grid.getObjectAt(x,y) instanceof Chip))
						emptySites.add(new GridPoint(x,y));
				}
			}
		}
		
		Collections.shuffle(emptySites);
		
		return emptySites;
	}
	*/
	/*
	private List<GridPoint> findEmptySites(){
		List<GridPoint> emptySites = new ArrayList<GridPoint>();
		Context context = ContextUtils.getContext(this);
		//Grid grid = (Grid) context.getProjection("Grid");
		
		GridPoint pt = this.grid.getLocation(this);
		int xc = pt.getX();
		int yc = pt.getY();
		
		GridDimensions dims = grid.getDimensions();
		int height = dims.getHeight();
		int width = dims.getWidth();
		
		// Find empty Moore neighbors
		for (int x=xc-1; x<=xc+1; x++) {
			for (int y=yc-1; y<=yc+1; y++) {
				if (x==xc && y==yc)
					continue;
				if (x>=0 && x<width && y>=0 && y<height) {
					if (!grid.getObjectsAt(x,y).iterator().hasNext())
						emptySites.add(new GridPoint(x,y));
				}
			}
		}
		
		Collections.shuffle(emptySites);
		
		return emptySites;
	}
	*/
	
	// look at north, south, east, and west empty spots only
	private List<GridPoint> findEmptySites(int xc, int yc) {
		GridDimensions dims = grid.getDimensions();
		int height = dims.getHeight();
		int width = dims.getWidth();
		
		List<GridPoint> emptySites = new ArrayList<GridPoint>();
		if (yc+1<height && !grid.getObjectsAt(xc, yc+1).iterator().hasNext()) // north
			emptySites.add(new GridPoint(xc,yc+1));
		if (xc+1<width && !grid.getObjectsAt(xc+1, yc).iterator().hasNext()) // east
			emptySites.add(new GridPoint(xc+1,yc));
		if (yc-1>=0 && !grid.getObjectsAt(xc, yc-1).iterator().hasNext()) // south
			emptySites.add(new GridPoint(xc,yc-1));
		if (xc-1>=0 && !grid.getObjectsAt(xc-1, yc).iterator().hasNext()) // west
			emptySites.add(new GridPoint(xc-1,yc));
		Collections.shuffle(emptySites);
		return emptySites;
	}
	
	private List<GridPoint> findEmptySites2(boolean includeChip, int xc, int yc){
		List<GridPoint> emptySites = new ArrayList<GridPoint>();
		//Context context = ContextUtils.getContext(this);
		//Grid grid = (Grid) context.getProjection("Grid");
		
		//GridPoint pt = this.grid.getLocation(this);
		//int xc = pt.getX();
		//int yc = pt.getY();
		
		GridDimensions dims = grid.getDimensions();
		int height = dims.getHeight();
		int width = dims.getWidth();
		
		// Find empty Moore neighbors
		for (int x=xc-1; x<=xc+1; x++) {
			for (int y=yc-1; y<=yc+1; y++) {
				if (x==xc && y==yc)
					continue;
				if (x>=0 && x<width && y>=0 && y<height) {
					if (!grid.getObjectsAt(x,y).iterator().hasNext())
						emptySites.add(new GridPoint(x,y));
					else if (includeChip && (grid.getObjectAt(x,y) instanceof Chip))
						emptySites.add(new GridPoint(x,y));
				}
			}
		}
		
		Collections.shuffle(emptySites);
		
		return emptySites;
	}
	
	/**
	 * Move the cell to a random empty adjacent site.
	 */
	/*
	private void move(){
		Context<Object> context = ContextUtils.getContext(this);
		Grid grid = (Grid) context.getProjection("Grid");
		List<GridPoint> emptySites = findEmptySites();
		grid.moveTo(this, emptySites.get(0).getX(), emptySites.get(0).getY());
	}
	*/
	
	private void dropChip(int xobj, int yobj) {
		List<GridPoint> emptySitesObj = findEmptySites(xobj, yobj); //findEmptySites2(false, xobj, yobj);
		Chip c = new Chip(this.space, this.grid);
		//Object obj = this.grid.getObjectAt(xobj, yobj);
		Context<Object> context = ContextUtils.getContext(this);
		context.add(c);
		//System.out.println("Drop chip");
		boolean moved = false;
		for (int i=0; i<emptySitesObj.size(); i++) {
			int xdrop = emptySitesObj.get(i).getX();
			int ydrop = emptySitesObj.get(i).getY();
			if (this.grid.moveTo(c, xdrop, ydrop)==true) {
				if (DEBUG)
					System.out.println("Termite's chip dropped at "+xdrop+" "+ydrop);
				this.state = TermiteState.NO_CHIP;
				moved = true;
			}
		}
		if (!moved) {
			if (DEBUG)
				System.out.println("Termite did not drop chip");
			context.remove(c);
		}
	}
	
	@ScheduledMethod(start = 1, interval = 1)
	public void step() {

		GridPoint thisPt = grid.getLocation(this);
		int xc = thisPt.getX();
		int yc = thisPt.getY();
		
		// pick a random empty location
		List<GridPoint> emptyAndChipSites = findEmptySites2(true, xc, yc);
		List<GridPoint> emptySites = findEmptySites2(false, xc, yc);

		int xobj = emptyAndChipSites.get(0).getX();
		int yobj = emptyAndChipSites.get(0).getY();		
		Object obj = this.grid.getObjectAt(xobj, yobj);
		//System.out.println("move termite to "+x+" "+y);
		Context<Object> context = ContextUtils.getContext(obj);
		
		if (obj instanceof Chip) { // found chip
			if (DEBUG)
				System.out.println("Termite at "+xc+" "+yc+" found a chip at "+xobj+" "+yobj);
			if (this.state==TermiteState.HAS_CHIP) {
				if (DEBUG)
					System.out.println("Termite carries a chip and needs to drop it");
				dropChip(xobj, yobj);
			} else { // not carrying any chip; pick up chip and move there
				if (DEBUG)
					System.out.println("Termite not carrying chip");
				// remove chip from context and set termite state
				if (context.remove(obj)==true) { // removed successfully
					this.state = TermiteState.HAS_CHIP; // termite now has chip
					// move termite to chip location
					if (grid.moveTo(this, xobj, yobj)==true) {
						if (DEBUG)
							System.out.println("Termite picks up chip and move to "+xobj+" "+yobj);
					}
					else
						if (DEBUG)
							System.out.println("Termite picks up chip but did not move to "+xobj+" "+yobj);
				}
			}
		} else { // no chip found; just move there
			if (DEBUG)
				System.out.println("Termite at "+thisPt.getX()+" "+thisPt.getY()+" found no chip");
			// simply move termite to new location, if possible
			if (grid.moveTo(this, xobj, yobj)==true) { // move this termite
				if (DEBUG)
					System.out.println("Termite moved to "+xobj+" "+yobj);
			}
		}
	}
	
	public String toString() {
		GridPoint pt = grid.getLocation(this);
		return "Termite at location "+pt.getX()+" "+pt.getY();
	}
}
