package phones;

import phones.InteractionModel.*;


public abstract class OneTimeMessageBase extends ProcessModelBase.Process {
	public OneTimeMessageBase(ProcessModelBase model, String message) {
		super(model);
		setStringArg("message", message);
	}

	public Descriptor handle() {
		return createMessage(getStringArg("message"));
	}

}
