package alpha;

import phones.InteractionModel.MenuDescriptor;
import phones.MenuBase;
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
		menu.addItem("Выбрать личность", "INDIVIDUAL");
		menu.addItem("Выбрать общество", "COMMONS");
		menu.addItem("Выбрать освоить", "EXPLORE");
		menu.addItem("Выбрать сохранить планету", "PROTECT");
		menu.addItem("Выбрать милосердие", "WEAK");
		menu.addItem("Выбрать суровость", "HARD");
	}

}
