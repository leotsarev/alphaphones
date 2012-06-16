package alpha;

import phones.InteractionModel.Descriptor;
import phones.ProcessModelBase;

public class MasterToggleGene extends GeneActionBase {

	public MasterToggleGene(ProcessModelBase model) {
		super(model);
	}

	public Descriptor handle() {
		getTargetGene().toggle();
		scheduleNow(new MasterGeneMenu(model));
		return null;
	}

	public String getName() {
		return "MasterToggleGene";
	}

}
