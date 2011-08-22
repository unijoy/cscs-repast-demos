package boids_relogo_cohesion.relogo


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
	def turnSpeed = 30
	
	def step(){
		forward(0.3)
		setHeading( cohesionDirection() )
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
				return avgBoidDirection
			}else{
			   return getHeading()
			}
		}else{
			return getHeading()
		}
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
	
}
