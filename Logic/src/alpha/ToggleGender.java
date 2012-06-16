package alpha;

import alpha.menu.MasterMenu;
import phones.InteractionModel.Descriptor;
import phones.ProcessModelBase;

public class ToggleGender extends AlphaProcess {

	public ToggleGender(ProcessModelBase model) {
		super(model);
	}

	public Descriptor handle() {
		getAlphaModel().toggleGender();
		scheduleNow(new MasterMenu(model));
		return null;
	}

	public String getName() {
		return "ToggleGender";
	}

}
