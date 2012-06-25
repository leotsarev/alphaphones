package phones;

public class Event {
	public Event(IProcess pr, int timeKey) {
		process = pr;
		time = timeKey;
	}
	public final int time;
	public final IProcess process;
	
	public String toString() {
		return time + ":" +  process.toString();
	}
}
