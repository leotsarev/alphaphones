package alpha.genes;

import phones.InteractionModel.Descriptor;
import phones.ProcessModelBase;
import alpha.ThreeDigitCodeBase;

public class ChangeGene extends ThreeDigitCodeBase {

	public ChangeGene(ProcessModelBase model) {
		super(model);
	}

	public Descriptor handle() {
		int gene = getCode() / 3;
		int value = getCode() % 3;
		getAlphaModel().Chemistry.getGeneArray()[gene-1].setValue(value);
		return null;
	}

	public String getName() {
		return "ChangeGene";
	}

}
