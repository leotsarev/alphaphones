package alpha.oxygen;

import phones.InteractionModel.Descriptor;
import phones.ProcessModelBase;

public class ScheduleNextOxygen extends OxygenProcess {

	public ScheduleNextOxygen(ProcessModelBase model) {
		super(model);
	}

	public Descriptor handle() {
		int oxygenPauseInMins =  getAlphaModel().calculateOxygenPause();
		
		unscheduleByName(this);
		unscheduleByName(new CanBreathAgain(model));
		unscheduleByName(new OutOfOxygen(model));
		
		if (getAlphaModel().isBadAtmoshere())
		{
			scheduleAfterMins(new OutOfOxygen(model), oxygenPauseInMins);
		}
		else
		{
			removeStatusMessage(BREATH);
		}
		return null;
	}

	public String getName() {
		return "ScheduleNextOxygen";
	}

}
