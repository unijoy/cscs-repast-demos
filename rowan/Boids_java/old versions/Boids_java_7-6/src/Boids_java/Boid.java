package Boids_java;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import repast.simphony.engine.schedule.ScheduledMethod;
import repast.simphony.query.space.grid.GridCell;
import repast.simphony.query.space.grid.GridCellNgh;
import repast.simphony.query.space.grid.MooreQuery;
import repast.simphony.random.RandomHelper;
import repast.simphony.space.Direction;
import repast.simphony.space.SpatialMath;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.continuous.NdPoint;
import repast.simphony.space.grid.Grid;
import repast.simphony.space.grid.GridPoint;
import repast.simphony.util.SimUtilities;
import java.lang.Iterable;

public class Boid {
	private ContinuousSpace<Object> space;
	private Grid<Object> grid;
	private double heading; //in Radians
	
	private final double turnSpeed = 0.5;			
	private final double DESIRED_DISTANCE = 1.0;
	private final double separationWeight = 0.5;
	private final double cohesionWeight = 1.0 - separationWeight;
	private final double distance = 0.3;
	
	public Boid(ContinuousSpace<Object> space, Grid<Object> grid){
		this.space = space;
		this.grid = grid;
		heading = randomRadian();
//		System.out.print(",");
	}
	
	@ScheduledMethod(start = 1, interval = 1)
	public void step(){
		assert(heading <= Math.PI*2);
		forward();
//		heading = alignmentDirection();
//		heading = cohesionDirection();
//		heading = separationDirection();
		heading = averageTwoDirections(
					averageTwoDirections(averageTwoDirections(cohesionDirection(),heading),
										 averageTwoDirections(separationDirection(),heading)),
					alignmentDirection()
				  );
		
	}
//step
	/*
	 def step(){
		forward(0.3)
		def heading = getHeading()
		setHeading(averageTwoDirections( 
			averageTwoDirections( averageTwoDirections(cohesionDirection(), getHeading()), 
			averageTwoDirections(getHeading(),separationDirection()) ), 
			alignmentDirection()) 
			)
	}
	 */
	
	
	
	public double cohesionDirection(){
		ArrayList<Boid> boidSet = neighborhood();
		
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
	 
	public double separationDirection(){
		ArrayList<Boid> boidSet = neighborhood();
		ArrayList<Boid> boidsTooClose = new ArrayList<Boid>();
		for(Boid boid : boidSet){
			if(distance(boid) < DESIRED_DISTANCE){
				boidsTooClose.add(boid);
			}
		}
		if(boidsTooClose.size() > 0){
			double avgBoidDirection = heading;
			for(Boid boid : boidsTooClose){
				avgBoidDirection = averageTwoDirections(boid.getHeading(),avgBoidDirection);
			}
			double distanceToClosestBoid = Double.MAX_VALUE;
			for(Boid boid : boidsTooClose){
				if(distance(boid) < distanceToClosestBoid){
					distanceToClosestBoid = distance(boid);
				}
			}
			if(distanceToClosestBoid < DESIRED_DISTANCE*0.5){
				moveAwayFrom(avgBoidDirection, 0.2*separationWeight);
			}
			if(toMyLeft(avgBoidDirection)){
				return (heading+turnSpeed)%Math.PI*2;
			}else{
				return (heading-turnSpeed)%Math.PI*2;
			}
		}
		return heading;
	}
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
	 */
	public double alignmentDirection(){
		ArrayList<Boid> boidSet = neighborhood();
		
		double averageHeading = heading;
		for(Boid boid : boidSet){
			averageHeading = averageTwoDirections(averageHeading,towards(boid));
		}
		return averageHeading;
	}
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
	
	/**
	 * 
	 * @param dir1 first angle in radians
	 * @param dir2 second angle in radians
	 * @return
	 */
	public double averageTwoDirections(double angle1, double angle2){
		assert(angle1 <= Math.PI*2);
		assert(angle2 <= Math.PI*2);
		
		angle1 = Math.toDegrees(angle1);
		angle2 = Math.toDegrees(angle2);
		//averages together the two directions if they are less than 180 degrees apart
		if(Math.abs(angle1-angle2) < 180){
			return Math.toRadians((angle1+angle2)/2);
		}
		//averages together the opposite of the two directions, then computes the opposite direction of that result
		else return oppositeDirection( (oppositeDirection(angle1) + oppositeDirection(angle2)) / 2 );
	}
	
	public double oppositeDirection(double angle){
		assert(angle <= Math.PI*2);
		
		angle = Math.toDegrees(angle);
		return Math.toRadians((angle + 180) % 360);
	}
	
	public void moveTowards(double direction, double dist){
		NdPoint pt = space.getLocation(this);
		double moveX = pt.getX() + Math.cos(heading)*dist;
		double moveY = pt.getY() + Math.sin(heading)*dist;
		space.moveTo(this, moveX, moveY);
	}
	
	public void moveAwayFrom(double direction, double dist){
		NdPoint pt = space.getLocation(this);
		double moveX = pt.getX() - Math.cos(heading)*dist;
		double moveY = pt.getY() - Math.sin(heading)*dist;
		space.moveTo(this, moveX, moveY);
	}
	
	public double randomRadian(){
		return Math.random()*(2*Math.PI);
	}
	
	public void forward(){
		NdPoint pt = space.getLocation(this);
		double moveX = pt.getX() + Math.cos(heading)*distance;
		double moveY = pt.getY() + Math.sin(heading)*distance;
		space.moveTo(this, moveX, moveY);
		grid.moveTo(this, (int)moveX, (int)moveY);
	}
	
	public ArrayList<Boid> neighborhood(){
		//get a set of all neighbors
		MooreQuery<Boid> query = new MooreQuery(grid,this);
		Iterator<Boid> iter = query.query().iterator();
		ArrayList<Boid> boidSet = new ArrayList<Boid>();
		while(iter.hasNext()){
			boidSet.add(iter.next());
		}
		//get all the Boids on that same tile
		Iterable<Object> list = grid.getObjectsAt(grid.getLocation(this).getX(),grid.getLocation(this).getY());
		for(Object boid : list){
			boidSet.add((Boid) boid);
		}
//		SimUtilities.shuffle(boidSet,RandomHelper.getUniform());
		boidSet.remove(this);
		return boidSet;
	}
	
	public int neighborCount(){
		ArrayList<Boid> boidSet = neighborhood();
		return boidSet.size();
	}
	
	public boolean toMyLeft(double angle){
		double angleDegrees = Math.toDegrees(angle);
		double headingDegrees = Math.toDegrees(heading);
		if(Math.abs(angleDegrees - headingDegrees) < 180){
			if(angleDegrees < headingDegrees){
				return true;
			}else return false;
		}else{
			if(angleDegrees < headingDegrees){
				return false;
			}else return true;
		}
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
