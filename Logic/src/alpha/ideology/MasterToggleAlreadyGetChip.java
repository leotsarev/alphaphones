package alpha.ideology;

import phones.InteractionModel.Descriptor;
import phones.ProcessModelBase;
import alpha.AlphaProcess;
import alpha.menu.MasterMenu;

public class MasterToggleAlreadyGetChip extends AlphaProcess {

	public MasterToggleAlreadyGetChip(ProcessModelBase model) {
		super(model);
	}

	public Descriptor handle() {
		
		getAlphaModel().alreadyGetChip = !getAlphaModel().alreadyGetChip;
		
		scheduleNow(new MasterMenu(model));
		return null;
	}

	public String getName() {
		return "MasterToggleAlreadyGetChip";
	}

}
