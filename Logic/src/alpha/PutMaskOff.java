package alpha;

import phones.InteractionModel.Descriptor;
import phones.ProcessModelBase;

public class PutMaskOff extends AlphaProcess {

	public PutMaskOff(ProcessModelBase model) {
		super(model);
	}

	public Descriptor handle() {
		if (!getAlphaModel().wearingMask)
		{
			return createMessage("Я без маски, что снимать?");
		}
		getAlphaModel().wearingMask = false;
		removeStatusMessage("mask");
		return createMessage("Снимаю маску...");
	}

	public String getName() {
		return "PutMaskOff";
	}

}
