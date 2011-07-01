package N_Bodies_Java;

import repast.simphony.context.Context;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.grid.Grid;
import repast.simphony.space.grid.GridPoint;
import repast.simphony.util.ContextUtils;

public class Body {
	private double forcex;
	private double forcey;
	private double velx;
	private double vely;
	private double xcor;
	private double ycor;
	private double mass;
	private double centerOfMassXC;
	private double centerOfMassYC;
	private double G;
	
	private Context<Body> context;
	private ContinuousSpace<Body> space;
	private Grid<Body> grid;
	
	public void setup(Grid<Body> grid, ContinuousSpace<Body> space){
		//this.space = space;
		//this.grid = grid;
		//set the different x/y coordinates
		//set velx and vely, xcor and ycor
		G = 5.0;
	}
	
	public void step(){
		this.context = ContextUtils.getContext(this);
		this.space = (ContinuousSpace)context.getProjection("Space");
		this.grid = (Grid)context.getProjection("Grid");
		
		checkForCollisions();
		updateForce();
		updateVelocity();
		updatePosition();
		//some kinda pause which is moddable
	}
	
	public void checkForCollisions() {
		// TODO Auto-generated method stub
		GridPoint point = grid.getLocation(this);
		Iterable neighbors = grid.getObjectsAt(point.getX(),point.getY());
		if(!neighbors.iterator().hasNext()){
			die();
		}
	}
	
	public void updateForce() {
		// TODO Auto-generated method stub
		
	}
	
	public void updateVelocity() {
		// TODO Auto-generated method stub
		velx = velx + (forcex * G / mass);
		vely = vely + (forcey * G / mass);
		forcex = 0;
		forcey = 0;
	}
	
	public void updatePosition() {
		// TODO Auto-generated method stub
		xcor = xcor + velx;
		ycor = ycor + vely;
		adjustPosition();
	}
	
	public void adjustPosition(){
		space.moveTo(this, xcor, ycor);
		grid.moveTo(this, (int)xcor, (int)ycor);
		
	}
	
	public double findCenterOfMassX(){
		return 0.0;
	}
	
	public double findCenterOfMassY(){
		return 0.0;
	}
	
	public void die(){
		context.remove(this);		
	}
}
