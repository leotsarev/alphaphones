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
		
	}

	public String getName() {
		return "AlphaTestMenu";
	}

}
