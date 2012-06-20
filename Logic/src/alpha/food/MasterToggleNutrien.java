package alpha.food;

import alpha.chem.IPersistentChemObject;
import phones.InteractionModel.Descriptor;
import phones.ProcessModelBase;

public class MasterToggleNutrien extends alpha.chem.ChemActionBase {

	public MasterToggleNutrien(ProcessModelBase model) {
		super(model);
	}

	public Descriptor handle() {
		((IPersistentChemObject)getChemObj()).masterToggle();
		scheduleNow(new NutrienMenu(model));
		return null;
	}

	public String getName() {
		return "MasterToggleNutrien";
	}

}
