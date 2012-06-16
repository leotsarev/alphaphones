package alpha.wounds;

import alpha.AlphaProcess;
import phones.InteractionModel.Descriptor;
import phones.ProcessModelBase;

public class ArmWound extends AlphaProcess {

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
