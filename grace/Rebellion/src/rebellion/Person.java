/**
 * 
 */
package rebellion;

import java.util.List;

import repast.simphony.engine.schedule.ScheduledMethod;
import repast.simphony.query.space.grid.GridCell;
import repast.simphony.query.space.grid.GridCellNgh;
import repast.simphony.random.RandomHelper;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.grid.Grid;
import repast.simphony.space.grid.GridPoint;
import repast.simphony.util.SimUtilities;
/**
 * @author grace
 *
 */
public class Person {

	private Grid <Object> grid;
	private ContinuousSpace <Object> space;
	
	private double riskAversion; // fixed for lifetime; 0 to 1
	private double perceivedHardship; // 0 to 1
	public boolean active; // true if currently rebelling
	public int jailTerm; // remaining jail turns (if 0, then agent is not in jail)
	private int visNeighbors; // neighbor visibility radius range
	public int MAX_JAIL_TERM;
	private double grievance;
	private double govLegitimacy;
	private double arrestProb;
	public int red;
	public int green;
	public int blue;
	
	public Person(Grid<Object> grid, ContinuousSpace<Object> space,
			int visNeighbors, int maxJailTerm, double govLegitimacy) {
		this.grid = grid;
		this.space = space;
		this.visNeighbors = visNeighbors;
		this.MAX_JAIL_TERM = maxJailTerm;
		this.riskAversion = 1.0;
		this.perceivedHardship = 1.0;
		this.active = false;
		this.jailTerm = 0;
		this.govLegitimacy = govLegitimacy;
		this.grievance = 0;
		set_colors();
	}

	/*
	public Grid<Object> getGrid() {
		@SuppressWarnings("unchecked")
		final Grid<Object> grid = (Grid<Object>) ContextUtils.getContext(this)
				.getProjection(Constants.GRID_ID);
		if (null == grid) {
			throw new IllegalStateException("Cannot locate grid in context.");
		}
		return grid;
	}
	*/
	
	private void calc_grievance() {
		this.grievance = this.perceivedHardship * (1 - this.govLegitimacy);
	}
	
	private void est_arrest_prob(List<GridCell<Person>> personNeighborhood, 
			List<GridCell<Cop>> copNeighborhood) {
		
		int cCount = 0; // total cops
		int pTotal = 0; // total people
		int pActiveCount = 1; // total people who are active
							  // at least one so formula won't have zero in denominator
		
		// count cops
		for (GridCell<Cop> c : copNeighborhood) {
			cCount += c.size();
		}
		
		// count people
		for (GridCell<Person> p : personNeighborhood) {
			pTotal += p.size();
			Object obj = grid.getObjectAt(p.getPoint().getX(), p.getPoint().getY());
			if (obj instanceof Person) {
				if (((Person)obj).active == true)
					pActiveCount++;
			}
		}
		
		/*
		for (Object obj : personNeighborhood) {
			if (obj instanceof Person) {
				if (((Person)obj).active == true)
					pActiveCount++;
			}
		}
		*/
		
		if (Constants.DEBUG) {
			//System.out.println(location.toString()+ " visNeighbors "+visNeighbors);
			System.out.println("active people nearby "+pActiveCount);
			System.out.println("cops nearby "+cCount);
		}
		
		this.arrestProb = 1 - Math.exp(-Constants.K * Math.floor(cCount/pActiveCount));
		
		if (Constants.DEBUG)
			System.out.println("arrestProb "+arrestProb+" cCount "+cCount+" pActiveCount "+pActiveCount);
	}

	/* set colors base on rebellion or grievance */
	private void set_colors() {
		if (this.active==true) {
			this.red = 0xFF;
			this.green = 0x0;
			this.blue = 0x0;
		} else {
			// grievance is between 0 and 1
			this.red = 0x0;
			this.green = (int) (this.grievance * 0xFF);
			this.blue = 0x0;
		}
	}

	@ScheduledMethod(start = 1, interval = 1, priority = 0)
	public void step() {

		// if jailTurn == 0 (not in jail), then move and determine behavior
		if (this.jailTerm == 0) {
			
			GridPoint location = grid.getLocation(this);
			
			// people nearby
			List<GridCell<Person>> personNeighborhood = new GridCellNgh<Person>(
					grid, location, Person.class, visNeighbors, 
					visNeighbors).getNeighborhood(false);
			

			
			// look for empty cells
			List<GridCell<Person>> freeCells = SMUtils
				.getFreeGridCells(personNeighborhood);
			
			// move to empty cell
			if (freeCells.isEmpty()) {
				return;
			}
			SimUtilities.shuffle(freeCells, RandomHelper.getUniform());
			GridCell<Person> chosenFreeCell = SMUtils.randomElementOf(freeCells);

			GridPoint newGridPoint = chosenFreeCell.getPoint();
			grid.moveTo(this, newGridPoint.getX(), newGridPoint.getY());
			
			// cops nearby
			GridCellNgh<Cop> copNgh = new GridCellNgh<Cop>(grid, location, Cop.class,
					visNeighbors, visNeighbors);
			List<GridCell<Cop>> copNeighborhood = copNgh.getNeighborhood(false);


			
			//List<GridCell<Cop>> copNeighborhood = new GridCellNgh<Cop>(
			//		grid, location, Cop.class, 1, 
			//		1).getNeighborhood(false);
			
			// should this person be active (rebelling)?
			this.calc_grievance();
			this.est_arrest_prob(personNeighborhood, copNeighborhood);
			
			if (this.grievance - this.riskAversion * this.arrestProb > 
				Constants.THRESHOLD) {
				this.active = true;
				/*
				System.out.println("ACTIVE grievance "+grievance+
						" riskAversion "+riskAversion+
						" arrestProb "+arrestProb+
						" threshold "+Constants.THRESHOLD);
						*/
			}
			
		} else if (this.jailTerm > 0) {
			this.jailTerm--;
		}
		
		set_colors();
		//print_state();
	}

	public void print_state() {
		System.out.println("active? "+active+" G "+grievance+" A "+arrestProb);
	}

	@Override
	public String toString() {
		String location = grid.getLocation(this).toString();
		return String.format("Person @ location %s", location);
	}
}
