package alpha.ideology;

import phones.InteractionModel.MenuDescriptor;
import phones.MenuBase;
import phones.ProcessModelBase;

public class IdeologyMenu extends MenuBase {

	public IdeologyMenu(ProcessModelBase model) {
		super(model);
	}

	public String getName() {
		return "IdeologyMenu";
	}

	public void addMenuItems(MenuDescriptor menu) {
		menu.addItem("Наука", "SCIENCE");
		menu.addItem("Мистика", "MYSTIC");
		menu.addItem("Личность", "INDIVIDUAL");
		menu.addItem("Общество", "COMMONS");
		menu.addItem("Освоить планету", "EXPLORE");
		menu.addItem("Сохранить планету", "PROTECT");
		menu.addItem("Милосердие", "WEAK");
		menu.addItem("Суровость", "HARD");
	}

}
