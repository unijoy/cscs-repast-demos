package DiscreteEventSim;

/**
 * @author Tim Sweda
 * 
 */

public class Stat {
	
	private double total, totalSq, count, lastTime;
	
	public Stat() {
		initialize();
	}
	
	public void initialize() {
		total = 0;
		totalSq = 0;
		count = 0;
		lastTime = DESimBuilder.schedule.getTickCount();
	}
	
	// Record continuous-time statistic
	public void recordCT(double value) {
		double currentTime = DESimBuilder.schedule.getTickCount();
		total += value*(currentTime-lastTime);
		count += currentTime-lastTime;
		lastTime = currentTime;
	}
	
	// Record discrete-time statistic
	public void recordDT(double value) {
		total += value;
		totalSq += value*value;
		count++;
	}
	
	// Return average
	public double getAverage() {
		if (count > 0)
			return total/count;
		else
			return 0;
	}
	
	// Return standard deviation (relevant only for discrete time statistics)
	public double getStDev() {
		if (count > 0)
			return Math.sqrt(totalSq/count);
		else
			return 0;
	}

}