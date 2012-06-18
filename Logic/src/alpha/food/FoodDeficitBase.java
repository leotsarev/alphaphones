package alpha.food;

import alpha.chem.Chemistry.Nutrien;
import phones.ProcessModelBase;

public abstract class FoodDeficitBase extends NutrienActionBase {

	public FoodDeficitBase(ProcessModelBase model, Nutrien targetNutrient) {
		super(model);
		setChemObj(targetNutrient);
	}

}
