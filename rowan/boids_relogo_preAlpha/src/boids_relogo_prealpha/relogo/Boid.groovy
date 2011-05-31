package boids_relogo_prealpha.relogo

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
	def orientWeight = Utility.randomFloat(0.2) + 0.4
	def alignmentWeight = 1.0 - orientWeight
	
	def step(){
		forward(0.3)
		steer()
	}
	
	def steer(){
		orient() //the boid points itself so that it will on average move away from the Boid that is crowding it most
				 //and towards the furthest neighboring Boid
		//question: should orient() pay attention to Boids travelling in the opposite direction?
		def heading = getHeading()
		def flockHeading = alignmentDirection()
		if(Math.abs(heading-flockHeading) < 360){
			setHeading( (heading*orientWeight) + (flockHeading*alignmentWeight) )
		}
	}
	
	def orient(){
		//get an AgentSet of all Boid turtles on the 8 patches neighboring this Boid
		def boidSet = new AgentSet()
		for(Patch patch : neighbors()){
			boidSet.addAll(turtlesOn(patch))
		}
		def boidsTooClose = new AgentSet()
		def boidsTooFar = new AgentSet()
		for(Boid boid : boidSet){
			if(distance(boid) < desiredDistance){
				//move away from that Boid by some amount
				boidsTooClose.add(boid)
			}else if(distance(boid) > desiredDistance){
				//move towards that Boid by some amount
				boidsTooFar.add(boid)
			}
		}
		if(boidsTooFar.size() > 0){
			//find the farthest Boid that is still a neighbor
			def farthestBoid = maxOneOf( boidsTooFar, { distance(it) } )
			if(Math.abs(farthestBoid.getHeading() - getHeading()) < 5 ){
				if(towards(farthestBoid) < getHeading()){ //if this is true then we're on the righthand side of the Boid
					//to converge with the furthestBoid, we turn left slightly
					setHeading((getHeading() - 5*orientWeight + 360) % 360)
					//the equivalent of subtracting 5, taking into account the modular nature of degrees, i.e. turn left 5 degrees
				}else{
					//to diverge from the furthestBoid, we turn right slightly
					setHeading((getHeading() + 5*orientWeight) % 360) //add five degrees mod 360, i.e. turn right 5 degrees
				}
			}
		}
		if(boidsTooClose.size() > 0){
			//find the closest neighboring Boid and set dir2 to the opposite direction as it
			def closestBoid = minOneOf( boidsTooClose, {distance(it) } )
			//now we check to see if we're on a parallel course with this other, too-close Boid
			if(Math.abs(closestBoid.getHeading() - getHeading()) < 5 ){
				if(towards(closestBoid) < getHeading()){ //if this is true then we're on the righthand side of the Boid
					//to diverge from the closestBoid, we turn right slightly
					setHeading((getHeading() + 5*orientWeight) % 360) //add five degrees mod 360, i.e. turn right 5 degrees
				}else{
					setHeading((getHeading() - 5*orientWeight + 360) % 360)
					//the equivalent of subtracting 5, taking into account the modular nature of degrees, i.e. turn left 5 degrees
				}
			}
		}
	}
	
	/**
	 * Returns an average heading of all Boids in the neighborhood of this Boid
	 * @return
	 */
	def alignmentDirection(){
		//get an AgentSet of all Boid turtles on the 8 patches neighboring this Boid
		def boidSet = new AgentSet()
		for(Patch patch : neighbors()){
			boidSet.addAll(turtlesOn(patch))
		}
		
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
	
}
