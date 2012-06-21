package alpha.food;

import phones.ProcessModelBase;
import alpha.disease.AlphaDiseaseBase;
import alpha.chem.IChemAction;
import alpha.chem.IChemObject;

public abstract class FoodDeficitBase extends AlphaDiseaseBase implements IChemAction {

	protected static final String CAN_T_STAY = "Не могу стоять без помощи";

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
