package alpha;

import alpha.chem.IChemObject;
import phones.ProcessModelBase;
import phones.ProcessModelBase.Process;
import phones.Utils;

public abstract class AlphaProcess extends Process {

	public AlphaProcess(ProcessModelBase model) {
		super(model);
	}

	protected AlphaIM getAlphaModel() {
		return (AlphaIM)model;
	}

	protected String gender(String m, String f) {
		return getAlphaModel().male ? m: f;
	}

	protected final void setChemObj(String chemName) {
		setStringArg("chemName", chemName);
	}

	protected final IChemObject getChemObj() {
		String chemName = getChemName();
		Utils.assert_(chemName != null);
		IChemObject targetGene = getAlphaModel().Chemistry.getByName(chemName);
		Utils.assert_(targetGene != null, "Can't find gen:" + chemName);
		return targetGene;
	}

	protected final String getChemName() {
		return getStringArg("chemName");
	}

	protected final void setChemObj(IChemObject chemObj) {
		setChemObj(chemObj.getName());
	}

}
