package alpha.oxygen;

import phones.InteractionModel.*;
import phones.ProcessModelBase;

public class OutOfOxygen extends OxygenProcess {

	public OutOfOxygen(ProcessModelBase model) {
		super(model);
	}

	public Descriptor handle() {
		int oxygenPauseInMins = 1 - getAlphaModel().oxygenLevel;
		if (oxygenPauseInMins <= 0)
		{
			return null;
		}
		addStatusMessage(BREATH, "Мне тяжело дышать: сижу, отдыхаю.");
		scheduleAfterMins(new CanBreathAgain(model), oxygenPauseInMins);
		return createMessage("Я закашлялся, стало тяжело дышать. Сяду, отдохну пока не полегчает.");
	}

	public String getName() {
		return "OutOfOxygen";
	}

}
