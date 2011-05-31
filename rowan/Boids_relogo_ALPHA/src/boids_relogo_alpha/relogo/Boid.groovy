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
	def desiredDistance = 2
	def cohesionWeight = Utility.randomFloat(0.2) + 0.4 //this number will always be at least as large as alignmentWeigt - 
													   //giving more importance to spacing than doing what one's neighbor is doing
	def separationWeight = 1.0 - cohesionWeight
	def turnSpeed = 30
	
	def step(){
		forward(0.3)
//		setHeading((separationDirection() + cohesionDirection() + alignmentDirection()) / 3)
//		setHeading(averageTwoDirections(separationDirection(),cohesionDirection()))
//		setHeading(averageTwoDirections(getHeading(),separationDirection()))
//		setHeading(averageTwoDirections(getHeading(),cohesionDirection()))
		setHeading(averageTwoDirections( 
			averageTwoDirections(cohesionDirection(),separationDirection()) , 
			alignmentDirection()) 
			)
//		cohese()
//		separate()
//		setHeading(alignmentDirection())
	}
	
	def separationDirection(){
		//get the set of all neighboring Boids
		def boidsTooClose = new AgentSet()
		
		boidsTooClose = inRadius(turtles(),(desiredDistance - (desiredDistance*0.5)))
		boidsTooClose = other(boidsTooClose)
		
		if(boidsTooClose.size() > 0){
			def closestBoid = minOneOf( boidsTooClose, {distance(it)} )
			def avgDirectionOfTooCloseBoids = towards(closestBoid)
			boidsTooClose.remove(closestBoid)
			if(boidsTooClose.size() > 0){
				for(Boid boid: boidsTooClose){
				avgDirectionOfTooCloseBoids = averageTwoDirections( avgDirectionOfTooCloseBoids, towards(boid) )
				}
			}
			if(toMyLeft(avgDirectionOfTooCloseBoids)){ 
				//to diverge from the average direction of the too-close Boids, turn right slightly
				return ( getHeading() + turnSpeed*separationWeight ) //turn right slightly
//				label = "right"
			}else{
				return ( getHeading() - turnSpeed*separationWeight )//else turn left slightly
//				label = "left"
			}
		}else{
//		   label = "no"
		   return getHeading()
		}
	}
	
	def cohesionDirection(){
		def boidsTooFar = new AgentSet()
		for(Boid boid : turtles()){
			if( (distance(boid) > desiredDistance) && (distance(boid) < (desiredDistance*2)) ){
				boidsTooFar.add(boid)
			}
		}
		boidsTooFar = other(boidsTooFar)
		//if there are any Boids which are too close, find the closest one and steer away from it slightly
		if(boidsTooFar.size() > 0){
//			label = boidsTooFar.size()
			def farthestBoid = maxOneOf( boidsTooFar, {distance(it)} )
			if(toMyLeft(farthestBoid)){ 
				return (getHeading() - turnSpeed*cohesionWeight )
//					label = "left:"
			}else{
				return ( getHeading() + turnSpeed*cohesionWeight )
//					label = "right:"
			}
		}else{
//			label = ""
			return getHeading()
		}
	}
	
	/**
	 * Returns an average heading of all Boids in the neighborhood of this Boid 
	 * @return
	 */
	def alignmentDirection(){
		//get an AgentSet of all Boid turtles on the 8 patches neighboring this Boid
		def boidSet = inRadius(turtles(),(desiredDistance * 1.0))
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
	
	def toMyLeft(double dir){
//		label = ""+dir
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
	
	def moveTowards(Boid boid, double amt){
		def heading = getHeading()
//		label = (int)(amt*10)
		face(boid)
		forward(amt)
		setHeading(heading)
	}
	
	def moveAwayFrom(Boid boid, double amt){
		def heading = getHeading()
//		label = (int)(amt*10)
		face(boid)
		back(amt)
		setHeading(heading)
	}
	
}
