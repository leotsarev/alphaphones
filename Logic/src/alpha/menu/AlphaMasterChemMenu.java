package alpha.menu;

import alpha.AlphaIM;
import alpha.chem.Chemistry;
import alpha.chem.IChemObject;
import alpha.food.deficit.*;
import phones.Event;
import phones.IProcess;
import phones.InteractionModel.MenuDescriptor;
import phones.Sample.CancelByName;
import phones.ProcessModelBase;

public class AlphaMasterChemMenu extends phones.MenuBase {

	public AlphaMasterChemMenu(ProcessModelBase model) {
		super(model);
	}
	
	public String getMenuName() {
		return "МАСТЕРСКИЙ Химический статус";
	}

	public void addMenuItems(MenuDescriptor menu) {
		AlphaIM alphaIM = (AlphaIM)model;
		Chemistry chem = alphaIM.Chemistry;
		addClose(menu);
		IChemObject[] items = chem.getSubstanceArray();
		for (int i =0; i < items.length; i++)
		{
			menu.addItem(items[i].toString(), "");
		}
		menu.addItem("Иммунитет: " + chem.getImmunityValue(), "");
		menu.addItem("Кислород: " + alphaIM.oxygenLevel, "");
		menu.addItem("Радиация: " + alphaIM.radDamage + "/" + alphaIM.Chemistry.getRadImmune() + "|" + alphaIM.radStage, "");
		showDeficit(menu, new FoodDeficitAlpha(alphaIM));
		showDeficit(menu, new FoodDeficitBeta(alphaIM));
		showDeficit(menu, new FoodDeficitGamma(alphaIM));
		showDeficit(menu, new FoodDeficitDelta(alphaIM));
		showDeficit(menu, new FoodDeficitPhi(alphaIM));
		showDeficit(menu, new FoodDeficitChi(alphaIM));
	}

	private void showDeficit(MenuDescriptor menu, IProcess process) {
		String name = process.getName();
		Event pr = model.getByName(name);
		if (pr != null)
		{
			addMenuItemAndBind(menu, pr.toString(), new CancelByName(model, name));
		}
	}


	public String getName() {
		return "AlphaMasterChemMenu";
	}

}
