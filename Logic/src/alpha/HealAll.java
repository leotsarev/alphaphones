package alpha;

import alpha.wounds.*;
import phones.InteractionModel.Descriptor;
import phones.ProcessModelBase;

public class HealAll extends AlphaProcess {

	public HealAll(ProcessModelBase model) {
		super(model);
	}

	public Descriptor handle() {
		unscheduleAndCleanup(new ArmWound(model, 0));
		unscheduleAndCleanup(new LegWound(model, 0));
		unscheduleAndCleanup(new TorsoWound(model));
		return createMessage("Современная медицина подобно чуду: все последствия ран мгновенно проходят");
	}

	public String getName() {
		return "HealAll";
	}

}
