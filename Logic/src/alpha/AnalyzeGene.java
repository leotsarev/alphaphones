package alpha;

import phones.InteractionModel.Descriptor;
import phones.ProcessModelBase;

public class AnalyzeGene extends GeneActionBase {

	public AnalyzeGene(ProcessModelBase model) {
		super(model);
	}

	public Descriptor handle() {
		return createMessage("Ген: " + getTargetGene().getAnalyzeResult());
	}

	public String getName() {
		return "AnalyzeGene";
	}

}
