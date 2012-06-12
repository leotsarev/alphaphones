package alpha;

import phones.InteractionModel.*;
import phones.ProcessModelBase;

public class OutOfOxygen extends OxygenProcess {

	public OutOfOxygen(ProcessModelBase model) {
		super(model);
	}

	public Descriptor handle() {
		addStatusMessage(BREATH, "Тяжело дышать: надо сидеть");
		scheduleAfterMins(new CanBreathAgain(model), 2);
		return createMessage("Вам стало тяжело дышать, не хватает воздуха. Присядьте, отдохните пару минут.");
	}

	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

}
