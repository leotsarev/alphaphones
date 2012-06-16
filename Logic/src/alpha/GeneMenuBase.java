package alpha;

import alpha.AlphaIM.GeneContainer.Gene;
import phones.InteractionModel.Descriptor;
import phones.InteractionModel.MenuDescriptor;
import phones.ProcessModelBase;

public abstract class GeneMenuBase extends AlphaProcess {

	public GeneMenuBase(ProcessModelBase model) {
		super(model);
	}

	public Descriptor handle() {
		MenuDescriptor menu = new MenuDescriptor();
		menu.menuHeader = getMenuHeader();
		menu.addItem("Закрыть", "");
		Gene[] genes = getAlphaModel().Genes.asArray();
		for (int i =0; i < genes.length; i++)
		{
			Gene gene = genes[i];
			bindGeneMenuItem(menu, gene);
		}
		return menu;
	}

	protected abstract String getMenuHeader();
	protected abstract void bindGeneMenuItem(MenuDescriptor menu, Gene gene);

}
