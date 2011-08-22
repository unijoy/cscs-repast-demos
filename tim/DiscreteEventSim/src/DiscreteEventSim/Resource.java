package DiscreteEventSim;

/**
 * @author Tim Sweda
 * 
 */

public class Resource {
	
	private int numServers; // Number of servers working in parallel
	private int numBusy; // Number of servers that are busy
	private Stat utilization; // Utilization of servers
	
	public Resource(int size) {
		numServers = size;
		numBusy = 0;
		utilization = new Stat();
		DESimBuilder.rList.add(this);
		DESimBuilder.sList.add(utilization);
	}
	
	// Check if new job can begin service immediately
	public boolean isAvailable(double num) {
		return (numServers-numBusy >= num);
	}
	
	// Seize (mark as busy) servers to begin working on new job
	public boolean seize(double num) {
		if (numServers-numBusy < num)
			return false;
		else {
			utilization.recordCT(numBusy);
			numBusy += num;
			return true;
		}
	}
	
	// Release (mark as available) servers after completed job
	public boolean release(int num) {
		if (numBusy < num)
			return false;
		else {
			utilization.recordCT(numBusy);
			numBusy -= num;
			return true;
		}
	}
	
	// Reset resource, release all servers
	public void reset() {
		numBusy = 0;
	}

}