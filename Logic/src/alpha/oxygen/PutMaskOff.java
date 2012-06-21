package alpha.oxygen;

import alpha.AlphaProcess;
import phones.InteractionModel.Descriptor;
import phones.ProcessModelBase;

public class PutMaskOff extends AlphaProcess {

	public PutMaskOff(ProcessModelBase model) {
		super(model);
	}

	public Descriptor handle() {
		removeStatusMessage("mask");
		if (!getAlphaModel().wearingMask)
		{
			return createChangeStatusMessage("Я без костюма, что снимать?");
		}
		getAlphaModel().wearingMask = false;
		scheduleNow(new ScheduleNextOxygen(model));
		return createChangeStatusMessage("Снимаю костюм...");
	}

	public String getName() {
		return "PutMaskOff";
	}

}
