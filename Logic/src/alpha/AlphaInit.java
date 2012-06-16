package alpha;

import phones.InteractionModel.Descriptor;
import phones.ProcessModelBase;

public class AlphaInit extends AlphaProcess {

	public AlphaInit(ProcessModelBase model) {
		super(model);
	}

	public Descriptor handle() {
		getAlphaModel().initCompleted = true;
		scheduleNow(new ScheduleNextOxygen(model));
		return null;
	}

	public String getName() {
		return "AlphaInit";
	}

}
