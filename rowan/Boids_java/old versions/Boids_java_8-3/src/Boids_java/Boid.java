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
	
	private final double turnSpeed = 0.4;			
	private final double DESIRED_DISTANCE = 0.66;
	private final double separationWeight = RandomHelper.nextDoubleFromTo(0.4, 0.6);
	private final double cohesionWeight = 1.0 - separationWeight;
	private final double distance = 0.05;//amount to travel forward each step
	private double abd;
	
	public Boid(ContinuousSpace<Object> space, Grid<Object> grid){
		this.space = space;
		this.grid = grid;
		heading = randomRadian();
//		heading = 0;
		abd = 0.0;
	}
	
	@ScheduledMethod(start = 1, interval = 1)
	public void step(){
		forward();
		
//		heading = alignmentDirection();
//		heading = cohesionDirection();
//		heading = separationDirection();
		heading = averageTwoDirections(cohesionDirection(),separationDirection());
		/*
		heading = averageTwoDirections(cohesionDirection(),separationDirection());
		heading = averageTwoDirections(heading,alignmentDirection());
		
		double heading1 = averageTwoDirections(cohesionDirection(),separationDirection());
		heading1 = averageTwoDirections(heading1,heading);
		double deviation = averageTwoDirections(heading,randomRadian());
		double heading2 = averageTwoDirections(alignmentDirection(),deviation);
		heading2 = averageTwoDirections(heading2,heading);
		heading = averageTwoDirections(heading1,heading2);
		*/
	}
	
	public double cohesionDirection(){
		ArrayList<Boid> boidSet = neighborhood();
		if(boidSet.size() > 0){
			
			ArrayList<Double> dirSet = new ArrayList<Double>();
			for(Boid boid : boidSet){
				dirSet.add(towards(boid));
			}
			double avgBoidDirection = averageNDirections(dirSet);
			dirSet.clear();
			for(Boid boid : boidSet){
				dirSet.add(distance(boid));
			}
			double avgBoidDistance = averageNDirections(dirSet);
			if(avgBoidDistance > DESIRED_DISTANCE){
				moveTowards(avgBoidDirection,0.2*cohesionWeight);
			}
			abd = avgBoidDirection;
			return avgBoidDirection;
		}else{
			abd = 0.0;
			return heading;
		}
	}
	
	public double getABD(){
		return abd;
	}
	
	public double separationDirection(){
		ArrayList<Boid> boidSet = neighborhood();
		ArrayList<Boid> boidsTooClose = new ArrayList<Boid>();
		for(Boid boid : boidSet){
			if(distance(boid) < DESIRED_DISTANCE*0.5){
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
//				return averageTwoDirections(((heading+turnSpeed)%Math.PI*2),heading);
				return (heading+turnSpeed)%Math.PI*2;
			}else{
				double newHeading = heading-turnSpeed;
				if(newHeading < 0)newHeading += Math.PI*2;
//				return averageTwoDirections((newHeading%Math.PI*2),heading);
				return newHeading%Math.PI*2;
			}
		}
		return heading;
	}
	
	public double alignmentDirection(){
		ArrayList<Boid> boidSet = neighborhood();
		
		double averageHeading = heading;
		for(Boid boid : boidSet){
			averageHeading = averageTwoDirections(averageHeading,boid.getHeading());
		}
		return averageHeading;
	}

	public double towards(Boid boid){
		NdPoint myPoint = space.getLocation(this);
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
	
	public double averageNDirections(ArrayList<Double> dirSet){
		if(dirSet.size()==2){
			return averageTwoDirections(dirSet.get(0),dirSet.get(1));
		}else if(dirSet.size()==1){
			return dirSet.get(0);
		}else{
			double result = averageTwoDirections(dirSet.get(0),dirSet.get(1));
			dirSet.remove(0);
			dirSet.remove(1);
			dirSet.add(result);
			return averageNDirections(dirSet);
		}
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
		else return Math.toRadians(oppositeDirectionDeg( (oppositeDirectionDeg(angle1) + oppositeDirectionDeg(angle2)) / 2 ));
	}
	
	public double oppositeDirectionDeg(double angle){
		assert(angle <= 360);
		return (angle+180) % 360;
	}
	
	public double oppositeDirection(double angle){
		assert(angle <= Math.PI*2);	
		angle = Math.toDegrees(angle);
		return Math.toRadians((angle + 180) % 360);
	}
	
	public void moveTowards(double direction, double dist){
		assert(direction <= Math.PI*2);
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
		SimUtilities.shuffle(boidSet,RandomHelper.getUniform());
		boidSet.remove(this);
		ArrayList<Boid> circularNeighborhood = new ArrayList<Boid>();
		for(Boid boid : boidSet){
			if(distance(boid) <= 1.0){
				circularNeighborhood.add(boid);
			}
		}
		return circularNeighborhood;
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
	
	public double avgBoidDistance(){
		double distance = DESIRED_DISTANCE;
		for(Boid boid : neighborhood()){
			distance = (distance(boid)+distance)/2;
		}
		return distance;
	}
}