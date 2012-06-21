package alpha.chem;

import phones.IPrefixHandler;
import phones.ProcessModelBase;
import phones.Utils;
import alpha.AlphaProcess;

public abstract class ChemActionBase extends AlphaProcess implements IPrefixHandler, IChemAction {

	public ChemActionBase(ProcessModelBase model) {
		super(model);
	}

	public final boolean isStartOfSuffix(String suffix) {
		return getAlphaModel().Chemistry.isCorrectNamePrefix(suffix);
	}

	public final boolean isValidSuffix(String suffix) {
		return getAlphaModel().Chemistry.getByName(suffix) != null;
	}

	public final void setSuffixValue(String suffix) {
		Utils.assert_(isValidSuffix(suffix));
		setChemObj(suffix);
	}

	public void setChem(String chemName) {
		setChemObj(chemName);
	}

	public void setChem(IChemObject chemObj) {
		setChemObj(chemObj);
	}
}
