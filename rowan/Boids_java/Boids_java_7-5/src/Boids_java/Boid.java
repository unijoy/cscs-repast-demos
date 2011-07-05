package Boids_java;

import java.util.ArrayList;
import java.util.List;
import repast.simphony.query.space.grid.GridCell;
import repast.simphony.query.space.grid.GridCellNgh;
import repast.simphony.random.RandomHelper;
import repast.simphony.space.SpatialMath;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.continuous.NdPoint;
import repast.simphony.space.grid.Grid;
import repast.simphony.space.grid.GridPoint;
import repast.simphony.util.SimUtilities;

public class Boid {
	private ContinuousSpace<Object> space;
	private Grid<Object> grid;
	private double heading; //in Radians
	
	private final int turnSpeed = 30;
	private final double DESIRED_DISTANCE = 2;
	private final double separationWeight = 0.5;
	private final double cohesionWeight = 1.0 - separationWeight;
	
	public Boid(ContinuousSpace<Object> space, Grid<Object> grid){
		this.space = space;
		this.grid = grid;
		heading = randomRadian();
		// @TODO this method needs to randomly place the Boid on the space and grid objects.
	}
	
	public void step(){
		forward(0.3);
		heading = randomRadian();
		//set heading according to the Three Rules
	}
//step
	/*
	 def step(){
		forward(0.3)
		def heading = getHeading()
		setHeading(averageTwoDirections( 
			averageTwoDirections( averageTwoDirections(cohesionDirection(), getHeading()), averageTwoDirections(getHeading(),separationDirection()) ), 
			alignmentDirection()) 
			)
	}
	 */
	
	public double cohesionDirection(){
	//get a set of all neighbors
		GridPoint pt = grid.getLocation(this);
		//GridCellNgh... can't find documentation on this class
		GridCellNgh<Boid> nghCreator = new GridCellNgh<Boid>
			(grid,pt,Boid.class,1,1);
		//note: java.util.List; not java.awt.List;
		//use GridCellNgh to create a list of GridCells containing Boids
		List<GridCell<Boid>> gridCells = nghCreator.getNeighborhood(true);
//		SimUtilities.shuffle(gridCells,RandomHelper.getUniform());
		ArrayList<Boid> boidSet = new ArrayList<Boid>();
		for(GridCell<Boid> cell : gridCells){
			//can't get the actual Boid objects from a GridCell<E>... le sigh
		}
		
	//so let's assume that we have an ArrayList<Boid> boidSet full of the
	//neighboring Boids... what next?
		if(boidSet.size() > 0){
			double avgBoidDirection = heading;
			for(Boid boid : boidSet){
				avgBoidDirection = averageTwoDirections(boid.getHeading(),avgBoidDirection);
			}
			double avgBoidDistance = DESIRED_DISTANCE;
			for(Boid boid : boidSet){
				avgBoidDistance = avgBoidDistance + distance(boid)/ 2;
			}
			if(avgBoidDistance > DESIRED_DISTANCE){
				moveTowards(avgBoidDirection,0.2*cohesionWeight);
				return avgBoidDirection;
			}else{
				return heading;
			}
		}else{
			return heading;
		}
	}
//cohesion
	/*
	 def cohesionDirection(){
		def boidSet = inRadius(turtles(),neighborhoodRadius)
		boidSet = other(boidSet)
		if(boidSet.size() > 0){
			def avgBoidDirection = getHeading()
			for(Boid boid : boidSet){
				avgBoidDirection = averageTwoDirections(towards(boid),avgBoidDirection)
			}
			def avgBoidDistance = desiredDistance
			for(Boid boid : boidSet){
				avgBoidDistance = (avgBoidDistance + distance(boid)) / 2
			}
			if(avgBoidDistance > desiredDistance){
//				moveTowards(avgBoidDirection,(0.2*cohesionWeight))
				return avgBoidDirection
			}else{
			   return getHeading()
			}
		}else{
			return getHeading()
		}
	}
	 
	public double separationDirection(){
		//assume we have a set of all neighboring boids
		
		if(boidsTooClose.size() > 0){
			//find the average direction of the Boids which are too close
			//if the closest Boid is less than half the desired distance in closeness, move away
			//turn away from the average direction of the too-close Boids
			double avgBoidDirection = heading;
			for(Boid boid : boidsTooClose){
				avgBoidDirection = averageTwoDirections(boid.getHeading(),avgBoidDirection);
			}
			Boid closestBoid = null;
			double distanceToClosestBoid = Double.MAX_VALUE;
			for(Boid boid : boidsTooClose){
				if(distance(boid) < distanceToClosestBoid){
					closestBoid = boid;
					distanceToClosestBoid = distance(boid);
				}
			}
			if(distanceToClosestBoid < DESIRED_DISTANCE*0.5){
				moveAwayFrom(avgBoidDirection, 0.2*separationWeight);
			}
			if(toMyLeft(avgBoidDirection)){
				return (heading+turnSpeed)%360;
			}else{
				return (heading-turnSpeed)%360;
			}
		}
		return heading;
	}*/
//separation
	/*
	 def separationDirection(){
		def boidsTooClose = inRadius(turtles(), desiredDistance)
		boidsTooClose = other(boidsTooClose)
		
		if(boidsTooClose.size() > 0){
			def boidsTooCloseSize = boidsTooClose.size()
			def closestBoid = minOneOf( boidsTooClose, {distance(it)} )
			def avgDirectionOfTooCloseBoids = towards(closestBoid)
			boidsTooClose.remove(closestBoid)
			if(boidsTooClose.size() > 0){
				for(Boid boid: boidsTooClose){
				avgDirectionOfTooCloseBoids = averageTwoDirections( avgDirectionOfTooCloseBoids, towards(boid) )
				}
			}
//			if(distance(closestBoid) < desiredDistance*0.5){
				moveAwayFrom(avgDirectionOfTooCloseBoids,(0.2*separationWeight))
//			}
			if(toMyLeft(avgDirectionOfTooCloseBoids)){
				return (getHeading() + turnSpeed*separationWeight)
			}else{
				return (getHeading() - turnSpeed*separationWeight)
			}
		}else{
		   return getHeading()
		}
	}
	 
	public double alignmentDirection(){
		//@TODO set of all neighbors
		double averageHeading = heading;
		for(Boid boid : boidSet){
			averageHeading = averageTwoDirections(averageHeading,towards(boid));
		}
		return averageHeading;
	}*/
//alignment
	/*
	 def alignmentDirection(){
		//get an AgentSet of all Boid turtles on the 8 patches neighboring this Boid
		def boidSet = inRadius(turtles(),neighborhoodRadius)
		boidSet = other(boidSet)
		//now we need to set up a variable that will end up being the average heading of all those neighboring Boids
		def heading = getHeading()
		for(Boid boid : boidSet){
			heading = averageTwoDirections( heading, boid.getHeading() )
		}
		return heading
	}
	 */
	public double towards(Boid boid){
		NdPoint myPoint = space.getLocation(boid);
		NdPoint otherPoint = space.getLocation(boid);
		return SpatialMath.calcAngleFor2DMovement(space, myPoint, otherPoint);
	}
	
