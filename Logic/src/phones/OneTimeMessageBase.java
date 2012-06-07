package phones;

import phones.InteractionModel.*;


public abstract class OneTimeMessageBase extends ProcessModelBase.Process {
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
