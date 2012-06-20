package alpha.menu;

import alpha.AlphaProcess;
import alpha.chem.IPersistentChemObject;
import phones.InteractionModel.Descriptor;
import phones.InteractionModel.MenuDescriptor;
import phones.ProcessModelBase;

public abstract class ChemMenuBase extends AlphaProcess {

	public ChemMenuBase(ProcessModelBase model) {
		super(model);
	}

	public Descriptor handle() {
		MenuDescriptor menu = new MenuDescriptor();
		menu.menuHeader = getMenuHeader();
		addMenuItemAndBind(menu, "Закрыть", new MasterMenu(model));
		IPersistentChemObject[] chemObjs = getChemObjects();
		for (int i =0; i < chemObjs.length; i++)
		{
			bindChemMenuItem(menu, chemObjs[i]);
		}
		return menu;
	}

	protected abstract IPersistentChemObject[] getChemObjects();
	protected abstract String getMenuHeader();
	protected abstract void bindChemMenuItem(MenuDescriptor menu, IPersistentChemObject gene);

}
