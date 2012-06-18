package alpha.food;

import alpha.chem.ChemActionBase;
import alpha.chem.Chemistry;
import phones.InteractionModel.Descriptor;
import phones.ProcessModelBase;

public class SetNutrient extends NutrientPrefixBase  {

	public SetNutrient(ProcessModelBase model) {
		super(model);
	}

	public Descriptor handle() {
		targetNutrient().setPresent();
		
		ChemActionBase process = new RemoveNutrient(model, targetNutrient());
		
		unscheduleEqual(process);
		scheduleAfterMins(process, Chemistry.NUTRIEN_VANISH_MINS);
		
		return null;
	}

	public String getName() {
		return "SetNutrient";
	}

}
