package alpha.food;

import alpha.AlphaProcess;
import alpha.chem.Chemistry;
import phones.InteractionModel.Descriptor;
import phones.ProcessModelBase;

public class SetNutrient extends NutrienActionBase  {

	public SetNutrient(ProcessModelBase model) {
		super(model);
	}

	public Descriptor handle() {
		targetNutrient().setPresent();
		
		AlphaProcess process = new RemoveNutrient(model, targetNutrient());
		
		unscheduleEqual(process);
		scheduleAfterMins(process, Chemistry.NUTRIEN_VANISH_MINS);
		
		return null;
	}

	public String getName() {
		return "SetNutrient";
	}

}
