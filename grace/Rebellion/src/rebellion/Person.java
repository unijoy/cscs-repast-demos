/**
 * 
 */
package rebellion;

import java.util.List;

import repast.simphony.engine.schedule.ScheduledMethod;
import repast.simphony.query.space.grid.GridCell;
import repast.simphony.query.space.grid.GridCellNgh;
import repast.simphony.random.RandomHelper;
import repast.simphony.space.grid.Grid;
import repast.simphony.space.grid.GridPoint;
import repast.simphony.util.ContextUtils;
import repast.simphony.util.SimUtilities;
/**
 * @author grace
 *
 */
public class Person {

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
	
	public Person(int visNeighbors, int maxJailTerm, double govLegitimacy) {
		super();
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

	
	public Grid<Object> getGrid() {
		@SuppressWarnings("unchecked")
		final Grid<Object> grid = (Grid<Object>) ContextUtils.getContext(this)
				.getProjection(Constants.GRID_ID);
		if (null == grid) {
			throw new IllegalStateException("Cannot locate grid in context.");
		}
		return grid;
	}

	
	private void calc_grievance() {
		this.grievance = this.perceivedHardship * (1 - this.govLegitimacy);
	}
	
	private void est_arrest_prob(List<GridCell<Person>> personNeighborhood, 
			List<GridCell<Cop>> copNeighborhood) {
		
		int cCount = 0;
		int pActiveCount = 1; // at least one so formula won't have zero in denominator
		
		for (Object obj : copNeighborhood) {
			if (obj instanceof Cop) {
				cCount++;
			}
		}
		
		for (Object obj : personNeighborhood) {
			if (obj instanceof Person) {
				if (((Person)obj).active == true)
					pActiveCount++;
			}
		}
		
		if (Constants.DEBUG)
			System.out.println("cops count "+cCount+" active people count "+pActiveCount);
		
		this.arrestProb = 1 - Math.exp(-Constants.K * Math.floor(cCount/pActiveCount));
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
			
			GridPoint location = getGrid().getLocation(this);
			
			// people nearby
			List<GridCell<Person>> personNeighborhood = new GridCellNgh<Person>(
					getGrid(), location, Person.class, visNeighbors, 
					visNeighbors).getNeighborhood(false);
			
			// look for empty cells nearby
			List<GridCell<Person>> freeCells = SMUtils
				.getFreeGridCells(personNeighborhood);
			
			// move to empty cell
			if (freeCells.isEmpty()) {
				return;
			}
			SimUtilities.shuffle(freeCells, RandomHelper.getUniform());
			GridCell<Person> chosenFreeCell = SMUtils.randomElementOf(freeCells);

			GridPoint newGridPoint = chosenFreeCell.getPoint();
			getGrid().moveTo(this, newGridPoint.getX(), newGridPoint.getY());
			
			// cops nearby
			List<GridCell<Cop>> copNeighborhood = new GridCellNgh<Cop>(
					getGrid(), location, Cop.class, visNeighbors, 
					visNeighbors).getNeighborhood(false);
			
			// should this person be active (rebelling)?
			this.calc_grievance();
			this.est_arrest_prob(personNeighborhood, copNeighborhood);
			if (this.grievance - this.riskAversion * this.arrestProb > 
				Constants.THRESHOLD) {
				this.active = true;
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
		final String location = (ContextUtils.getContext(this) != null) ? getGrid()
				.getLocation(this).toString() : "[?, ?]";

		return String.format("Person @ location %s", location);
	}
}
