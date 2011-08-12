package followtheleader.relogo

import static repast.simphony.relogo.Utility.*;
import static repast.simphony.relogo.UtilityG.*;
import repast.simphony.relogo.BaseObserver;
import repast.simphony.relogo.Stop;
import repast.simphony.relogo.Turtle;
import repast.simphony.relogo.Utility;
import repast.simphony.relogo.UtilityG;

class UserObserver extends BaseObserver{

	def firstRoute
	def presentRoute
	
	def setup(){
			clearAll()
			
			ask(patches())
			{
				setFood()
				setNest()
			}
			
			setDefaultShape(Ant,"turtle")
			createAnts(1)
			{
				setxy(-8,0)
				setColor(red())
			}
			ask(ants()){
				setLeader()
			}
			createAnts((numAnts-1))
			{
				setxy(-8,0)
				setColor(red())
			}
			ask(ants()){
				setFollower()
			}
		}
			
		def go()
		{
			ask(ants())
			{
				step()
			}
			tick()
		}
		
		def timestamp()
		{
			ticks()
		}
		
		def firstAnt()
		{	
			if(first==-1)
				firstRoute=-1
			else
				firstRoute = turtle(first).moved
		}
		
		def presentAnt()
		{	
			(count(ants())-count(turtlesAt(-8,0))-count(turtlesAt(8,0)))
			/*		
			if(present==-1)
				presentRoute=-1
			else
				presentRoute = turtle(present).moved 
				*/
		}
				
	}