package alpha.menu;

import alpha.AlphaIM;
import alpha.chem.IChemObject;
import phones.InteractionModel.MenuDescriptor;
import phones.ProcessModelBase;

public class AlphaMasterChemMenu extends phones.MenuBase {

	public AlphaMasterChemMenu(ProcessModelBase model) {
		super(model);
	}
	
	public String getMenuName() {
		return "МАСТЕРСКИЙ Химический статус";
	}

	public void addMenuItems(MenuDescriptor menu) {
		IChemObject[] items = ((AlphaIM)model).Chemistry.getSubstanceArray();
		for (int i =0; i < items.length; i++)
		{
			menu.addItem(items[i].toString(), "");
		}
	}

	public String getName() {
		return "AlphaMasterChemMenu";
	}

}
