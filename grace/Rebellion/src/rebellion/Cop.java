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

public class Cop {
	
	private int visNeighbors;
	
	public Cop(int visNeighbors) {
		super();
		this.visNeighbors = visNeighbors;
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
	
	@ScheduledMethod(start = 1, interval = 1, priority = 0)
	public void step() {

		GridPoint location = getGrid().getLocation(this);
		
		// look for active people in the area
		List<GridCell<Person>> peopleNeighborhood = new GridCellNgh<Person>(
			getGrid(), location, Person.class, visNeighbors,
			visNeighbors).getNeighborhood(false);

		// shuffle to pick random person
		SimUtilities.shuffle(peopleNeighborhood, RandomHelper.getUniform());
		
		// arrest an active person
		for (Object obj : peopleNeighborhood) {
			if (obj instanceof Person) {
				Person p = (Person) obj;
				if (p.active == true) {
					p.active = false;
					p.jailTerm = RandomHelper.nextIntFromTo(0, p.MAX_JAIL_TERM);
				}
			}
		}
		
		// pick an empty cell to move to
		List<GridCell<Person>> freeCells = SMUtils
			.getFreeGridCells(peopleNeighborhood);
		
		if (freeCells.isEmpty()) {
			return;
		}
		
		SimUtilities.shuffle(freeCells, RandomHelper.getUniform());
		GridCell<Person> chosenFreeCell = SMUtils.randomElementOf(freeCells);
		
		GridPoint newGridPoint = chosenFreeCell.getPoint();
		getGrid().moveTo(this, newGridPoint.getX(), newGridPoint.getY());
		
	}
	
	
	@Override
	public String toString() {
		// This may happen when testing
		final String location = (ContextUtils.getContext(this) != null) ? getGrid()
				.getLocation(this).toString() : "[?, ?]";

		return String.format("Cop @ location %s", location);
	}
}
