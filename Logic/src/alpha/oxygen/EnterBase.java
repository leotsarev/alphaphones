package alpha.oxygen;

import phones.InteractionModel.Descriptor;
import phones.ProcessModelBase;
import alpha.AlphaProcess;

public class EnterBase extends AlphaProcess {

	public EnterBase(ProcessModelBase model) {
		super(model);
	}

	public Descriptor handle() {
		addStatusMessage("out", "В поселении");
		if (getAlphaModel().inHouse)
		{
			return createMessage("Я уже в поселении.");
		}
		getAlphaModel().inHouse = true;
		scheduleNow(new ScheduleNextOxygen(model));
		return createChangeStatusMessage("Вхожу в поселение...");
	}

	public String getName() {
		return "EnterBase";
	}

}
