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
	
	public String getMenuName() {
		return "Ранен в..";
	}

	public void addMenuItems(MenuDescriptor menu) {
		menu.addItem("...правую руку", "wound_right_arm");
		menu.addItem("...левую руку", "wound_left_arm");
		menu.addItem("...правую ногу", "wound_right_leg");
		menu.addItem("...левую ногу", "wound_left_leg");
		menu.addItem("..корпус", "wound_torso");
	}

}
