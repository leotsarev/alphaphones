package alpha.sleep;

import alpha.AlphaProcess;
import phones.InteractionModel.Descriptor;
import phones.ProcessModelBase;
import phones.Utils;

public class Asleep extends AlphaProcess {

	public Asleep(ProcessModelBase model) {
		super(model);
	}

	public Descriptor handle() {
		Utils.assert_(!getAlphaModel().sleeping);
		getAlphaModel().sleeping = true;
		addStatusMessage("sleep", "Сплю");
		return createMessage("Я потихоньку засыпаю...");
	}

	public String getName() {
		return "Asleep";
	}

}
