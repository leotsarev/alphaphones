package alpha.food;

import phones.IPrefixHandler;
import phones.ProcessModelBase;

public abstract class NutrientPrefixBase extends NutrientActionBase implements IPrefixHandler {

	public NutrientPrefixBase(ProcessModelBase model) {
		super(model);
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
