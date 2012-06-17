package alpha.food;

import phones.InteractionModel.Descriptor;
import phones.ProcessModelBase;

public class SetNutrient extends NutrientActionBase {

	public SetNutrient(ProcessModelBase model) {
		super(model);
	}

	public Descriptor handle() {
		targetNutrient().setNutrientValue(true);
		
		RemoveNutrient process = new RemoveNutrient(model, getNutrientNum());
		
		unscheduleEqual(process);
		scheduleAfterMins(process, Nutrient.VANISH_MINS);
		
		return null;
	}

	public String getName() {
		return "SetNutrient";
	}

}
