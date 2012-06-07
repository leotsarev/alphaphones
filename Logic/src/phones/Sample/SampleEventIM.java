package phones.Sample;

import phones.ProcessModelBase;

public class SampleEventIM extends ProcessModelBase {

	public void reset()
	{
		super.reset();
		scheduleNow(new BreathHardly(this));
	}
}
