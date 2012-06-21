package alpha.food;

import alpha.SimpleDiseaseBase;
import alpha.chem.IChemAction;
import alpha.chem.IChemObject;
import phones.ProcessModelBase;

public abstract class FoodDeficitBase extends SimpleDiseaseBase implements IChemAction {

	public FoodDeficitBase(ProcessModelBase model) {
		super(model);
	}
	
	public void setChem(String chemName) {
		setChemObj(chemName);
	}

	public void setChem(IChemObject chemObj) {
		setChemObj(chemObj);
	}

	protected boolean shouldHeal() {
		return getChemObj().isPresent();
	}

	protected int getMaxStage() {
		return 4;
	}
}
