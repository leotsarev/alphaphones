package alpha;

import alpha.food.*;
import alpha.genes.*;
import alpha.ideology.Faction;
import alpha.ideology.IdeologyChange;
import alpha.ideology.IdeologyCheck;
import alpha.ideology.IdeologyMenu;
import alpha.menu.AlphaMenu;
import alpha.menu.MasterMenu;
import alpha.oxygen.*;
import alpha.sleep.Asleep;
import alpha.sleep.Awake;
import alpha.wounds.ArmWound;
import alpha.wounds.WoundMenu;
import phones.ISerializer;
import phones.ProcessModelBase;
import phones.Sample.HitMeProcess;

public class AlphaIM extends ProcessModelBase{

	public Faction[] factions;
	public Faction currentFaction = null;
	
	public static final int OXYGEN_MIN = 0;
	public static final int OXYGEN_MAX = 3;
	public int oxygenLevel = OXYGEN_MIN;
	
	public static final int LEFT_LIMB = 1;
	public static final int RIGHT_LIMB = 1;
	
	public final GeneContainer Genes = new GeneContainer();
	public final NutrientContainer Nutrients = new NutrientContainer();
	
	public boolean initCompleted;
	public boolean gender;
	public boolean inHouse;
	public boolean wearingMask;
	public boolean sleeping;
	public static final String TOGGLE_GENE = "toggle_gene_";
	public static final String ANALYZE_GENE = "analyze_gene_";
	
	

	protected void reset()
	{
		super.reset();
		
		bindCommandWords();
		
		factions = Faction.createFactions(this);
	}

	private void bindCommandWords() {
		bindFixedCommandWord("HITME", new HitMeProcess(this));
		bindFixedCommandWord("MENU", new AlphaMenu(this));
		
		bindFixedCommandWord("INDIVIDUAL", new IdeologyChange(this, 0, 1));
		bindFixedCommandWord("COMMONS", new IdeologyChange(this, 0, -1));
		
		bindFixedCommandWord("SCIENCE", new IdeologyChange(this, 1, 1));
		bindFixedCommandWord("MYSTIC", new IdeologyChange(this, 1, -1));

		bindFixedCommandWord("EXPLORE", new IdeologyChange(this, 2, 1));
		bindFixedCommandWord("PROTECT", new IdeologyChange(this, 2, -1));
		
		bindFixedCommandWord("WEAK", new IdeologyChange(this, 3, 1));
		bindFixedCommandWord("HARD", new IdeologyChange(this, 3, -1));
		
		bindFixedCommandWord("wound_left_arm", new ArmWound(this, LEFT_LIMB));
		bindFixedCommandWord("wound_right_arm", new ArmWound(this, RIGHT_LIMB));
		
		bindFixedCommandWord("menu_master", new MasterMenu(this));
		
		bindPrefixCommandWord(TOGGLE_GENE, new MasterToggleGene(this));
		bindPrefixCommandWord(ANALYZE_GENE, new AnalyzeGene(this));
		
		bindPrefixCommandWord("_food_", new SetNutrient(this));
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
				new AlphaInit(this),
				new ScheduleNextOxygen(this),
				new MasterGeneMenu(this),
				new MasterToggleGene(this),
				new WoundMenu(this),
				new ArmWound(this, LEFT_LIMB),
				new MasterGeneMenu(this),
				new MasterMenu(this),
				new ToggleGender(this),
				new AnalyzeGene(this),
				new GeneAnalyzeMenu(this),
				new Awake(this),
				new Asleep(this),
				new PutMaskOn(this),
				new PutMaskOff(this),
				new EnterBase(this),
				new ExitBase(this),
				new SetNutrient(this),
				new RemoveNutrient(this)
			};
		for (int i = 0; i < process.length; i++)
		{
			if (name.equals(process[i].getName()))
			{
				return process[i];
			}
		}
		return super.createProcessByName(name);
	}
	
	public void serialize(ISerializer ser) {
		super.serialize(ser);
		ser.writeBool(initCompleted);
		ser.writeBool(gender);
		ser.writeBool(inHouse);
		ser.writeBool(sleeping);
		ser.writeBool(wearingMask);
		for (int i =0; i<factions.length; i++)
		{
			factions[i].serialize(ser);
		}
		Genes.serialize(ser);
		Nutrients.serialize(ser);
	}

	public void unserialize(ISerializer ser) {
		super.unserialize(ser);
		initCompleted = ser.readBool();
		gender = ser.readBool();
		inHouse = ser.readBool();
		sleeping = ser.readBool();
		wearingMask = ser.readBool();
		for (int i =0; i<factions.length; i++)
		{
			factions[i].unserialize(ser);
		}
		Genes.unserialize(ser);
		Nutrients.unserialize(ser);
	}

	

	public Faction getCurrentFaction() {
		updateCurrentFaction();
		return currentFaction;
	}

	public void updateCurrentFaction() {
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
		String factionName = (currentFaction != null) ? currentFaction.getName() : "Не определился"; 
		status.addMessage("faction", "Фракция: " + factionName);
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

	public void toggleGender() {
		gender = !gender;
	}

	public String getGenderName() {
		return gender ? "Мужчина" : "Женщина";
	}

	public int calculateOxygenPause() {
		return (int) Math.floor((10 + oxygenLevel * 5) * (Math.random() + 1));
	}

	public boolean isBadAtmoshere() {
		return !inHouse && !wearingMask;
	}
}
