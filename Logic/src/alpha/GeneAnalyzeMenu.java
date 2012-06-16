package alpha;

import alpha.AlphaIM.GeneContainer.Gene;
import phones.InteractionModel.MenuDescriptor;
import phones.ProcessModelBase;

public class GeneAnalyzeMenu extends GeneMenuBase {

	public GeneAnalyzeMenu(ProcessModelBase model) {
		super(model);
	}

	public String getName() {
		return "GeneAnalyzeMenu";
	}

	protected String getMenuHeader() {
		return "Генетический анализ";
	}

	protected void bindGeneMenuItem(MenuDescriptor menu, Gene gene) {
		menu.addItem("Анализ гена " + gene.getName(), AlphaIM.ANALYZE_GENE + gene.getName());		
	}

}
