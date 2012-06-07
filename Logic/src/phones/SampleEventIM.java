package phones;

import phones.Processes.BreathHardly;

public class SampleEventIM extends ProcessModelBase {

	public void reset()
	{
		super.reset();
		scheduleNow(new BreathHardly(this));
	}
}
