package rebellion;

import repast.simphony.context.Context;
import repast.simphony.engine.schedule.ScheduleParameters;
import repast.simphony.engine.schedule.ScheduledMethod;
import repast.simphony.util.ContextUtils;

public class CoverageCounter {

	public double pCount;
	public double pActiveCount;
	public double cCount;
	public double pJailedCount;
	public double pQuietCount;
	
	@ScheduledMethod(start=1, interval=1, priority=ScheduleParameters.LAST_PRIORITY)
	public void step(){
		pCount = 0;
		pActiveCount = 0;
		cCount = 0;
		pJailedCount = 0;
		pQuietCount = 0;
		updateCount();
	}
	
	public void updateCount(){
		Context context = ContextUtils.getContext(this);
		//Grid grid = (Grid)context.getProjection(Constants.CONTEXT_ID);
		
		Object[] objs = context.toArray();
		
		for (Object obj : objs) {
			if (obj instanceof Person) {
				pCount++;
				if (((Person) obj).active==true)
					pActiveCount++; // active count
				else if (((Person) obj).jailTerm > 0)
					pJailedCount++; // jailed
				else
					pQuietCount++; // quiet
			} else if (obj instanceof Cop) {
				cCount++;
			}
		}
		System.out.println("people "+pCount+" active "+pActiveCount+
				" jailed "+pJailedCount+" quiet "+pQuietCount+
				" total "+(pActiveCount+pJailedCount+pQuietCount));
		
	}
	
	public double getActivePeopleCount() {
		return pActiveCount;
	}
	
	public double getJailedPeopleCount() {
		return pJailedCount;
	}
	
	public double getQuietPeopleCount() {
		return pQuietCount;
	}
}
