package alpha.sleep;

import alpha.AlphaProcess;
import phones.InteractionModel.Descriptor;
import phones.ProcessModelBase;

public class Asleep extends AlphaProcess {

	public Asleep(ProcessModelBase model) {
		super(model);
	}

	public Descriptor handle() {
		if (getAlphaModel().sleeping)
		{
			return createMessage("Я и так уже сплю!");
		}
		if (getAlphaModel().wearingMask)
		{
			return createMessage("В маске не поспишь!");
		}
		if (getAlphaModel().isBadAtmoshere())
		{
			return createMessage("В таком воздухе не поспишь!");
		}
		getAlphaModel().sleeping = true;
		addStatusMessage("sleep", "Сплю");
		return createMessage("Я потихоньку засыпаю...");
	}

	public String getName() {
		return "Asleep";
	}

}
