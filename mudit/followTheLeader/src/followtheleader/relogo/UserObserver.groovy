package followtheleader.relogo

import static repast.simphony.relogo.Utility.*;
import static repast.simphony.relogo.UtilityG.*;
import repast.simphony.relogo.BaseObserver;
import repast.simphony.relogo.Stop;
import repast.simphony.relogo.Turtle;
import repast.simphony.relogo.Utility;
import repast.simphony.relogo.UtilityG;

/**
*
* In this class, the environment is set up and ants are created. Few variables are used to keep track
* of the length of route of various ants. Ants are asked to move and track of ticks is kept.
*
* @author Mudit Raj Gupta
*
*/

class UserObserver extends BaseObserver{

	//first ant route length
	def fr
	
	//present ant route length
	def pr
	
	//sets up the world
	def setup()
	{
	
		//clears everything
		clearAll()
			
		//sets up environment
			ask(patches())
			{
				setFood()
				setNest()
			}
			
			//create leader ant
			setDefaultShape(Ant,"turtle")
			createAnts(1)
			{
				setxy(-8,0)
				setColor(red())
			}
			ask(ants()){
				setLeader()
			}
			
			//create follower ant
			createAnts((numAnts-1))
			{
				setxy(-8,0)
				setColor(red())
			}
			ask(ants()){
				setFollower()
			}
		}
		
	    //makes the ant move	
		def go()
		{
			//asks all ants to move
			ask(ants())
			{
				step()
			}
			
			//increments time
			tick()
			
			//calculates the route length of the first ant
			firstAnt()
			
			//calculates the route length of the present ant
			presentAnt()
		}
		
		//time counter
		def timestamp()
		{
			ticks()
		}
		
		//sets first ants route
		def firstAnt()
		{	
			fr=patch(0,0).firstRoute
			
		}
		
		//sets present ants route
		def presentAnt()
		{			pr=patch(0,0).presentRoute 
		
		}		
	}