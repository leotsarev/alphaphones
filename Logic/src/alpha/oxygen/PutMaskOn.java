package alpha.oxygen;

import alpha.AlphaProcess;
import phones.InteractionModel.Descriptor;
import phones.ProcessModelBase;

public class PutMaskOn extends AlphaProcess {

	public PutMaskOn(ProcessModelBase model) {
		super(model);
	}

	public Descriptor handle() {
		addStatusMessage("mask", "В защ. костюме");
		if (getAlphaModel().wearingMask)
		{
			return createChangeStatusMessage("Я уже в костюме.");
		}
		getAlphaModel().wearingMask = true;
		scheduleNow(new ScheduleNextOxygen(model));
		return createChangeStatusMessage("Одеваю защитный костюм...");
	}

	public String getName() {
		return "PutMaskOn";
	}

}
