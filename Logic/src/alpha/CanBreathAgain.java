package alpha;

import phones.InteractionModel.Descriptor;
import phones.ProcessModelBase;

public class CanBreathAgain extends OxygenProcess{

	public CanBreathAgain(ProcessModelBase model) {
		super(model);
	}

	public Descriptor handle() {
		removeStatusMessage(BREATH);
		scheduleNow(new ScheduleNextOxygen(model));
		return createMessage("Вроде вы отдохнули, можно опять вернуться к труду.");
	}

	public String getName() {
		return "CanBreathAgain";
	}

}
