package phones.Processes;

import phones.InteractionModel.Descriptor;
import phones.InteractionModel.SleepDescriptor;
import phones.ProcessModelBase;

public class BreathHardly extends ProcessModelBase.Process{

	public BreathHardly(ProcessModelBase model) {
		super(model);
	}

	public Descriptor handle() {
		SleepDescriptor result = new SleepDescriptor();
		result.saveRequired = true;
		result.status = "Немного не хватает воздуха. Вы тяжело дышите.";
		result.timeout = 1;
		rescheduleAgain();
		return result;
	}

	public String getName() {
		return "BreathHardly";
	}

}
