package alpha.menu;

import alpha.AlphaIM;
import alpha.genes.GeneAnalyzeMenu;
import alpha.ideology.IdeologyCheck;
import alpha.oxygen.PutMaskOff;
import alpha.oxygen.PutMaskOn;
import alpha.sleep.Asleep;
import alpha.sleep.Awake;
import alpha.wounds.WoundMenu;
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
		if (((AlphaIM)model).wearingMask)
		{
			addMenuItemAndBind(menu, "Снять маску", new PutMaskOff(model));
		}
		else
		{
			addMenuItemAndBind(menu, "Одеть маску", new PutMaskOn(model));
		}
	}

}
