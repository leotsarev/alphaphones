package alpha.genes;

import phones.InteractionModel.Descriptor;
import phones.ProcessModelBase;

public class MasterToggleGene extends GeneActionBase {

	public MasterToggleGene(ProcessModelBase model) {
		super(model);
	}

	public Descriptor handle() {
		getTargetGene().masterToggle();
		scheduleNow(new MasterGeneMenu(model));
		return null;
	}

	public String getName() {
		return "MasterToggleGene";
	}

}
