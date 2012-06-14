package alpha;

import phones.InteractionModel.*;
import phones.ProcessModelBase;

public class OutOfOxygen extends OxygenProcess {

	public OutOfOxygen(ProcessModelBase model) {
		super(model);
	}

	public Descriptor handle() {
		int oxygenPauseInMins = 3 - getAlphaModel().oxygenLevel;
		if (oxygenPauseInMins <= 0)
		{
			return null;
		}
		addStatusMessage(BREATH, "Тяжело дышать: надо сидеть");
		scheduleAfterMins(new CanBreathAgain(model), oxygenPauseInMins);
		return createMessage("Вам стало тяжело дышать, не хватает воздуха. Присядьте, отдохните пару минут.");
	}

	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

}
