package alpha;

import alpha.oxygen.ScheduleNextOxygen;
import phones.InteractionModel.Descriptor;
import phones.ProcessModelBase;

public class TestInit extends AlphaProcess {

	public TestInit(ProcessModelBase model) {
		super(model);
	}

	public Descriptor handle() {
		getAlphaModel().initCompleted = true;
		getAlphaModel().gender = true;
		getAlphaModel().inHouse = true;
		getAlphaModel().wearingMask = false;
		scheduleNow(new ScheduleNextOxygen(model));
		return createMessage("Инициализация закончена");
	}

	public String getName() {
		return "TestInit";
	}

}
