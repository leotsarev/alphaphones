package alpha;

import phones.ProcessModelBase;
import phones.Sample.HitMeProcess;

public class AlphaIM extends ProcessModelBase{

	public int[] coords = new int[4];

	protected void reset()
	{
		super.reset();
		bindFixedCommandWord("HITME", new HitMeProcess(this));
		bindFixedCommandWord("MENU", new AlphaMenu(this));
		bindFixedCommandWord("IDEOLOGY", new IdeologyMenu(this));
		bindFixedCommandWord("SCIENCS", new IdeologyChange(this));
	}
	
	public Process createProcessByName(String name) {
		// TODO Auto-generate
		Process[] process =
			{
				new HitMeProcess(this),
				new AlphaMenu(this),
				new IdeologyMenu(this),
				new IdeologyChange(this),
				new UpdateFaction(this)
			};
		for (int i = 0; i < process.length; i++)
		{
			if (name == process[i].getName())
			{
				return process[i];
			}
		}
		return super.createProcessByName(name);
	}

}
