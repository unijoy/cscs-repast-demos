package Boids_java;

import repast.simphony.context.Context;
import repast.simphony.context.space.continuous.ContinuousSpaceFactory;
import repast.simphony.context.space.continuous.ContinuousSpaceFactoryFinder;
import repast.simphony.context.space.grid.GridFactoryFinder;
import repast.simphony.dataLoader.ContextBuilder;
import repast.simphony.engine.environment.RunEnvironment;
import repast.simphony.engine.schedule.ScheduledMethod;
import repast.simphony.parameter.Parameters;
import repast.simphony.relogo.Utility;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.continuous.NdPoint;
import repast.simphony.space.continuous.RandomCartesianAdder;
import repast.simphony.space.grid.Grid;
import repast.simphony.space.grid.GridBuilderParameters;
import repast.simphony.space.grid.RandomGridAdder;
import repast.simphony.space.grid.SimpleGridAdder;
import repast.simphony.space.grid.WrapAroundBorders;

public class BoidsContextBuilder implements ContextBuilder<Object>{
	
	public Context build(Context<Object> context) {
		context.setId("Boids_java");
		Parameters p = RunEnvironment.getInstance().getParameters();
		//get all values from Parameters that the user can change
//		int gridWidth = (Integer) p.getValue("gridWidth");
//		int numBoids = (Integer) p.getValue("numBoids"); //don't know if this'll work...
		int gridWidth = 16;
		int numBoids = 20;
		
		ContinuousSpaceFactory spaceFactory = ContinuousSpaceFactoryFinder.createContinuousSpaceFactory(null);
		ContinuousSpace<Object> space = spaceFactory.createContinuousSpace("space",context,
				new RandomCartesianAdder<Object>(), 
				new repast.simphony.space.continuous.WrapAroundBorders(),
				gridWidth,gridWidth);
		
		//...do a grid, for now
		Grid<Object> grid = GridFactoryFinder.createGridFactory(null).createGrid
		   ("Grid", context, new GridBuilderParameters<Object>(
				   new WrapAroundBorders(),
				   new SimpleGridAdder<Object>(),true,
				   gridWidth,gridWidth));
		
		for(int i = 0; i < numBoids; i++){
			Boid boid = new Boid(space, grid);
			context.add(boid);
			NdPoint pt = space.getLocation(boid);
			grid.moveTo(boid, (int)pt.getX(), (int)pt.getY());
			//might as well add the Boids to the Grid and Space here...
			//start: RandomCartesianAdder and SimpleGridAdder. but how to use?
		}
		return context;
	}
	
	@ScheduledMethod(start = 1, interval = 1)
	public static void sleep(){
		try {
			Thread.sleep(50);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
