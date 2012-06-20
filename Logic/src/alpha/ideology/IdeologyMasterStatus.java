package alpha.ideology;

import phones.InteractionModel.Descriptor;
import phones.InteractionModel.MenuDescriptor;
import phones.ProcessModelBase;

public class IdeologyMasterStatus extends alpha.AlphaProcess {

	public IdeologyMasterStatus(ProcessModelBase model) {
		super(model);
	}

	public Descriptor handle() {
		MenuDescriptor menu = new MenuDescriptor();
		menu.menuHeader = "МАСТЕР Статус идеологии";
		menu.addItem("Закрыть", "");
		Faction[] fct = getAlphaModel().factions;
		for (int i =0; i < fct.length; i++)
		{
			menu.addItem(fct[i].toString(), "");
		}
		return menu;
	}

	public String getName() {
		return "IdeologyMasterStatus";
	}

}
