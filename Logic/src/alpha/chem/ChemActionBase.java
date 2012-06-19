package alpha.chem;

import phones.IPrefixHandler;
import phones.ProcessModelBase;
import phones.Utils;
import alpha.AlphaProcess;

public abstract class ChemActionBase extends AlphaProcess implements IPrefixHandler {

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
		setChemName(suffix);
	}

	private void setChemName(String chemName) {
		setStringArg("chemName", chemName);
	}

	protected final IChemObject getChemObj() {
		IChemObject targetGene = getAlphaModel().Chemistry.getByName(getChemName());
		Utils.assert_(targetGene != null, "Can't find gen:" + getChemName());
		return targetGene;
	}

	protected final String getChemName() {
		return getStringArg("chemName");
	}

	protected final void setChemObj(IChemObject obj) {
		setStringArg("chemName", obj.getName());
	}

	public void setTargetChemObj(IChemObject chemObj) {
		setChemName(chemObj.getName());
	}

}
