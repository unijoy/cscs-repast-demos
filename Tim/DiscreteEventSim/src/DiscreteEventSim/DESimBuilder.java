package DiscreteEventSim;

import java.util.ArrayList;
import java.util.List;
import repast.simphony.context.Context;
import repast.simphony.dataLoader.ContextBuilder;
import repast.simphony.engine.environment.RunEnvironment;
import repast.simphony.engine.schedule.ISchedulableAction;
import repast.simphony.engine.schedule.ISchedule;
import repast.simphony.engine.schedule.ScheduleParameters;

/**
 * Toolkit for discrete event simulation using Repast's scheduler.
 * 
 * The model in this example is an M/M/1 queue, which can easily be modified 
 * to a G/G/s queue with some minor adjustments.  More complex systems can be 
 * modeled with the addition of new methods.
 * 
 * @author Tim Sweda
 *  
 */

public class DESimBuilder implements ContextBuilder<Object> {

	static ISchedule schedule;
	RNG rng;
	static List<Queue> qlist;
	static List<Resource> rlist;
	static List<Stat> slist;
	
	// Declare run parameters
	int replications = 100; // Number of replications
	int repcounter;
	double warmup = 500; // Warmup time for each replication
	double endtime = 5000; // Length of each replication
	
	// Declare queues, resources, and statistics
	Queue mm1q;
	Resource mm1r;
	int arrivals, departures;
	Stat arriveStat, departStat, lengthStat, waitStat, utilStat;
	
	// Declare actions
	ISchedulableAction nextArrival, nextDeparture, nextEnd;
	
	// Declare system parameters
	double lambda = 0.8; // Mean arrival rate
	double mu = 1; // Mean service time
	
	@Override
	public Context build(Context<Object> context) {
		
		// Initialize everything
		schedule = RunEnvironment.getInstance().getCurrentSchedule();
		rng = new RNG();
		qlist = new ArrayList<Queue>();
		rlist = new ArrayList<Resource>();
		slist = new ArrayList<Stat>();
		
		mm1q = new Queue("FIFO");
		mm1r = new Resource(1);
		
		// Global statistics (not part of slist)
		arriveStat = new Stat();
		departStat = new Stat();
		lengthStat = new Stat();
		waitStat = new Stat();
		utilStat = new Stat();
		
		repcounter = 0;
		
		// Schedule first event
		schedule.schedule(ScheduleParameters.createOneTime(0), this, "initialize");
		
		return context;
	}
	
	// Initialize system before each replication
	public void initialize() {
		repcounter++;
		arrivals = 0;
		departures = 0;
		for (Queue q:qlist)
			q.clear();
		for (Resource r:rlist)
			r.reset();
		for (Stat s:slist)
			s.initialize();
		
		double firstarrival = schedule.getTickCount()+rng.nextExponential(lambda);
		nextArrival = schedule.schedule(ScheduleParameters.createOneTime(firstarrival, 1), this, "arrive");
		schedule.schedule(ScheduleParameters.createOneTime(schedule.getTickCount()+warmup, ScheduleParameters.LAST_PRIORITY), this, "clearStats");
		nextEnd = schedule.schedule(ScheduleParameters.createOneTime(schedule.getTickCount()+endtime, ScheduleParameters.LAST_PRIORITY), this, "end");
	}
	
	// Arrival event
	public void arrive() {
		arrivals++;
		Entity e = new Entity();
		if (mm1r.isAvailable(1)) {
			mm1q.skip();
			mm1r.seize(1);
			double departtime = schedule.getTickCount()+rng.nextExponential(mu);
			if (departtime < nextEnd.getNextTime()) // This conditional (and others like it) can be removed once removeAction is implemented
				nextDeparture = schedule.schedule(ScheduleParameters.createOneTime(departtime, 1), this, "depart", e);
		}
		else
			mm1q.enqueue(e);
		double arrivetime = schedule.getTickCount()+rng.nextExponential(lambda);
		if (arrivetime < nextEnd.getNextTime())
			nextArrival = schedule.schedule(ScheduleParameters.createOneTime(arrivetime, 1), this, "arrive");
	}
	
	// End of service event
	public void depart(Entity e) {
		Entity next = mm1q.pop();
		if (next != null) {
			double departtime = schedule.getTickCount()+rng.nextExponential(mu);
			if (departtime < nextEnd.getNextTime())
				nextDeparture = schedule.schedule(ScheduleParameters.createOneTime(departtime, 1), this, "depart", next);
		}
		else
			mm1r.release(1);
		departures++;
	}
	
	// Clear statistics after warmup period
	public void clearStats() {
		for (Stat s:slist)
			s.initialize();
		arrivals = 0;
		departures = 0;
	}
	
	// Record and print statistics after each replication; reset system for next replication
	public void end() {
		/*
		 * To be added when removeAction bug is fixed
		if (nextarrival.getNextTime() > schedule.getTickCount())
			schedule.removeAction(nextarrival);
		if (nextdeparture.getNextTime() > schedule.getTickCount())
			schedule.removeAction(nextdeparture);
		*/
		recordGlobalStats();
		printStats();
		if (repcounter < replications)
			initialize();
		else {
			System.out.println("Mean arrival rate:  "+arriveStat.getAverage()/(endtime-warmup));
			System.out.println("Mean service rate:  "+departStat.getAverage()/(endtime-warmup));
			System.out.println("Mean queue length:  "+lengthStat.getAverage());
			System.out.println("Mean waiting time in queue:  "+waitStat.getAverage());
			System.out.println("Mean resource utilization:  "+utilStat.getAverage());
		}
	}
	
	// Save statistics from current replication
	public void recordGlobalStats() {
		arriveStat.recordDT(arrivals);
		departStat.recordDT(departures);
		lengthStat.recordDT(slist.get(0).getAverage());
		waitStat.recordDT(slist.get(1).getAverage());
		utilStat.recordDT(slist.get(2).getAverage());
	}
	
	// Print statistics to console as comma-separated list
	public void printStats() {
		System.out.print(arrivals+","+departures);
		for (Stat s:slist)
			System.out.print(","+s.getAverage());
		System.out.println();
	}
	
}