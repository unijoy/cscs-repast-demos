/**
 * 
 */
package Fire;

import java.util.HashMap;

import repast.simphony.context.Context;
import repast.simphony.context.space.continuous.ContinuousSpaceFactory;
import repast.simphony.context.space.continuous.ContinuousSpaceFactoryFinder;
import repast.simphony.context.space.grid.GridFactoryFinder;
import repast.simphony.dataLoader.ContextBuilder;
import repast.simphony.engine.environment.RunEnvironment;
import repast.simphony.engine.schedule.ScheduleParameters;
import repast.simphony.parameter.Parameters;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.continuous.NdPoint;
import repast.simphony.space.continuous.RandomCartesianAdder;
import repast.simphony.space.grid.Grid;
import repast.simphony.space.grid.GridBuilderParameters;
import repast.simphony.space.grid.RandomGridAdder;
import repast.simphony.space.grid.WrapAroundBorders;
import repast.simphony.valueLayer.GridValueLayer;

/**
 * @author grace
 *
 */
public class TreeBuilder implements ContextBuilder<Tree> {

	@Override
	public Context<Tree> build(Context<Tree> context) {
		
		// get environment variables
		Parameters p = RunEnvironment.getInstance().getParameters();
		int gridWidth = (Integer)p.getValue("gridWidth");
		int gridHeight = (Integer)p.getValue("gridHeight");
		int treeCount = (Integer)p.getValue("initialTrees");
		int neighborSize = (Integer)p.getValue("neighborSize");
		float probAll = (Float)p.getValue("probAll");
		HashMap<String, Float> directedProb = new HashMap<String, Float>();
		directedProb.put("probE", (Float)p.getValue("probE"));
		directedProb.put("probN", (Float)p.getValue("probN"));
		directedProb.put("probNE", (Float)p.getValue("probNE"));
		directedProb.put("probNW", (Float)p.getValue("probNW"));
		directedProb.put("probS", (Float)p.getValue("probS"));
		directedProb.put("probSE", (Float)p.getValue("probSE"));
		directedProb.put("probSW", (Float)p.getValue("probSW"));
		directedProb.put("probW", (Float)p.getValue("probW"));
		HashMap<String, String> windProb = new HashMap<String, String>();
		windProb.put("windE", (String) p.getValue("windE"));
		windProb.put("windN", (String) p.getValue("windN"));
		windProb.put("windNE", (String) p.getValue("windNE"));
		windProb.put("windNW", (String)p.getValue("windNW"));
		windProb.put("windS", (String)p.getValue("windS"));
		windProb.put("windSE", (String)p.getValue("windSE"));
		windProb.put("windSW", (String)p.getValue("windSW"));
		windProb.put("windW", (String)p.getValue("windW"));
		boolean useProbAll = true;
		 
		
		// Create a space with random positioning with the specified
		// dimensions
		ContinuousSpaceFactory spaceFactory =
			ContinuousSpaceFactoryFinder . createContinuousSpaceFactory ( null );
		ContinuousSpace<Tree> space = spaceFactory
				.createContinuousSpace(
						"Space",
						context,
						new RandomCartesianAdder<Tree>(),
						new repast.simphony.space.continuous.WrapAroundBorders(),
						gridWidth, gridHeight);
		
		// Create the grid for the CAs
		Grid<Tree> grid = GridFactoryFinder.createGridFactory(null).createGrid("Grid", 
				context, new GridBuilderParameters<Tree>(new WrapAroundBorders(), 
				new RandomGridAdder<Tree>(), false, gridWidth, gridHeight));

		// Create a value layer to store the state for each CA
		GridValueLayer valueLayer = new GridValueLayer("State", true,
				new WrapAroundBorders(), gridWidth, gridHeight);

		// Add the value layers to the context 
		context.addValueLayer(valueLayer);
		
		// create trees and add to context
		// todo: have user pick one
		// Create and place a new Tree in each grid location.
		/*
		for (int j=0; j<gridHeight; j++){
			for (int i=0; i<gridWidth; i++){
				Tree t = new Tree();
				context.add(t);
				grid.moveTo(t, i, j);
			}
		}
		*/
		
		// figure out whether to use probAll (same prob for all directions)
		// or use individual directions
		if (probAll!=0) {
			useProbAll = true;
		} else {
			useProbAll = false;
		}


		// "Seed" the the center CA.
		for (int j=gridHeight/2; j<gridHeight/2+1; j++){
			for (int i=gridWidth/2; i<gridWidth/2+1; i++){
				Tree t = new Tree(grid, space, neighborSize, probAll, directedProb, windProb);
				context.add(t);
				grid.moveTo(t, i, j);
				ScheduleParameters scheduleParams = ScheduleParameters.createOneTime(0);
				//Tree t = grid.getObjectAt(i,j);
				RunEnvironment.getInstance().getCurrentSchedule().schedule(scheduleParams, 
						t, "setState", Tree.TreeState.BURN);
			}
		}

		// Create specified num of trees and add to context
		// and to the grid as placed randomly by the 
		// RandomCartesianAdder of the space
		for (int i = 0; i < treeCount; ++i) {
			Tree t = new Tree(grid, space, neighborSize, probAll, directedProb, windProb);
			context.add(t);
			NdPoint pt = space.getLocation(t);
			grid.moveTo(t, (int) pt.getX(), (int) pt.getY());
			ScheduleParameters scheduleParams = ScheduleParameters.createOneTime(0);
			RunEnvironment.getInstance().getCurrentSchedule().schedule(scheduleParams, 
					t, "setState", Tree.TreeState.FRESH);
		}
		
		//context.add(new CoverageCounter());
		
		return context;
	}

}
