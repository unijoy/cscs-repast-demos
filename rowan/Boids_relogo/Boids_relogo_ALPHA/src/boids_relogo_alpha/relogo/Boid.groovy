package boids_relogo_alpha.relogo

import static repast.simphony.relogo.Utility.*;
import static repast.simphony.relogo.UtilityG.*;
import repast.simphony.relogo.BasePatch;
import repast.simphony.relogo.BaseTurtle;
import repast.simphony.relogo.Plural;
import repast.simphony.relogo.Stop;
import repast.simphony.relogo.Utility;
import repast.simphony.relogo.UtilityG;
import repast.simphony.relogo.*;

class Boid extends BaseTurtle {
	def neighborhoodRadius = 4
	def desiredDistance = 2
	def cohesionWeight = Utility.randomFloat(0.2) + 0.4 
	def separationWeight = 1.0 - cohesionWeight
	def turnSpeed = Utility.random(5) + 5
	
	def step(){
		forward(0.3)
		def heading = getHeading()
		setHeading(averageTwoDirections( 
			averageTwoDirections( averageTwoDirections(cohesionDirection(), getHeading()), averageTwoDirections(getHeading(),separationDirection()) ), 
			alignmentDirection()) 
			)
	}
	
	//for each Boid that is too close, turn away more.
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
	/*
	def toMyLeft(Boid boid){
		if(Math.abs(towards(boid) - getHeading()) < 180){
			if(towards(boid) < getHeading())
				return true
			else return false
		}else{
			if(towards(boid) < getHeading())
				return false
			else return true
		}
	}
	*/
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
	/* 
	def moveTowards(Boid boid, double amt){
		def heading = getHeading()
		face(boid)
		forward(amt)
		setHeading(heading)
	}
	
	def moveAwayFrom(Boid boid, double amt){
		def heading = getHeading()
		face(boid)
		back(amt)
		setHeading(heading)
	}
	*/
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
}
