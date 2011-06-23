/**
 * 
 */
package Fire;

import java.util.HashMap;
import java.util.List;

import Fire.Tree; 

import repast.simphony.context.Context;
import repast.simphony.engine.schedule.ScheduleParameters;
import repast.simphony.engine.schedule.ScheduledMethod;
import repast.simphony.parameter.Parameter;
import repast.simphony.query.space.grid.GridCell;
import repast.simphony.query.space.grid.GridCellNgh;
import repast.simphony.random.RandomHelper;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.grid.Grid;
import repast.simphony.space.grid.GridPoint;
import repast.simphony.util.ContextUtils;
import repast.simphony.util.SimUtilities;
import repast.simphony.valueLayer.GridValueLayer;

/**
 * @author grace
 *
 */
public class Tree {

	private ContinuousSpace <Tree> space; // use of floating point
	private Grid<Tree> grid; // neighborhood proximity 
	public enum TreeState {FRESH, BURN, ASH}
	private TreeState state;
	private TreeState oldState;
	private boolean infected;
	private double stateVal;
	private int neighborSize;
	private float probAll;
	private HashMap<String, Float> directedProb;
	private HashMap<String, String> windProb;
	private float burnProb;
	
	public Tree(Grid<Tree> grid, ContinuousSpace<Tree> space, int neighborSize,
			float probAll, HashMap<String, Float> directedProb,
			HashMap<String, String> windProb) {
		state = TreeState.FRESH;
		oldState = TreeState.FRESH;
		this.grid = grid;
		this.space = space;
		this.neighborSize = neighborSize;
		this.probAll = probAll;
		this.directedProb = directedProb;
		this.windProb = windProb;
		//Context<Tree> context = ContextUtils.getContext(this);
		//GridValueLayer vl = (GridValueLayer)context.getValueLayer("State");
		//vl.set(5, grid.getLocation(this).getX(), grid.getLocation(this).getY());
	}
	
	
	// Maintain the history of the state for one time interval
	@ScheduledMethod(start=1, interval=1, priority=ScheduleParameters.FIRST_PRIORITY)
	public void setOldState(){
		oldState = state;
	}
	
	@Parameter(displayName = "Set Tree State", usageName = "stateVal")
	public double getStateVal() {
		//System.out.println("stateVal="+stateVal);
		return stateVal;
	}
	
	public void setStateVal(final int stateVal) {
		//System.out.println("stateVal="+stateVal);
		this.stateVal = stateVal;
	}
	
	
	double getWindProb(String str) {
		if (str == "strong") {
			return 0.9;
		} else if (str == "medium") {
			return 0.5;
		} else if (str == "low") {
			return 0.1;
		} else if (str == "none") {
			return 0.0;
		} else {
			System.out.println("ERR: unknown wind option");
			return 0.0;
		}
	}
	
