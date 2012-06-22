package alpha.wounds;

import alpha.AlphaIM;
import alpha.disease.AlphaDiseaseBase;
import phones.ProcessModelBase;

public abstract class WoundBase extends AlphaDiseaseBase {

	public WoundBase(ProcessModelBase model, int location) {
		super(model);
		setIntArg("location", location);
	}

	protected int getMaxStage() {
		return 4;
	}
	
	protected int getQuantLenInMins() {
		return 20;
	}

	protected boolean shouldHeal() {
		return false;
	}

	protected void addDefaultPain() {
		switch (getStage()) {
		case 1:
			addPain(AlphaIM.PAIN_POWER_STRONG);
		case 2:
			addPain(AlphaIM.PAIN_POWER_WEAK);
		case 3:
			addPain(AlphaIM.PAIN_POWER_NORMAL);
		case 4:
			addPain(AlphaIM.PAIN_POWER_STRONG);
		}
	}

	protected void addPain(int power) {
		getAlphaModel().Pain.add(getName(), getIntArg("location"), power);
	}

}
