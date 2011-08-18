package ant.relogo

import static repast.simphony.relogo.Utility.*;
import static repast.simphony.relogo.UtilityG.*;
import repast.simphony.relogo.BasePatch;
import repast.simphony.relogo.BaseTurtle;
import repast.simphony.relogo.Plural;
import repast.simphony.relogo.Stop;
import repast.simphony.relogo.Utility;
import repast.simphony.relogo.UtilityG;

/**
 * This is a bio-inspired model. The model is a simulation of Ant and their behavior. 
 * The model demonstrates how ants move away from their nests in search for food and then
 * come back to the nest with food.This behavior is used in computational intelligence 
 * and various other fields. The paper which is an inspiration for this model :
 * 
 *  - "Ant System: Optimization by a Colony of Cooperating Agents", Marco Dorigo, Vittorio 
 *  Maniezzo and Alberto Colorni, IEEE TRANSACTIONS ON SYSTEMS, MAN, AND CYBERNETICS-PART B 
 *  CYBERNETICS, VOL 26, NO 1, FEBRUARY 1996, 1083-4419, IEEE 1996
 *
 * Ant only move a given distance away from their nest. This is because they have to come 
 * back to their nest. The ant return to their nest depending on the nest smell, they do so 
 * when the nest scent starts dying. If an ant finds food, it carries it back to its nest 
 * leaving a chemical trail which evaporates with time. The chemical is also diffused in 
 * the Moore's neighborhood, although the "level" is less. If an ant sniffs Chemical it moves 
 * in the direction where the chemical is for a large number of time. The ants reach the food 
 * pile, following the chemical and finally the come back to the nest reinforcing the chemical 
 * trail.
 *
 * The ant behavior of significant interest to various researches. The model provides a demo plot 
 * of the rate at which the food is consumed in all the three piles of food. Many other such 
 * behavioral plots can be added to study the possibilities.
 * 
 * See:
 *
 * http://code.google.com/p/cscs-repast-demos/wiki/Ant
 *
 * for a full description of the model and details.
 *
 * @author Mudit Raj Gupta
 *
 * */

class Ant extends BaseTurtle 
{
	//State Variables
	def inNest=1
	def away=1
	def gotCube=0
	def nearChem=0
	
	//Saving Initial and Final Coordinates to avoid rings
	def Xi = 0
	def Yi = 0
	def Xf = 0
	def Yf = 0
	
	//Called at each clock tick, decides the motion of the ant depending of state	
	def step()
	{	
		//Initial Coordinates saved
		Xi = getXcor()
		Yi = getYcor()
		
		//Checks if the ant is near a cube and sets gotCube
		foundCube()
			
		//Checks if the ant is in it's nest and sets inNest
		foundNest()
	
		//Checks if the ant is near a chemical and sets nearChem
		checkChem()
	
		//Sets the direction of travel of the Ant by setting away
		setDir()
	
		//if ants finds food
		if(gotCube)
			//drops Chemical
			drop()
			
		//movement of the ant, if ant is not near chemiCal
		decide()
		Xf=getXcor()
		Yf=getYcor()
		if(Xi==Xf&&Yi==Yf)
		{
			nearChem=0
			patchHere().level-=2	
			decide()
		}
	}
		
	//Sets the nest scent ant's direction by setting away
	def setDir()
	{
		if(gotCube||getXcor()>15||getYcor()>15||getXcor()<-15||getYcor()<-15)
			away=0
		if(inNest)
			away=1
	}

	//Checks chem
	def decide()
	{
		if(!nearChem)
		{
			if(away)
				goAway()
			else
				comeBack()
		}
	}
	
	//away=1
	def goAway()
	{
		facexy(0,0)
		left(180)
		move()
	}
	
	//away=0
	def comeBack()
	{
		facexy(0,0)
		move()
	}
	
	//moving 
	def move()
	{
		if(random(2))
			left(random(90))
		else
			left(-1*random(90))
		forward(0.8)
	}
	
	//If it finds cube
	def foundCube()
	{
		if(patchHere().cubes1>0)
		{
				setColor(blue())
				gotCube=1
				patchHere().cubes1--
		}
		if(patchHere().cubes2>0)
		{
				setColor(blue())
				gotCube=1
				patchHere().cubes2--
		}
		if(patchHere().cubes3>0)
		{
				setColor(blue())
				gotCube=1
				patchHere().cubes3--
		}
	}
	
	//If it finds nest
	def foundNest()
	{
		if(distancexy(0,0)<4)
		{
			setColor(red())
			label=""
			gotCube=0
			inNest=1
		}
		else
		{
			inNest=0
		}
		
	}
	
	//Leave trail
	def drop()
	{
		ask(patchHere())
		{
			set_main()
		}
		ask(neighbors())
		{
			ask(patchHere())
			{
				set_diffused()
			}
		}
		
		
	}
	
	//See if chemical around
	def checkChem()
	{
				if((patchHere().level>0)&&(!gotCube))
		{
			nearChem=1
			uphill("level")
		}		
		else
			nearChem=0
	}
	
}