	@ScheduledMethod(start = 1, interval = 1)
	public void step() {
		Context<Tree> context = ContextUtils.getContext(this);
		//Grid<Tree> grid = (Grid)context.getProjection("Grid");
		
		// Query the von Neuman neighborhood
		//VNQuery<Tree> query = new VNQuery(grid,this);
		//Iterator<Tree> iter = query.query().iterator();
		
		//System.out.println("infected? "+infected);
		
		if (this.oldState == TreeState.BURN) {
			// burn nearby 4trees
			//iter.next().infected = true;
			//iter.next().infected = true;
			//iter.next().infected = true;
			//iter.next().infected = true;
			GridPoint pt = grid.getLocation(this);
			int xc = pt.getX(); // current location as center
			int yc = pt.getY();
			GridCellNgh<Tree> nghCreator = new GridCellNgh<Tree>(grid, pt,
					Tree.class, neighborSize, neighborSize);
			// create corresponding list
			// false = do not return center
			List<GridCell <Tree>> gridCells = nghCreator.getNeighborhood(false);
			// without shuffle, trees will always burn in the same direction

			
			int treesToInfect = 0;
			// probability of infecting trees
			if (this.probAll != 0) {
				// when all cells are equal
				SimUtilities.shuffle(gridCells, RandomHelper.getUniform());
				treesToInfect = (int) (this.probAll*gridCells.size());	
				System.out.println("trees to infect "+ treesToInfect);
				int count = 0;
				for (GridCell<Tree> cell : gridCells) {
					//GridPoint pt2 = cell.getPoint();
					//System.out.println("grid location "+pt2.getX()+" "+pt2.getY());
					if (cell.size()>0 && count<treesToInfect) {
						GridPoint pt1 = cell.getPoint();
						grid.getObjectAt(pt1.getX(), pt1.getY()).infected = true;
						System.out.println("set infected");
					}
					count++;
				}	
			} else {
				// each direction has its own probability
				// set each tree's burnProb
				
				// 8 neighbors only
				// todo: debug
				/*
				grid.getObjectAt(xc-1, yc).burnProb = directedProb.get("probW");
				grid.getObjectAt(xc+1, yc).burnProb = directedProb.get("probE");
				grid.getObjectAt(xc, yc-1).burnProb = directedProb.get("probS");
				grid.getObjectAt(xc, yc+1).burnProb = directedProb.get("probN");
				grid.getObjectAt(xc-1, yc-1).burnProb = directedProb.get("probSW");
				grid.getObjectAt(xc+1, yc-1).burnProb = directedProb.get("probSE");
				grid.getObjectAt(xc-1, yc+1).burnProb = directedProb.get("probNW");
				grid.getObjectAt(xc+1, yc+1).burnProb = directedProb.get("probNE");
				*/
				
				// entire neighborhood
				for (GridCell<Tree> cell : gridCells) {
					if (cell.size()>0) {
						GridPoint pt1 = cell.getPoint();
						int x = pt1.getX();
						int y = pt1.getY();
						Tree t = grid.getObjectAt(x,y);
						if (x==xc && y>yc) { // North
							t.burnProb = directedProb.get("probN");
							t.burnProb += getWindProb(windProb.get("windprobN"));
						} else if (x==xc && y<yc) { // south
							t.burnProb = directedProb.get("probS");
							t.burnProb += getWindProb(windProb.get("windprobS"));
						} else if (x>xc && y==yc) { // east
							t.burnProb = directedProb.get("probE");
							t.burnProb += getWindProb(windProb.get("windprobE"));
						} else if (x<xc && y==yc) { // west
							t.burnProb = directedProb.get("probW");
							t.burnProb += getWindProb(windProb.get("windprobW"));
						} else if (x>xc && y>yc) { // NE
							t.burnProb = directedProb.get("probNE");
							t.burnProb += getWindProb(windProb.get("windprobNE"));
						} else if (x<xc && y<yc) { // NW
							t.burnProb = directedProb.get("probNW");
							t.burnProb += getWindProb(windProb.get("windprobNW"));
						} else if (x>xc && y<yc) { // SE
							t.burnProb = directedProb.get("probSE");
							t.burnProb += getWindProb(windProb.get("windprobSE"));
						} else { // if (x<50 && y<50) { // SW
							t.burnProb = directedProb.get("probSW");
							t.burnProb += getWindProb(windProb.get("windprobSW"));
						}
						if (t.burnProb>0)
							t.infected = true;
						System.out.println("set infected "+t.burnProb+" at "+x+" "+y);
					}
					
				}
				
			}
					
			// set current tree to ash
			setState(TreeState.ASH);
		} else if (this.oldState == TreeState.FRESH && this.infected==true) {
			// this tree is getting burned
			setState(TreeState.BURN);
			//System.out.println("set burn");
		}
	}

	public void setState(TreeState state) {
		setOldState();
		this.state = state;
		
		Context<Tree> context = ContextUtils.getContext(this);
		Grid<Tree> grid = (Grid)context.getProjection("Grid");
	    GridValueLayer vl = (GridValueLayer)context.getValueLayer("State");
	    
	    if (state == TreeState.FRESH) {
	    	//System.out.println("set fresh tree");
	    	vl.set(10, grid.getLocation(this).getX(),grid.getLocation(this).getY());
	    } else if (state == TreeState.BURN) {
			double num = RandomHelper.nextDoubleFromTo(0, 1);
			//System.out.println("random num: "+ num);
			if (this.probAll == 0 && this.burnProb>0 && num <= this.burnProb) {
				System.out.println("set burn tree "+grid.getLocation(this).getX()+grid.getLocation(this).getY());
				vl.set(6, grid.getLocation(this).getX(), grid.getLocation(this).getY());
			}
		} else if (this.state == TreeState.ASH) {
			System.out.println("set ash tree");
			vl.set(2, grid.getLocation(this).getX(), grid.getLocation(this).getY());
		}
	}

}
