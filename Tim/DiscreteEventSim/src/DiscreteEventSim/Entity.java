package DiscreteEventSim;

/**
 * @author Tim Sweda
 * 
 */

public class Entity {
	
	private double priority; // Priority of entity (important if using priority queues)
	private double timestamp; // Useful for calculating duration of an activity (such as waiting in a queue)
	
	public Entity() {
		priority = 0;
	}
	
	public Entity(double pr) {
		setPriority(pr);
	}
	
	public void setPriority(double pr) {
		priority = pr;
	}
	
	public double getPriority() {
		return priority;
	}
	
	public void stamp(double time) {
		timestamp = time;
	}
	
	public double read() {
		return timestamp;
	}

}