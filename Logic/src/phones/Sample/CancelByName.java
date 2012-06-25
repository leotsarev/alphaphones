package phones.Sample;

import phones.InteractionModel.Descriptor;
import phones.ProcessModelBase;
import phones.ProcessModelBase.Process;

public class CancelByName extends Process {

	public CancelByName(ProcessModelBase model)
	{
		super(model);
	}
	
	public CancelByName(ProcessModelBase model, String target)
	{
		super(model);
		setStringArg("target", target);
	}
	
	public Descriptor handle() {
		unscheduleByName(getStringArg("target"));
		return null;
	}

	public String getName() {
		return "CancelByName";
	}

}
