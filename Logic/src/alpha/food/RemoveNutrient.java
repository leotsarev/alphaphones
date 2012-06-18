package alpha.food;

import alpha.chem.ChemActionBase;
import alpha.chem.Chemistry.Nutrien;
import phones.InteractionModel.Descriptor;
import phones.ProcessModelBase.Process;
import phones.ProcessModelBase;
import phones.Utils;

public class RemoveNutrient extends NutrientPrefixBase {

	public RemoveNutrient(ProcessModelBase model) {
		super(model);
	}

	public RemoveNutrient(ProcessModelBase model, Nutrien nutrient) {
		this(model);
		setChemObj(nutrient);
	}
	
	
	public static double calculateDeficitRisk(Nutrien targetNutrient) {
		return 10;
	}


	public Descriptor handle() {
		targetNutrient().setNotPresent();
		
		if (model.randomInt(100) < calculateDeficitRisk(targetNutrient()))
		{
			Process process = model.createProcessByName(targetNutrient().getDeficitName());
			Utils.assert_(process instanceof ChemActionBase);
			((ChemActionBase)process).setTargetChemObj(targetNutrient());
			scheduleNow(process);
		}
		
		scheduleAfterMins(this, 10);
		return null;
	}

	public String getName() {
		return "RemoveNutrient";
	}

}