	public void forward(double distance){
		space.moveByVector(this, distance, heading);
	}
	
	public double distance(Boid boid) {
		NdPoint myPoint = space.getLocation(this);
		NdPoint otherPoint = space.getLocation(boid);
		double differenceX = Math.abs(myPoint.getX() - otherPoint.getX());
		double differenceY = Math.abs(myPoint.getY() - otherPoint.getY());
		return Math.hypot(differenceX, differenceY);
	}

	public double getHeading(){
		return heading;
	}
	
	public void setHeading(double heading){
		this.heading = heading;
	}
	
	public double averageTwoDirections(double dir1, double dir2){
		//averages together the two directions if they are less than 180 degrees apart
		if(Math.abs(dir1-dir2) < 180){
			return (dir1+dir2)/2;
		}
		//averages together the opposite of the two directions, then computes the opposite direction of that result
		else return oppositeDirection( (oppositeDirection(dir1) + oppositeDirection(dir2)) / 2 );
	}
	
	public double oppositeDirection(double dir){
		return (dir + 180) % 360;
	}
	
	public void moveTowards(double direction, double distance){
		space.moveByVector(this, distance, Math.toRadians(direction));
	}
	
	public void moveAwayFrom(double direction, double distance){
		space.moveByVector(this, distance, Math.toRadians(oppositeDirection(direction)));
	}
	
	public double randomRadian(){
		return Math.random()*(2*Math.PI);
	}
	/*
	 	def averageTwoDirections(double dir1, double dir2){
		//averages together the two directions if they are less than 180 degrees apart
		if(Math.abs(dir1-dir2) < 180){
			return (dir1+dir2)/2
		}
		//averages together the opposite of the two directions, then computes the opposite direction of that result
		else return oppositeDirection( (oppositeDirection(dir1) + oppositeDirection(dir2)) / 2 )
	}
	
	def oppositeDirection(double dir){
		return (dir + 180) % 360
	}
	
	def toMyLeft(double dir){
		if(Math.abs(dir - getHeading()) < 180){
			if(dir < getHeading())
				return true
			else return false
		}else{
			if(dir < getHeading())
				return false
			else return true
		}
	}
	
	def moveTowards(double dir, double amt){
		def heading = getHeading()
		setHeading(dir)
		forward(amt)
		setHeading(heading)
	}
	
	def moveAwayFrom(double dir, double amt){
		def heading = getHeading()
		setHeading( oppositeDirection(dir) )
		forward(amt)
		setHeading(heading)
	}
	 */
}
