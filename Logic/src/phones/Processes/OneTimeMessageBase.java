package phones.Processes;

import phones.InteractionModel.Descriptor;
import phones.InteractionModel.SleepDescriptor;
import phones.ProcessModelBase;
import phones.ProcessModelBase.Process;

public abstract class OneTimeMessageBase extends Process {
	public OneTimeMessageBase(ProcessModelBase model, String message) {
		super(model);
		setStringArg("message", message);
	}

	public Descriptor handle() {
		SleepDescriptor result = new SleepDescriptor();
		result.status = getStringArg("message");
		result.timeout = 0;
		return result;
	}

}
