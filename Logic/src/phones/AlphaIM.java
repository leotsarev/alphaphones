package phones;

import phones.Processes.*;

public class AlphaIM extends ProcessModelBase{

	protected void reset()
	{
		super.reset();
		bindFixedCommandWord("HITME", new HitMeProcess(this).getName());
		bindFixedCommandWord("MENU", new AlphaMenu(this).getName());
		bindFixedCommandWord("IDEOLOGY", new IdeologyMenu(this).getName());
		
	}
	
	public Process createProcessByName(String name) {
		// TODO Auto-generate
		Process[] process =
			{
				new HitMeProcess(this),
				new AlphaMenu(this),
				new IdeologyMenu(this)
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
