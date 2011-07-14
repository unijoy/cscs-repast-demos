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
import repast.simphony.space.Dimensions;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.grid.Grid;
import repast.simphony.space.grid.GridDimensions;
import repast.simphony.space.grid.GridPoint;
import repast.simphony.util.ContextUtils;
import repast.simphony.util.SimUtilities;
import repast.simphony.valueLayer.GridValueLayer;

/**
 * Tree
 * @author grace
 *
 */
public class Tree {

	// A tree has 3 states: FRESH (newly grown), BURN (burning), ASH (no more tree)
	public enum TreeState {FRESH, BURN, ASH}
	private TreeState state; // current state of tree
	private TreeState oldState; // previous state of tree
	// Creating a space and grid to fill trees
	private ContinuousSpace <Tree> space;
	private Grid<Tree> grid;
	// Wind strength: strong, medium, weak, none
	// e.g., strong = trees in direction of wind have 90% chance of 
	// being burned as well
	private double WIND_STRONG = 0.9;
	private double WIND_MEDIUM = 0.5;
	private double WIND_WEAK = 0.1;
	private double WIND_NONE = 0.0;
	// Probability of this tree being burned
	private double burnProb;
	// Probability of this tree being burned from wind source
	private double burnFromWindProb;
	// Infected if burnProb and/or burnFromWindProb are set
	private boolean infected;
	// Neighborhood visibility
	private int neighborSize;
	// Parameter passed in from GUI: set burnProb to same value for all neighbors
	private double probAll;
	// Parameter passed in from GUI: set percentage of surrounding trees to burn
	private double percBurn;
	// Parameter passed in from GUI: set burnProb depending on individual 
	// directed probability
	private HashMap<String, Float> directedProb;
	// Wind strength: "strong", "medium", "weak", "none"
	private String windStrength;
	// Wind direction: "north", "northeast", "northwest", "east", "west", "south", "southeast", "southwest"
	private String windDir;


	//
	// Initialization
	// Parameters passed in from GUI
	//
	public Tree(Grid<Tree> grid, ContinuousSpace<Tree> space, int neighborSize,
			float probAll, double percBurn, HashMap<String, Float> directedProb,
			String windStrength, String windDir) {
		this.state = TreeState.FRESH;
		this.oldState = TreeState.FRESH;
		this.grid = grid;
		this.space = space;
		this.neighborSize = neighborSize;
		this.probAll = probAll;
		this.percBurn = percBurn;
		this.directedProb = directedProb;
		this.windStrength = windStrength;
		this.windDir = windDir;
		this.burnProb = 0.0;
		this.burnFromWindProb = 0.0;
	}
	
	//
	// Maintain the history of the state for one time interval
	//
	@ScheduledMethod(start=1, interval=1, priority=ScheduleParameters.FIRST_PRIORITY)
	public void setOldState(){
		this.oldState = this.state;
	}
	
	//
	// Get wind probability from indicated string
	//
	double getWindProb(String str) {
		double prob;
		if ("strong".equals(str)) {
			//System.out.println("set strong wind");
			prob = WIND_STRONG;
		} else if ("medium".equals(str)) {
			prob = WIND_MEDIUM;
		} else if ("weak".equals(str)) {
			prob = WIND_WEAK;
		} else if ("none".equals(str)) {
			prob = WIND_NONE;
		} else {
			System.out.println("ERR: unknown wind option: "+str);
			prob = WIND_NONE;
		}
		return prob;
	}
	
	//
	// Burn trees within the neighborhood with same probability
	//
	private void burnNeighborTrees(List<GridCell<Tree>> gridCells) {
		// need to shuffle so trees will not always burn in the same direction
		SimUtilities.shuffle(gridCells, RandomHelper.getUniform());
		// count trees to burn
		int treesToInfect = 0;
		treesToInfect = (int) (this.percBurn*gridCells.size());	
		//System.out.println("trees to infect "+ treesToInfect);
		int count = 0;
		for (GridCell<Tree> cell : gridCells) {
			//System.out.println("grid location "+pt2.getX()+" "+pt2.getY());
			if (cell.size()>0 && count<treesToInfect) {
				GridPoint pt1 = cell.getPoint();
				grid.getObjectAt(pt1.getX(), pt1.getY()).infected = true;
				//System.out.println("set infected");
			}
			count++;
		}
	}
	
