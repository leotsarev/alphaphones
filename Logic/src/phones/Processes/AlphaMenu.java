package phones.Processes;

import phones.InteractionModel.MenuDescriptor;
import phones.ProcessModelBase;

public class AlphaMenu extends MenuBase {

	public AlphaMenu(ProcessModelBase model) {
		super(model);
	}

	public String getName() {
		return "AlphaMenu";
	}
	
	public void addMenuItems(MenuDescriptor menu) {
		menu.addItem("Выбор идеологии", "IDEOLOGY");
	}

}
