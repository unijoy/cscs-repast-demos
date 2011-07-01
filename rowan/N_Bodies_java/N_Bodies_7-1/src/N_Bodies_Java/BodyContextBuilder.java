package N_Bodies_Java;
import repast.simphony.context.Context;
import repast.simphony.context.space.grid.GridFactoryFinder;
import repast.simphony.dataLoader.ContextBuilder;
import repast.simphony.space.grid.Grid;
import repast.simphony.space.grid.GridBuilderParameters;
import repast.simphony.space.grid.RandomGridAdder;
import repast.simphony.space.grid.StickyBorders;
public class BodyContextBuilder implements ContextBuilder<Body>{

	public Context build(Context<Body> context) {
		// TODO Auto-generated method stub
		Grid<Body> grid = GridFactoryFinder.createGridFactory(null).createGrid
			("Grid", context, new GridBuilderParameters<Body>
			(new StickyBorders(),new RandomGridAdder<Body>(),false,16,16));
		for(int i = 0; i < 2; i++){
			Body body = new Body();
			context.add(body);
			grid.moveTo(body, i, i);//this is very temporary
		}
		return context;
	}

}