	//
	// Burn trees within the neighborhood with directed probabilities
	//
	private void burnNeighborTreesDirected(List<GridCell<Tree>> gridCells) {
		GridPoint pt = grid.getLocation(this);
		int xc = pt.getX(); // current location as center
		int yc = pt.getY();
		// count trees to burn
		int treesToInfect = (int) (this.percBurn*gridCells.size());
		int count = 0;
		for (GridCell<Tree> cell : gridCells) {
			if (cell.size()>0 && count<treesToInfect) {
				GridPoint pt1 = cell.getPoint();
				int x = pt1.getX(); // get neighbor tree's location
				int y = pt1.getY();
				Tree t = grid.getObjectAt(x,y);
				
				// Set appropriate burn probability for each neighboring tree
				if (x==xc && y>yc) {
					// if neighbor tree's location is directly north,
					// then set its burn probability to passed in parameter
					// "probN" from GUI
					t.burnProb = directedProb.get("probN");
				} else if (x==xc && y<yc) {
					// similar to previous
					t.burnProb = directedProb.get("probS");
				} else if (x>xc && y==yc) {
					t.burnProb = directedProb.get("probE");
				} else if (x<xc && y==yc) {
					t.burnProb = directedProb.get("probW");
				} else if (x>xc && y>yc) {
					// for north east, cover the entire north-east quadrant
					// optional change: just make it diagonal?
					t.burnProb = directedProb.get("probNE");
				} else if (x<xc && y<yc) {
					// similar to previous
					t.burnProb = directedProb.get("probNW");
				} else if (x>xc && y<yc) {
					t.burnProb = directedProb.get("probSE");
				} else { // if (x<50 && y<50) {
					t.burnProb = directedProb.get("probSW");
				}
				// consider the tree infected if probability is set
				if (t.burnProb>0)
					t.infected = true;
				//System.out.println("set infected "+t.burnProb+" at "+x+" "+y);
			}
			count++;
		}
	}
	


	//
	// set tree's brunFromWindProb and infected variables
	//
	private void setBurnFromWindProb(int x, int y, double windProb) {
		// get tree at desired location x, y
		Tree t = grid.getObjectAt(x,y);
		// if there's a tree, then set burning probability
		if (t != null) {
			t.burnFromWindProb = windProb;
			if (windProb>0)
				t.infected = true;
		}
	}
	
	//
	// set wind burning probability to neighboring trees
	//
	private void setWindProb() {
		// get corresponding probability based on wind strength string
		double windProb = getWindProb(windStrength);
		// get this tree's location
		int x = grid.getLocation(this).getX();
		int y = grid.getLocation(this).getY();
		// get this grid's dimensions (width, height)
		GridDimensions dims = grid.getDimensions();
		int height = dims.getHeight();
		int width = dims.getWidth();
		// temp variables
		int i, j;
	
		// If wind direction is north, then set burn probability on
		// all trees located north of tree. Similar for east, west,
		// and south directions.
		// If wind direction is diagonal (northeast, northwest, southeast, 
		// southwest), then set probability to trees along the diagonal
		// (not the entire quadrant like the directed fire probability)
		if ("north".equals(this.windDir)) {
			i = x + 1; 
			j = y + 1;
			while (j < height) {
				setBurnFromWindProb(x, j, windProb);
				j++;
			}
		} else if ("northeast".equals(this.windDir)) {
			i = x + 1;
			j = y + 1;
			while ((i < width) && (j < height)) {
				setBurnFromWindProb(i,j, windProb);
				i++; j++;
			}
		} else if ("northwest".equals(this.windDir)) {
			i = x - 1;
			j = y + 1;
			while ((i>0) && (j<height)) {
				setBurnFromWindProb(i,j, windProb);
				i--; j++;					
			}
		} else if ("east".equals(this.windDir)) {
			i = x + 1;
			while (i<width) {
				setBurnFromWindProb(i,y, windProb);
				i++;
			}
		} else if ("west".equals(this.windDir)) {
			i = x - 1;
			while (i>width) {
				setBurnFromWindProb(i,y, windProb);
				i--;
			}
		} else if ("south".equals(this.windDir)) {
			j = y - 1;
			while (j>0) {
				setBurnFromWindProb(x,j, windProb);
				j--;
			}
		} else if ("southeast".equals(this.windDir)) {
			i = x + 1;
			j = y - 1;
			while (i<width && j>0) {
				setBurnFromWindProb(i,j,windProb);
				i++; j--;
			}
		} else if ("southwest".equals(this.windDir)) {
			i = x - 1;
			j = y - 1;
			while (i>0 && j>0) {
				setBurnFromWindProb(i,j, windProb);
				i--; j--;
			}
		} else {
			System.out.println("ERR: unknown wind direction: "+this.windDir);
		}
	}
	
