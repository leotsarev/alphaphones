package alpha;

import phones.InteractionModel.Descriptor;
import phones.ProcessModelBase;

public class DisableRad extends AlphaProcess {

	public DisableRad(ProcessModelBase model) {
		super(model);
	}

	public Descriptor handle() {
		getAlphaModel().radEnabled = false;
		return createChangeStatusMessage("Ничего не случилось");
	}

	public String getName() {
		return "DisableRad";
	}

}
