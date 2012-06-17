package alpha.food;

import phones.InteractionModel.Descriptor;
import phones.ProcessModelBase.Process;
import phones.ProcessModelBase;

public class RemoveNutrient extends NutrientPrefixBase {

	public RemoveNutrient(ProcessModelBase model) {
		super(model);
	}

	public RemoveNutrient(ProcessModelBase model, int nutrientNum) {
		this(model);
		setNutrientNum(nutrientNum);
	}
	
	
	public static double calculateDeficitRisk(Nutrient targetNutrient) {
		return 0.1;
	}


	public Descriptor handle() {
		targetNutrient().setNutrientValue(false);
		
		if (Math.random() > calculateDeficitRisk(targetNutrient()))
		{
			Process process = model.createProcessByName(targetNutrient().getDeficitName());
			
			scheduleNow(process);
		}
		
		return createMessage("nutrient vanished:" + targetNutrient().getName());
	}

	public String getName() {
		return "RemoveNutrient";
	}

}