	//
	// set current state
	// save previous state
	//
	public void setState(TreeState state) {
		setOldState();
		this.state = state;
		
		// get current context, grid, and value layer
		Context<Tree> context = ContextUtils.getContext(this);
		Grid<Tree> grid = (Grid)context.getProjection("Grid");
	    GridValueLayer vl = (GridValueLayer)context.getValueLayer("State");
	    
	    // if setting tree to FRESH state, then set the color to bright green
	    // note that the green color is set at GUI, and the intensity ranges
	    // from 0 to 10
	    if (state == TreeState.FRESH) {
	    	//System.out.println("set fresh tree");
	    	vl.set(10, grid.getLocation(this).getX(),grid.getLocation(this).getY());
	    } else if (state == TreeState.BURN) {
	    	// for BURN tree, then generate a random number and see if it's
	    	// within the probability range
			double num;
			// regular burning tree
			num = RandomHelper.nextDoubleFromTo(0, 1);
			if (this.burnProb>0 && num <= this.burnProb){
				System.out.println("set burn tree "+grid.getLocation(this).getX()+" "+grid.getLocation(this).getY());
				vl.set(6, grid.getLocation(this).getX(), grid.getLocation(this).getY());
			}
			// burning tree from wind
			num = RandomHelper.nextDoubleFromTo(0, 1);
			if (this.burnFromWindProb>0 && num <= this.burnFromWindProb){
				System.out.println("set burn tree from wind "+grid.getLocation(this).getX()+" "+grid.getLocation(this).getY());
				vl.set(6, grid.getLocation(this).getX(), grid.getLocation(this).getY());
			}		
			
			// set burn probability to neighbors
			// get grid point location of current tree
			GridPoint pt = grid.getLocation(this);
			// get surrounding trees within the n by n square neighborhood
			// "neighborSize" is a parameter passed in from GUI
			GridCellNgh<Tree> nghCreator = new GridCellNgh<Tree>(grid, pt,
					Tree.class, neighborSize, neighborSize);
			// create a corresponding list
			// parameter false means do not return the center tree (i.e., this one)
			List<GridCell <Tree>> gridCells = nghCreator.getNeighborhood(false);
				
			// Two ways to infect neighboring trees:
			// 1. same probability for all neighboring trees (probAll != 0), or
			// 2. different probability for each direction (probAll==0, thus we
			//    will use the table directedProb
			if (this.probAll != 0) {
				// 1. same probability for all neighboring trees
				burnNeighborTrees(gridCells);
			} else {
				// 2. different probability for each direction (probAll==0, thus we
				//    will use the table directedProb
				burnNeighborTreesDirected(gridCells);
			}	
			
			// set burn prob from wind to neighbors
			setWindProb();
			
		} else if (this.state == TreeState.ASH) {
			//System.out.println("set ash tree");
			vl.set(2, grid.getLocation(this).getX(), grid.getLocation(this).getY());
		}
	}

	//
	// at each step... go to next state if necessary
	//
	@ScheduledMethod(start = 1, interval = 1)
	public void step() {
		// get current context
		Context<Tree> context = ContextUtils.getContext(this);
		// if the tree was burning, then set to ASH
		if (this.oldState == TreeState.BURN) {
			// set current tree to ash
			setState(TreeState.ASH);
		// if the tree was fresh and infected, then set to BURN
		} else if (this.oldState == TreeState.FRESH && this.infected==true) {
			// this tree is getting burned
			setState(TreeState.BURN);
			//System.out.println("set burn");
		}
	}
	
}
