package alpha.genes;

import alpha.AlphaProcess;
import phones.IPrefixHandler;
import phones.ProcessModelBase;
import phones.Utils;

public abstract class GeneActionBase extends  AlphaProcess implements IPrefixHandler {

	public GeneActionBase(ProcessModelBase model) {
		super(model);
	}

	public boolean isStartOfSuffix(String suffix) {
		return suffix == "";
	}

	public boolean isValidSuffix(String suffix) {
		return Gene.isValidGeneName(suffix);
	}

	public void setSuffixValue(String suffix) {
		Utils.assert_(isValidSuffix(suffix));
		setStringArg("gene", suffix);
	}

	protected Gene getTargetGene() {
		return getAlphaModel().Genes.get(getStringArg("gene"));
	}

}
