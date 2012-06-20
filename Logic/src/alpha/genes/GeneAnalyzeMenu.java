package alpha.genes;

import alpha.AlphaIM;
import alpha.chem.IPersistentChemObject;
import alpha.menu.ChemMenuBase;
import phones.InteractionModel.MenuDescriptor;
import phones.ProcessModelBase;

public class GeneAnalyzeMenu extends ChemMenuBase {

	public GeneAnalyzeMenu(ProcessModelBase model) {
		super(model);
	}

	public String getName() {
		return "GeneAnalyzeMenu";
	}

	protected String getMenuHeader() {
		return "Генетический анализ";
	}

	protected void bindChemMenuItem(MenuDescriptor menu, IPersistentChemObject gene) {
		if (gene.eligbleForAnalysis())
		{
			menu.addItem("Анализ " + gene.getName(), AlphaIM.ANALYZE_GENE + gene.getName());
		}
	}

	protected IPersistentChemObject[] getChemObjects() {
		return getAlphaModel().Chemistry.getGeneArray();
	}

}
