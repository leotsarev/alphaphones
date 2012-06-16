package alpha.oxygen;

import alpha.AlphaProcess;
import phones.InteractionModel.Descriptor;
import phones.ProcessModelBase;

public class PutMaskOn extends AlphaProcess {

	public PutMaskOn(ProcessModelBase model) {
		super(model);
	}

	public Descriptor handle() {
		if (getAlphaModel().wearingMask)
		{
			return createMessage("Я уже в маске.");
		}
		getAlphaModel().wearingMask = true;
		addStatusMessage("mask", "В маске");
		return createMessage("Одеваю маску...");
	}

	public String getName() {
		return "PutMaskOn";
	}

}
