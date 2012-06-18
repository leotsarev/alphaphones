package alpha.genes;

import alpha.AlphaIM;
import alpha.chem.Chemistry.Gene;
import phones.InteractionModel.MenuDescriptor;
import phones.ProcessModelBase;

public class MasterGeneMenu extends GeneMenuBase {

	public MasterGeneMenu(ProcessModelBase model) {
		super(model);
	}

	public String getName() {
		return "MasterGeneMenu";
	}

	protected void bindGeneMenuItem(MenuDescriptor menu, Gene gene) {
		menu.addItem(gene.toString(), AlphaIM.TOGGLE_GENE + gene.getName());
	}

	protected String getMenuHeader() {
		return "МАСТЕРСКОЕ: управление генами";
	}

}
