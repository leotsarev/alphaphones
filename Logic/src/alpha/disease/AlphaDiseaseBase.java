package alpha.disease;

import alpha.AlphaIM;
import alpha.chem.IChemObject;
import phones.ProcessModelBase;
import phones.SimpleDiseaseBase;
import phones.Utils;

public abstract class AlphaDiseaseBase extends SimpleDiseaseBase  {

	public AlphaDiseaseBase(ProcessModelBase model) {
		super(model);
		setStage(0);
	}
	
	public void cleanupStatus() {
		getAlphaModel().Pain.remove(getName());
		removeStatusMessage(getName());
	}

	protected void onDiseaseStart() {
		getAlphaModel().sick = true;
	}

	protected boolean shouldStartDisease() {
		return !getAlphaModel().sick;
	}

	protected void onDiseaseEnd() {
		getAlphaModel().sick = false;
	}

	protected AlphaIM getAlphaModel() {
		return (AlphaIM) model;
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
