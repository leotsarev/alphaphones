package alpha.oxygen;

import alpha.AlphaProcess;
import phones.InteractionModel.Descriptor;
import phones.ProcessModelBase;

public class PutMaskOn extends AlphaProcess {

	public PutMaskOn(ProcessModelBase model) {
		super(model);
	}

	public Descriptor handle() {
		addStatusMessage("mask", "В маске");
		if (getAlphaModel().wearingMask)
		{
			return createMessage("Я уже в маске.");
		}
		getAlphaModel().wearingMask = true;
		scheduleNow(new ScheduleNextOxygen(model));
		return createMessage("Одеваю маску...");
	}

	public String getName() {
		return "PutMaskOn";
	}

}
