package alpha.disease;

import phones.ProcessModelBase;

public abstract class GeneDiseaseBase extends AlphaDiseaseBase {

	public GeneDiseaseBase(ProcessModelBase model) {
		super(model);
	}

	protected int getMaxStage() {
		return 4;
	}
	
	protected int getQuantLenInMins() {
		return 60;
	}

	protected final boolean hasDefectGene()
	{
		return getChemObj().isPresent();
	}

	protected final boolean shouldHeal() {
		return !hasDefectGene();
	}

	public final boolean shouldStartDisease() {
		return hasDefectGene() && super.shouldStartDisease();
	}

}
