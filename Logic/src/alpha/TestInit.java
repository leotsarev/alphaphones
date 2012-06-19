package alpha;

import alpha.oxygen.EnterBase;
import alpha.oxygen.PutMaskOff;
import alpha.oxygen.ScheduleNextOxygen;
import alpha.sleep.Awake;
import phones.InteractionModel.Descriptor;
import phones.ProcessModelBase;

public class TestInit extends AlphaProcess {

	public TestInit(ProcessModelBase model) {
		super(model);
	}

	public Descriptor handle() {
		getAlphaModel().initCompleted = true;
		getAlphaModel().gender = true;
		scheduleNow(new EnterBase(model));
		scheduleNow(new PutMaskOff(model));
		scheduleNow(new Awake(model));
		scheduleNow(new ScheduleNextOxygen(model));
		return createMessage("Инициализация закончена");
	}

	public String getName() {
		return "TestInit";
	}

}
