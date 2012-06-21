package alpha.menu;

import alpha.AlphaIM;
import alpha.TestInit;
import alpha.ideology.IdeologyCheck;
import alpha.ideology.KillSomeOne;
import alpha.oxygen.EnterBase;
import alpha.oxygen.ExitBase;
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
		
		if (AlphaIM.DEMO_MODE)
		{
			bindDemoMenu(menu);
			return;
		}
		
		menu.addItem("Закрыть", "");
		
		if (((AlphaIM)model).sleeping)
		{
			addMenuItemAndBind(menu, "Проснуться", new Awake(model));
			return;
		}

		addMenuItemAndBind(menu, "Выбор идеологии", new IdeologyCheck(model));
		if (((AlphaIM) model).canGetKillChip())
		{
			addMenuItemAndBind(menu, "Убить", new KillSomeOne(model));
		}
		addMenuItemAndBind(menu, "Ранение", new WoundMenu(model));
		addMenuItemAndBind(menu, "Лечь спать", new Asleep(model));
		
		addMaskMenuItem(menu);
		if (((AlphaIM)model).inHouse)
		{
			addMenuItemAndBind(menu, "Выйти из базы", new ExitBase(model));
		}
		else
		{
			addMenuItemAndBind(menu, "Войти в базу", new EnterBase(model));
		}

	}

	private void addMaskMenuItem(MenuDescriptor menu) {
		if (((AlphaIM)model).wearingMask)
		{
			addMenuItemAndBind(menu, "Снять защ.костюм", new PutMaskOff(model));
		}
		else
		{
			addMenuItemAndBind(menu, "Надеть защ.костюм", new PutMaskOn(model));
		}
	}

	private void bindDemoMenu(MenuDescriptor menu) {
		menu.addItem("Закрыть", "");
		if (((AlphaIM)model).sleeping)
		{
			addMenuItemAndBind(menu, "Проснуться", new Awake(model));
			return;
		}
		
		addMenuItemAndBind(menu, "Старт теста", new TestInit(model));
		addMenuItemAndBind(menu, "Лечь спать", new Asleep(model));

		addMaskMenuItem(menu);
		if (((AlphaIM)model).inHouse)
		{
			addMenuItemAndBind(menu, "Выйти из поселения", new ExitBase(model));
		}
		else
		{
			addMenuItemAndBind(menu, "Войти в поселение", new EnterBase(model));
		}
	}

}
