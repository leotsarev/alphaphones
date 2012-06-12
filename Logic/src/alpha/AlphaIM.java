package alpha;

import phones.ISerializer;
import phones.ProcessModelBase;
import phones.Sample.HitMeProcess;

public class AlphaIM extends ProcessModelBase{

	public Faction[] factions;
	public Faction currentFaction = null;
	
	public static final int LEFT_LIMB = 1;
	public static final int RIGHT_LIMB = 1;

	protected void reset()
	{
		super.reset();
		
		bindCommandWords();
		
		factions = Faction.createFactions(this);
	}

	private void bindCommandWords() {
		bindFixedCommandWord("HITME", new HitMeProcess(this));
		bindFixedCommandWord("MENU", new AlphaMenu(this));
		bindFixedCommandWord("IDEOLOGY_CHECK", new IdeologyCheck(this));
		
		bindFixedCommandWord("IDEOLOGY_MENU", new IdeologyMenu(this));
		
		bindFixedCommandWord("INDIVIDUAL", new IdeologyChange(this, 0, 1));
		bindFixedCommandWord("COMMONS", new IdeologyChange(this, 0, -1));
		
		bindFixedCommandWord("SCIENCE", new IdeologyChange(this, 1, 1));
		bindFixedCommandWord("MYSTIC", new IdeologyChange(this, 1, -1));

		bindFixedCommandWord("EXPLORE", new IdeologyChange(this, 2, 1));
		bindFixedCommandWord("PROTECT", new IdeologyChange(this, 2, -1));
		
		bindFixedCommandWord("WEAK", new IdeologyChange(this, 3, 1));
		bindFixedCommandWord("HARD", new IdeologyChange(this, 3, -1));
		
		bindFixedCommandWord("wound_menu", new WoundMenu(this));
		
		bindFixedCommandWord("wound_left_arm", new ArmWound(this, LEFT_LIMB));
		bindFixedCommandWord("wound_right_arm", new ArmWound(this, RIGHT_LIMB));
	}
	
	public Process createProcessByName(String name) {
		// TODO Auto-generate
		Process[] process =
			{
				new HitMeProcess(this),
				new AlphaMenu(this),
				new IdeologyMenu(this),
				new IdeologyChange(this),
				new IdeologyCheck(this),
				new OutOfOxygen(this),
				new CanBreathAgain(this),
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
	
	public void serialize(ISerializer ser) {
		super.serialize(ser);
		for (int i =0; i<factions.length; i++)
		{
			factions[i].serialize(ser);
		}
	}
	
	public void unserialize(ISerializer ser) {
		super.unserialize(ser);
		for (int i =0; i<factions.length; i++)
		{
			factions[i].unserialize(ser);
		}
	}

	public Faction getCurrentFaction() {
		updateCurrentFaction();
		return currentFaction;
	}

	private void updateCurrentFaction() {
		if (currentFaction == null)
		{
			selectFactionForFirstTime();
		}
		else for (int i =0; i<factions.length; i++)
		{
			Faction fct = factions[i];
			if (fct.getTrack() > currentFaction.getTrack())
			{
				currentFaction = fct;
			}
		}
	}

	public void selectFactionForFirstTime() {
		boolean skipFirst = Math.random() > 0.5;
		for (int i =0; i<factions.length; i++)
		{
			Faction fct = factions[i];
			if  (fct.getTrack() > 0)
			{
				if (skipFirst)
				{
					skipFirst = false;
				}
				else
				{
					currentFaction = fct;
				}
			}
		}
	}

	public boolean isFanatic() {
		return currentFaction != null && currentFaction.isFanatic();
	}

}
