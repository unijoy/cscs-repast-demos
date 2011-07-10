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
 * TreeBuilder
 * @author grace
 *
 */
public class TreeBuilder implements ContextBuilder<Tree> {

	@Override
	public Context<Tree> build(Context<Tree> context) {
		
		// get environment variables from GUI
		Parameters p = RunEnvironment.getInstance().getParameters();
		// grid width and height
		int gridWidth = (Integer)p.getValue("gridWidth");
		int gridHeight = (Integer)p.getValue("gridHeight");
		// initial tree count
		int treeCount = (Integer)p.getValue("initialTrees");
		// a tree's visibility of neighbors
		int neighborSize = (Integer)p.getValue("neighborSize");
		// burn probability for all neighboring trees
		float probAll = (Float)p.getValue("probAll");
		// percent of neighboring trees to set probability
		double percBurn = (Double)p.getValue("percBurn");
		// directed burn probability; works only if probAll=0
		HashMap<String, Float> directedProb = new HashMap<String, Float>();
		directedProb.put("probE", (Float)p.getValue("probE"));
		directedProb.put("probN", (Float)p.getValue("probN"));
		directedProb.put("probNE", (Float)p.getValue("probNE"));
		directedProb.put("probNW", (Float)p.getValue("probNW"));
		directedProb.put("probS", (Float)p.getValue("probS"));
		directedProb.put("probSE", (Float)p.getValue("probSE"));
		directedProb.put("probSW", (Float)p.getValue("probSW"));
		directedProb.put("probW", (Float)p.getValue("probW"));
		// wind strength: strong, medium, weak
		String windStrength = (String)p.getValue("windStrength");
		String windDir = (String)p.getValue("windDir");
		 
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
		
		// Create grid
		Grid<Tree> grid = GridFactoryFinder.createGridFactory(null).createGrid("Grid", 
				context, new GridBuilderParameters<Tree>(new WrapAroundBorders(), 
				new RandomGridAdder<Tree>(), false, gridWidth, gridHeight));

		// Create a value layer
		GridValueLayer valueLayer = new GridValueLayer("State", true,
				new WrapAroundBorders(), gridWidth, gridHeight);

		// Add the value layers to the context 
		context.addValueLayer(valueLayer);

		// Create an initial burning tree at center 
 		for (int j=gridHeight/2; j<gridHeight/2+1; j++){
			for (int i=gridWidth/2; i<gridWidth/2+1; i++){
				Tree t = new Tree(grid, space, neighborSize, probAll, percBurn,
						directedProb, windStrength, windDir);
				context.add(t);
				grid.moveTo(t, i, j);
				ScheduleParameters scheduleParams = ScheduleParameters.createOneTime(0);
				//Tree t = grid.getObjectAt(i,j);
				RunEnvironment.getInstance().getCurrentSchedule().schedule(scheduleParams, 
						t, "setState", Tree.TreeState.BURN);
			}
		}

		// Create specified number of trees and add to context
		// and to the grid as placed randomly by the 
		// RandomCartesianAdder of the space
		for (int i = 0; i < treeCount; ++i) {
			Tree t = new Tree(grid, space, neighborSize, probAll, percBurn, 
					directedProb, windStrength, windDir);
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
