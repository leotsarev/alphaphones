package alpha;

import phones.InteractionModel.Descriptor;
import phones.ProcessModelBase;
import phones.ProcessModelBase.Process;

public class AlphaInit extends Process {

	public AlphaInit(ProcessModelBase model) {
		super(model);
	}

	public Descriptor handle() {
		scheduleNow(new ScheduleNextOxygen(model));
		return null;
	}

	public String getName() {
		return "AlphaInit";
	}

}
