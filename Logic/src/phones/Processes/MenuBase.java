package phones.Processes;

import phones.InteractionModel.Descriptor;
import phones.InteractionModel.MenuDescriptor;
import phones.ProcessModelBase;
import phones.ProcessModelBase.Process;

public abstract class MenuBase extends Process {

	public MenuBase(ProcessModelBase model) {
		super(model);
		// TODO Auto-generated constructor stub
	}

	public Descriptor handle() {
		MenuDescriptor menu = new MenuDescriptor();
		addMenuItems(menu);
		menu.timeoutCommand = getTimeoutCommandWord();
		menu.timeout = getMenuTimeout();
		menu.menuHeader = getMenuName();
		return menu;
	}

	public String getTimeoutCommandWord() {
		return "";
	}

	public int getMenuTimeout() {
		return 30;
	}

	public String getMenuName() {
		return "Меню";
	}

	public abstract void addMenuItems(MenuDescriptor menu);

}