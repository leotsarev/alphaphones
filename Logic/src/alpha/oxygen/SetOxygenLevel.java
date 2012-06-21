package alpha.oxygen;

import phones.InteractionModel.Descriptor;
import phones.ProcessModelBase;
import alpha.AlphaProcess;

public class SetOxygenLevel extends AlphaProcess {

	public SetOxygenLevel(ProcessModelBase model) {
		super(model);
	}

	public SetOxygenLevel(ProcessModelBase alphaIM, int level) {
		this(alphaIM);
		setIntArg("oxygen", level);
	}

	public Descriptor handle() {
		getAlphaModel().oxygenLevel = getIntArg("oxygen");
		return nothing();
	}

	public String getName() {
		return "SetOxygenLevel";
	}

}
