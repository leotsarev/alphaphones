package alpha.food;

import alpha.chem.Chemistry.Nutrien;
import alpha.chem.IPersistentChemObject;
import phones.InteractionModel.Descriptor;
import phones.ProcessModelBase;

public class MasterToggleNutrien extends alpha.chem.ChemActionBase {

	public MasterToggleNutrien(ProcessModelBase model) {
		super(model);
	}

	public Descriptor handle() {
		scheduleNow(new NutrienMenu(model));
		if (getChemObj().isPresent())
		{
			scheduleNow(new RemoveNutrient(model, (Nutrien) getChemObj()));
		}
		else
		{
			getAlphaModel().consumeNutrien((Nutrien) getChemObj());
		}
		
		return null;
	}

	public String getName() {
		return "MasterToggleNutrien";
	}

}
