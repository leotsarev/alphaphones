package alpha.menu;

import alpha.AlphaIM;
import alpha.TestInit;
import alpha.oxygen.EnterBase;
import alpha.oxygen.ExitBase;
import alpha.oxygen.PutMaskOff;
import alpha.oxygen.PutMaskOn;
import alpha.sleep.Asleep;
import alpha.sleep.Awake;
import phones.MenuBase;
import phones.InteractionModel.MenuDescriptor;
import phones.ProcessModelBase;

public class AlphaTestMenu extends MenuBase {

	public AlphaTestMenu(ProcessModelBase model) {
		super(model);
	}

	public void addMenuItems(MenuDescriptor menu) {
		addMenuItemAndBind(menu, "Старт теста", new TestInit(model));
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
		if (((AlphaIM)model).inHouse)
		{
			addMenuItemAndBind(menu, "Выйти из поселения", new ExitBase(model));
		}
		else
		{
			addMenuItemAndBind(menu, "Войти в поселение", new EnterBase(model));
		}
	}

	public String getName() {
		return "AlphaTestMenu";
	}

}
