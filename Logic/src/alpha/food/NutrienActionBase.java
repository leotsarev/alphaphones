package alpha.food;

import phones.ProcessModelBase;
import phones.Utils;
import alpha.chem.ChemActionBase;
import alpha.chem.Chemistry.Nutrien;
import alpha.chem.IChemObject;

public abstract class NutrienActionBase extends ChemActionBase {

	public NutrienActionBase(ProcessModelBase model) {
		super(model);
	}

	protected Nutrien targetNutrient() {
		IChemObject result = getChemObj();
		Utils.assert_(result instanceof Nutrien, getChemName() + "isn't nutrient!");
		return (Nutrien) result;
	}
}
