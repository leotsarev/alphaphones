package phones;

import phones.Processes.HitMeProcess;

public class AlphaIM extends ProcessModelBase{

	protected void reset()
	{
		super.reset();
		bindFixedCommandWord("HITME", new HitMeProcess(this).getName());
		
	}
	
	public Process createProcessByName(String name) {
		// TODO Auto-generate
		HitMeProcess hitMeProcess = new HitMeProcess(this);
		if (name == hitMeProcess.getName())
		{
			return hitMeProcess;
		}
		return super.createProcessByName(name);
	}
}
