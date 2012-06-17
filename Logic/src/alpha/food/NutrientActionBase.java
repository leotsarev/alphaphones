package alpha.food;

import phones.ProcessModelBase;
import phones.Utils;
import alpha.AlphaProcess;

public abstract class NutrientActionBase extends AlphaProcess {

	public NutrientActionBase(ProcessModelBase model) {
		super(model);
	}

	protected Nutrient targetNutrient() {
		Nutrient nutrient = getAlphaModel().Nutrients.get(getNutrientNum());
		Utils.assert_(nutrient != null, "Can't find nutrient");
		return nutrient;
	}

	protected int getNutrientNum() {
		return getIntArg("nutrientNum");
	}
	
	protected void setNutrientNum(int val) {
		setIntArg("nutrientNum", val);
	}


}
