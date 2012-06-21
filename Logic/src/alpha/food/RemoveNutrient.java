package alpha.food;

import phones.InteractionModel.Descriptor;
import phones.ProcessModelBase;
import alpha.chem.Chemistry.Nutrien;

public class RemoveNutrient extends NutrientPrefixBase {

	public RemoveNutrient(ProcessModelBase model) {
		super(model);
	}

	public RemoveNutrient(ProcessModelBase model, Nutrien nutrient) {
		this(model);
		setChemObj(nutrient);
	}
	
	public Descriptor handle() {
		targetNutrient().setNotPresent();
		scheduleAfterMins(this, 10);
		return null;
	}
	
	public String getName() {
		return "RemoveNutrient";
	}

}
