package alpha.genes;

import alpha.AlphaIM;
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
		menu.addItem(gene.getValueString(), AlphaIM.TOGGLE_GENE + gene.getName());
	}

	protected String getMenuHeader() {
		return "МАСТЕРСКОЕ: управление генами";
	}

}
