package alpha.food;

import phones.InteractionModel.MenuDescriptor;
import phones.ProcessModelBase;
import alpha.AlphaIM;
import alpha.chem.IPersistentChemObject;
import alpha.menu.ChemMenuBase;

public class NutrienMenu extends ChemMenuBase {

	public NutrienMenu(ProcessModelBase model) {
		super(model);
	}

	protected IPersistentChemObject[] getChemObjects() {
		return getAlphaModel().Chemistry.getNutrienArray();
	}

	protected String getMenuHeader() {
		return "МАСТЕРСКОЕ: Нутриены";
	}

	protected void bindChemMenuItem(MenuDescriptor menu,
			IPersistentChemObject gene) {
		menu.addItem(gene.toString(), AlphaIM.TOGGLE_NUTRIEN + gene.getName());
	}

	public String getName() {
		return "NutrienMenu";
	}

}
