package alpha;

import alpha.food.CheckFoodDeficits;
import alpha.oxygen.*;
import alpha.sleep.Awake;
import phones.InteractionModel.Descriptor;
import phones.ProcessModelBase;

public class AlphaInit extends AlphaProcess {

	public AlphaInit(ProcessModelBase model) {
		super(model);
	}

	public Descriptor handle() {
		getAlphaModel().initCompleted = true;
		getAlphaModel().gender = true;
		getAlphaModel().updateCurrentFaction();
		scheduleNow(new EnterBase(model));
		scheduleNow(new PutMaskOff(model));
		scheduleNow(new Awake(model));
		scheduleNow(new ScheduleNextOxygen(model));
		scheduleAfterMins(new CheckFoodDeficits(model), 0);
		
		getAlphaModel().Chemistry.setAllNutrients();
		return null;
	}

	public String getName() {
		return "AlphaInit";
	}

}
