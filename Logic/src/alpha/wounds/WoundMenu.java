package alpha.wounds;

import phones.InteractionModel.MenuDescriptor;
import phones.MenuBase;
import phones.ProcessModelBase;

public class WoundMenu extends MenuBase {

	public WoundMenu(ProcessModelBase model) {
		super(model);
	}

	public String getName() {
		return "WoundMenu";
	}

	public void addMenuItems(MenuDescriptor menu) {
		menu.addItem("Ранен в левую руку", "wound_left_arm");
		
	}

}
