package alpha;

import phones.IPrefixHandler;
import phones.InteractionModel.Descriptor;
import phones.ProcessModelBase;
import phones.Utils;

public class MasterToggleGene extends AlphaProcess implements IPrefixHandler {

	public MasterToggleGene(ProcessModelBase model) {
		super(model);
	}

	public Descriptor handle() {
		getAlphaModel().Genes.getGene(getStringArg("gene")).toggle();
		scheduleNow(new MasterGeneMenu(model));
		return null;
	}

	public String getName() {
		return "MasterToggleGene";
	}

	public void setSuffixValue(String suffix) {
		Utils.assert_(isValidSuffix(suffix));
		setStringArg("gene", suffix);
	}

	public boolean isValidSuffix(String suffix) {
		return getAlphaModel().Genes.isValidGeneName(suffix);
	}

	public boolean isStartOfSuffix(String suffix) {
		return suffix == "";
	}

}
