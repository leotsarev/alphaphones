package phones;

import phones.InteractionModel.Descriptor;
import phones.ProcessModelBase.Process;

public class DoNothingProcess extends Process {

	public DoNothingProcess(ProcessModelBase model) {
		super(model);
	}

	public Descriptor handle() {
		// NOP
		return null;
	}

	public String getName() {
		return "DoNothing";
	}

}
