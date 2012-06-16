package alpha;

import alpha.AlphaIM.GeneContainer.Gene;
import phones.InteractionModel.Descriptor;
import phones.InteractionModel.MenuDescriptor;
import phones.ProcessModelBase;

public class MasterGeneMenu extends AlphaProcess {

	public MasterGeneMenu(ProcessModelBase model) {
		super(model);
	}

	public Descriptor handle() {
		MenuDescriptor menu = new MenuDescriptor();
		menu.menuHeader = "МАСТЕРСКОЕ: управление генами";
		Gene[] genes = getAlphaModel().Genes.asArray();
		for (int i =0; i < genes.length; i++)
		{
			Gene gene = genes[i];
			menu.addItem(gene.toString(), AlphaIM.TOGGLE_GENE + gene.getName());
		}
		return menu;
	}

	public String getName() {
		return "MasterGeneMenu";
	}

}
