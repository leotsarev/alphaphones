package alpha.disease;

import phones.ProcessModelBase;

public abstract class VirusDiseaseBase extends GeneDiseaseBase {

	public VirusDiseaseBase(ProcessModelBase model) {
		super(model);
	}
	
	protected int getQuantLenInMins() {
		return 5;
	}
	
	protected int getMaxStage() {
		return 11;
	}

	protected void updateStatusForStage() {
	}

}
