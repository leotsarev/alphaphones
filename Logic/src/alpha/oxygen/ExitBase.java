package alpha.oxygen;

import phones.InteractionModel.Descriptor;
import phones.ProcessModelBase;

public class ExitBase extends alpha.AlphaProcess{

	public ExitBase(ProcessModelBase model) {
		super(model);
	}

	public Descriptor handle() {
		addStatusMessage("out", "Снаружи поселения");
		if (!getAlphaModel().inHouse)
		{
			return createMessage("Я уже снаружи!");
		}
		getAlphaModel().inHouse = false;
		scheduleNow(new ScheduleNextOxygen(model));
		return createMessage("Покидаю поселение...");
	}

	public String getName() {
		return "ExitBase";
	}

}
