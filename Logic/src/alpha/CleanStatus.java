package alpha;

import phones.InteractionModel.Descriptor;
import phones.ProcessModelBase;

public class CleanStatus extends AlphaProcess {

	public CleanStatus(ProcessModelBase model) {
		super(model);
	}

	public Descriptor handle() {
		model.cleanStatus();
		getAlphaModel().updateCurrentFaction();
		return null;
	}

	public String getName() {
		return "CleanStatus";
	}

}
