package alpha.disease;

import phones.ProcessModelBase;

public abstract class EarthDiseaseBase extends AlphaDiseaseBase {

	public EarthDiseaseBase(ProcessModelBase model) {
		super(model);
	}

	protected int getMaxStage() {
		return 4;
	}
	protected boolean shouldStartDisease() {
		return getAlphaModel().Chemistry.getImmunityValue() < 2; 
	}

	protected boolean shouldHeal() {
		return getStage() == 2 && getAlphaModel().Chemistry.getImmunityValue() > -1;
	}

}
