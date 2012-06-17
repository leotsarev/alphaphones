package alpha.food;

import phones.InteractionModel.Descriptor;
import phones.ProcessModelBase;

public class RemoveNutrient extends NutrientActionBase {

	public RemoveNutrient(ProcessModelBase model) {
		super(model);
	}

	public RemoveNutrient(ProcessModelBase model, int nutrientNum) {
		this(model);
		setNutrientNum(nutrientNum);
	}

	public Descriptor handle() {
		targetNutrient().setNutrientValue(true);
		
		return createMessage("nutrient vanished:" + targetNutrient().getName());
	}

	public String getName() {
		return "RemoveNutrient";
	}

}
