package alpha.food;

import phones.ProcessModelBase;

public abstract class FoodDeficitBase extends NutrientActionBase {

	public FoodDeficitBase(ProcessModelBase model, Nutrient targetNutrient) {
		super(model);
		setNutrientNum(targetNutrient.getNum());
	}

}
