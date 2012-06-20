package alpha.genes;

import alpha.AlphaProcess;
import alpha.chem.IPersistentChemObject;
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
		IPersistentChemObject[] chemObjs = getChemObjects();
		for (int i =0; i < chemObjs.length; i++)
		{
			bindGeneMenuItem(menu, chemObjs[i]);
		}
		return menu;
	}

	protected abstract IPersistentChemObject[] getChemObjects();
	protected abstract String getMenuHeader();
	protected abstract void bindGeneMenuItem(MenuDescriptor menu, IPersistentChemObject gene);

}
