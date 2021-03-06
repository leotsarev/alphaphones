package alpha.sleep;

import alpha.AlphaProcess;
import phones.InteractionModel.Descriptor;
import phones.ProcessModelBase;

public class Awake extends AlphaProcess {

	public Awake(ProcessModelBase model) {
		super(model);
	}

	public Descriptor handle() {
		getAlphaModel().sleeping = false;
		removeStatusMessage("sleep");
		return createChangeStatusMessage("Я проснул{ся/ась}");
	}

	public String getName() {
		return "Awake";
	}

}
