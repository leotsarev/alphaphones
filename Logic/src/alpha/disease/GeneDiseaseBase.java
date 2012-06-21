package alpha.disease;

import phones.ProcessModelBase;

public abstract class GeneDiseaseBase extends AlphaDiseaseBase {

	public GeneDiseaseBase(ProcessModelBase model) {
		super(model);
	}

	protected final int getMaxStage() {
		return 4;
	}
	
	protected final int getQuantLenInMins() {
		return 60;
	}

	protected final boolean hasDefectGene()
	{
		return getChemObj().isPresent();
	}

	protected final boolean shouldHeal() {
		return !hasDefectGene();
	}

	protected final boolean shouldStartDisease() {
		return hasDefectGene() && super.shouldStartDisease();
	}

}
