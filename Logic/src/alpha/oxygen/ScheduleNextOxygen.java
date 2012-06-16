package alpha.oxygen;

import alpha.AlphaProcess;
import phones.InteractionModel.Descriptor;
import phones.ProcessModelBase;

public class ScheduleNextOxygen extends AlphaProcess {

	public ScheduleNextOxygen(ProcessModelBase model) {
		super(model);
	}

	public Descriptor handle() {
		int oxygenPauseInMins =  (int) Math.floor((10 + getAlphaModel().oxygenLevel * 5) * (Math.random() + 1));
		scheduleAfterMins(new OutOfOxygen(model), oxygenPauseInMins);
		return null;
	}

	public String getName() {
		return "ScheduleNextOxygen";
	}

}
