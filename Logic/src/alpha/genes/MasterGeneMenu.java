package alpha.genes;

import alpha.AlphaIM;
import alpha.chem.IPersistentChemObject;
import alpha.menu.ChemMenuBase;
import phones.InteractionModel.MenuDescriptor;
import phones.ProcessModelBase;

public class MasterGeneMenu extends ChemMenuBase {

	public MasterGeneMenu(ProcessModelBase model) {
		super(model);
	}

	public String getName() {
		return "MasterGeneMenu";
	}

	protected void bindChemMenuItem(MenuDescriptor menu, IPersistentChemObject gene) {
		menu.addItem(gene.toString(), AlphaIM.TOGGLE_GENE + gene.getName());
	}

	protected String getMenuHeader() {
		return "МАСТЕРСКОЕ: управление генами";
	}

	protected IPersistentChemObject[] getChemObjects() {
		return getAlphaModel().Chemistry.getGeneArray();
	}

}
