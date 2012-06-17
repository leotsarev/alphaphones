package alpha.food;

import phones.IPrefixHandler;
import phones.ProcessModelBase;
import phones.Utils;
import alpha.AlphaProcess;

public abstract class NutrientActionBase extends AlphaProcess implements IPrefixHandler{

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

	public boolean isStartOfSuffix(String suffix) {
		return suffix == "";
	}

	public boolean isValidSuffix(String suffix) {
		return getAlphaModel().Nutrients.get(Integer.valueOf(suffix).intValue()) != null;
	}

	public void setSuffixValue(String suffix) {
		setNutrientNum(Integer.valueOf(suffix).intValue());
	}

}
