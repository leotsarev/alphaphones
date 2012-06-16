package alpha;

import phones.InteractionModel.MenuDescriptor;
import phones.MenuBase;
import phones.ProcessModelBase;

public class AlphaMenu extends MenuBase {

	public AlphaMenu(ProcessModelBase model) {
		super(model);
	}

	public String getName() {
		return "AlphaMenu";
	}
	
	public void addMenuItems(MenuDescriptor menu) {
		addMenuItemAndBind(menu, "Выбор идеологии", new IdeologyCheck(model));
		addMenuItemAndBind(menu, "Ранение", new WoundMenu(model));
		addMenuItemAndBind(menu, "(Под код) Генетический анализ", new GeneAnalyzeMenu(model)); 
		if (((AlphaIM)model).sleeping)
		{
			addMenuItemAndBind(menu, "Проснуться", new Awake(model));
		}
		else
		{
			addMenuItemAndBind(menu, "Лечь спать", new Asleep(model));
		}
	}

}
