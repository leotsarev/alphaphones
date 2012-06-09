package alpha;

import phones.InteractionModel.Descriptor;
import phones.ProcessModelBase;
import phones.ProcessModelBase.Process;

public class ArmWound extends Process {

	public ArmWound(ProcessModelBase model, int location) {
		super(model);
		setIntArg("location", location);
	}

	public Descriptor handle() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getName() {
		return "ArmWound";
	}

}
