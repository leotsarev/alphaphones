package alpha.menu;

import alpha.AlphaIM;
import alpha.AlphaInit;
import alpha.ToggleGender;
import alpha.genes.MasterGeneMenu;
import phones.InteractionModel.MenuDescriptor;
import phones.MenuBase;
import phones.ProcessModelBase;

public class MasterMenu extends MenuBase {

	public MasterMenu(ProcessModelBase model) {
		super(model);
	}

	public String getName() {
		return "MasterMenu";
	}

	public void addMenuItems(MenuDescriptor menu) {
		addMenuItemAndBind(menu, "Гены", new MasterGeneMenu(model));
		AlphaIM alphaIM = (AlphaIM)model;
		if (!alphaIM.initCompleted)
		{
			addMenuItemAndBind(menu, "Инициализация", new AlphaInit(model));
		}
		addMenuItemAndBind(menu, alphaIM.getGenderName() + " (переключить)", new ToggleGender(model));
	}

}
