package DiscreteEventSim;

import java.util.ArrayList;
import java.util.List;

import cern.jet.random.Exponential;
import repast.simphony.context.Context;
import repast.simphony.dataLoader.ContextBuilder;
import repast.simphony.engine.environment.RunEnvironment;
import repast.simphony.engine.schedule.ISchedulableAction;
import repast.simphony.engine.schedule.ISchedule;
import repast.simphony.engine.schedule.ScheduleParameters;
import repast.simphony.parameter.Parameters;
import repast.simphony.random.RandomHelper;

/**
 * Toolkit for discrete event simulation using Repast Java.
 * 
 * The model in this example is an M/M/1 queue, which can easily be modified 
 * to a G/G/s queue with some minor adjustments.  More complex systems can be 
 * modeled with the addition of new methods.
 * 
 * @author Tim Sweda
 *  
 */

public class DESimBuilder implements ContextBuilder<Object> {

	// Declare schedule and actions
	static ISchedule schedule;
	ISchedulableAction nextArrival, nextDeparture, nextClear, nextEnd;
	
	// Declare queues, resources, random number generators, and statistics
	Queue mm1q;
	Resource mm1r;
	Exponential arrivalRNG, serviceRNG;
	int arrivals, departures;
	Stat arriveStat, departStat, lengthStat, waitStat, utilStat;
	
	// Declare queue, resource, and stat lists
	static List<Queue> qList;
	static List<Resource> rList;
	static List<Stat> sList;
	
	// Declare system parameters
	double lambda; // Mean arrival rate
	double mu; // Mean service time
	
	// Declare run parameters
	int replications; // Number of replications
	int repCounter;
	double warmup; // Warmup time for each replication
	double endTime; // Length of each replication
	
	@Override
	public Context<Object> build(Context<Object> context) {
		
		// Initialize everything
		schedule = RunEnvironment.getInstance().getCurrentSchedule();
		Parameters params = RunEnvironment.getInstance().getParameters();
		replications = (Integer)params.getValue("replications");
		warmup = (Double)params.getValue("warmup");
		endTime = (Double)params.getValue("endTime");
		lambda = (Double)params.getValue("lambda");
		mu = (Double)params.getValue("mu");
		
		arrivalRNG = RandomHelper.createExponential(lambda);
		serviceRNG = RandomHelper.createExponential(mu);
		
		qList = new ArrayList<Queue>();
		rList = new ArrayList<Resource>();
		sList = new ArrayList<Stat>();
		
		mm1q = new Queue("FIFO");
		mm1r = new Resource(1);
		
		// Global statistics (not part of slist)
		arriveStat = new Stat();
		departStat = new Stat();
		lengthStat = new Stat();
		waitStat = new Stat();
		utilStat = new Stat();
		
		repCounter = 0; // Keep track of current replication
		
		// Schedule first event
		schedule.schedule(ScheduleParameters.createOneTime(0), this, "initialize");
		
		return context;
	}
	
	// Initialize system before each replication
	public void initialize() {
		repCounter++;
		arrivals = 0;
		departures = 0;
		for (Queue q:qList)
			q.clear();
		for (Resource r:rList)
			r.reset();
		for (Stat s:sList)
			s.initialize();
		
		double firstArrival = schedule.getTickCount()+arrivalRNG.nextDouble();
		nextArrival = schedule.schedule(ScheduleParameters.createOneTime(firstArrival, 1), this, "arrive");
		nextClear = schedule.schedule(ScheduleParameters.createOneTime(schedule.getTickCount()+warmup, ScheduleParameters.LAST_PRIORITY), this, "clearStats");
		nextEnd = schedule.schedule(ScheduleParameters.createOneTime(schedule.getTickCount()+endTime, ScheduleParameters.LAST_PRIORITY), this, "end");
	}
	
	// Arrival event
	public void arrive() {
		arrivals++;
		Entity e = new Entity();
		if (mm1r.isAvailable(1)) {
			mm1q.skip();
			mm1r.seize(1);
			double departTime = schedule.getTickCount()+serviceRNG.nextDouble();
			if (departTime < nextEnd.getNextTime()) // This conditional (and others like it) can be removed once removeAction is implemented
				nextDeparture = schedule.schedule(ScheduleParameters.createOneTime(departTime, 1), this, "depart", e);
		}
		else
			mm1q.enqueue(e);
		double arriveTime = schedule.getTickCount()+arrivalRNG.nextDouble();
		if (arriveTime < nextEnd.getNextTime())
			nextArrival = schedule.schedule(ScheduleParameters.createOneTime(arriveTime, 1), this, "arrive");
	}
	
	// End of service event
	public void depart(Entity e) {
		Entity next = mm1q.pop();
		if (next != null) {
			double departTime = schedule.getTickCount()+serviceRNG.nextDouble();
			if (departTime < nextEnd.getNextTime())
				nextDeparture = schedule.schedule(ScheduleParameters.createOneTime(departTime, 1), this, "depart", next);
		}
		else
			mm1r.release(1);
		departures++;
	}
	
	// Clear statistics after warmup period
	public void clearStats() {
		for (Stat s:sList)
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
		if (repCounter < replications)
			initialize();
		else {
			System.out.println("Mean arrival rate:  "+arriveStat.getAverage()/(endTime-warmup));
			System.out.println("Mean service rate:  "+departStat.getAverage()/(endTime-warmup));
			System.out.println("Mean queue length:  "+lengthStat.getAverage());
			System.out.println("Mean waiting time in queue:  "+waitStat.getAverage());
			System.out.println("Mean resource utilization:  "+utilStat.getAverage());
		}
	}
	
	// Save statistics from current replication
	public void recordGlobalStats() {
		arriveStat.recordDT(arrivals);
		departStat.recordDT(departures);
		lengthStat.recordDT(sList.get(0).getAverage());
		waitStat.recordDT(sList.get(1).getAverage());
		utilStat.recordDT(sList.get(2).getAverage());
	}
	
	// Print statistics to console as comma-separated list
	public void printStats() {
		System.out.print(arrivals+","+departures);
		for (Stat s:sList)
			System.out.print(","+s.getAverage());
		System.out.println();
	}
	
}