package alpha;


import java.util.Date;

import alpha.chem.IChemObject;
import alpha.food.*;
import alpha.food.deficit.*;
import alpha.genes.*;
import alpha.ideology.*;
import alpha.menu.*;
import alpha.oxygen.*;
import alpha.sleep.Asleep;
import alpha.sleep.Awake;
import alpha.wounds.ArmWound;
import alpha.wounds.WoundMenu;
import phones.ISerializer;
import phones.ProcessModelBase;
import phones.Utils;
import phones.Sample.HitMeProcess;

public class AlphaIM extends ProcessModelBase{

	public Faction[] factions;
	public Faction currentFaction = null;
	
	public static final int OXYGEN_MIN = 0;
	public static final int OXYGEN_MAX = 3;
	public int oxygenLevel = OXYGEN_MIN;
	
	public static final int LEFT_LIMB = 1;
	public static final int RIGHT_LIMB = 1;
	
	public final alpha.chem.Chemistry Chemistry = new alpha.chem.Chemistry();
	
	public boolean initCompleted;
	public boolean gender;
	public boolean inHouse;
	public boolean wearingMask;
	public boolean sleeping;
	public boolean sick;
	public boolean dead;
	public boolean alreadyGetChip;
	
	public static final String TOGGLE_GENE = "toggle_gene_";
	public static final String TOGGLE_NUTRIEN = "toggle_nutrien_";
	public static final String ANALYZE_GENE = "analyze_gene_";
	
	public static final int LOCATION_WHOLE_BODY = 0;
	public static final int LOCATION_KNEE = 1;
	
	public PainAggregator Pain;
	public static final int PAIN_POWER_WEAK = 1;
	public static final int PAIN_POWER_NORMAL = 2;
	public static final int PAIN_POWER_STRONG = 3;
	
	public static final boolean DEMO_MODE = false;
	
	public final class PainAggregator
	{
		//TODO Сильные источники боли подавляют слабые, etc
		public void serialize(ISerializer ser) {
		}

		public void unserialize(ISerializer ser) {
		}

		public void add(String tag, int location, int painPower) {
			status.addMessage(tag+location, getPainPowerString(painPower) + " " + getLocationString(location));
		}

		private String getLocationString(int location) {
			switch (location)
			{
			case LOCATION_KNEE: 
				return "в колене";
			case LOCATION_WHOLE_BODY:
				return "во всем теле";
			default:
				Utils.assert_(false, "Wrong location " + location);
				return null;
			}
		}

		private String getPainPowerString(int painPower) {
			switch (painPower) {
			case PAIN_POWER_WEAK:
				return "Слабая боль";
			case PAIN_POWER_NORMAL:
				return "Боль";
			case PAIN_POWER_STRONG:
				return "Сильная БОЛЬ";
			default:
				Utils.assert_(false, "Wrong pain level " + painPower);
				return null;
			}
		}

		public void remove(String tag, int location) {
			status.removeMessage(tag+String.valueOf(location));
		}
	}
	
	public Descriptor whatNext(int passedSecs, Date currentTime) {
		if (dead)
		{
			SleepDescriptor sleepDescriptor = new SleepDescriptor("Я мертв, иду к мастерам");
			sleepDescriptor.timeout = 100;
			return sleepDescriptor;
		}
		return super.whatNext(passedSecs, currentTime);
	}
	
	public void reset()
	{
		super.reset();
		
		bindCommandWords();
		
		factions = Faction.createFactions(this);
		Pain = new PainAggregator();
	}

	private void bindCommandWords() {
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
		
		bindFixedPhoneWord("IDDQD", new MasterMenu(this));
		
		bindPrefixCommandWord(TOGGLE_GENE, new MasterToggleGene(this));
		bindPrefixCommandWord(ANALYZE_GENE, new AnalyzeGene(this));
		bindPrefixCommandWord(TOGGLE_NUTRIEN, new MasterToggleNutrien(this));
		
		bindFixedPhoneWord("MENU", new AlphaMenu(this));
		
		bindFixedPhoneWord("DEATH4EVER1251", new Killed(this));
		bindFixedPhoneWord("DEATH4EVER2456", new Killed(this));
		bindFixedPhoneWord("DEATH4EVER3028", new Killed(this));
		bindFixedPhoneWord("DEATH4EVER4453", new Killed(this));
		bindFixedPhoneWord("ANALYZEGENOM", new GeneAnalyzeMenu(this));
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
				new RemoveNutrient(this),
				new FoodDeficitAlpha(this),
				new TestInit(this),
				new AlphaMasterChemMenu(this),
				new MasterToggleNutrien(this),
				new NutrienMenu(this),
				new Killed(this),
				new KillSomeOne(this),
				new MasterToggleAlreadyGetChip(this),
				new IdeologyMasterStatus(this)
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
		ser.writeBool(sick);
		ser.writeBool(dead);
		ser.writeBool(alreadyGetChip);
		
		ser.writeString("faction_start");
		for (int i =0; i<factions.length; i++)
		{
			factions[i].serialize(ser);
		}
		ser.writeString(currentFaction == null ? "" : currentFaction.getName());
		Chemistry.serialize(ser);
		Pain.serialize(ser);
	}

	public void unserialize(ISerializer ser) {
		super.unserialize(ser);
		initCompleted = ser.readBool();
		gender = ser.readBool();
		inHouse = ser.readBool();
		sleeping = ser.readBool();
		wearingMask = ser.readBool();
		sick = ser.readBool();
		dead = ser.readBool();
		alreadyGetChip = ser.readBool();
		
		Utils.assert_(ser.readString().equals("faction_start"));
		for (int i =0; i<factions.length; i++)
		{
			factions[i].unserialize(ser);
		}
		
		String curFactionName = ser.readString();
		for (int i =0; i<factions.length; i++)
		{
			if (factions[i].getName().equals(curFactionName))
			{
				currentFaction = factions[i];
			}
		}
		
		Chemistry.unserialize(ser);
		Pain.unserialize(ser);
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
		boolean skipFirst = randomInt(2) > 1;
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
		return (int) ((10.0 + oxygenLevel * 5) * (randomInt(100)/100.0 + 1) * getOoxygenGeneCoef() * getDemoCoef());
	}

	private double getDemoCoef() {
		return DEMO_MODE ? 0.2 : 1;
	}

	private int getOoxygenGeneCoef() {
		IChemObject gene = Chemistry.getGene("o");
		if (gene.isStrictlyPresent())
		{
			return 3;
		}
		if (gene.isPresent())
		{
			return 2;
		}
		return 1;
	}

	public boolean isBadAtmoshere() {
		return !inHouse && !wearingMask;
	}

	public boolean canGetKillChip() {
	
		return isFanatic() && !alreadyGetChip;
	}
}
