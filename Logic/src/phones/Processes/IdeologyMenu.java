package phones.Processes;

import phones.InteractionModel.MenuDescriptor;
import phones.ProcessModelBase;

public class IdeologyMenu extends MenuBase {

	public IdeologyMenu(ProcessModelBase model) {
		super(model);
		// TODO Auto-generated constructor stub
	}

	public String getName() {
		return "IdeologyMenu";
	}

	public void addMenuItems(MenuDescriptor menu) {
		menu.addItem("Выбрать науку", "SCIENCE");
		menu.addItem("Выбрать мистику", "MYSTIC");
	}

}
