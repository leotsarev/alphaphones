package alpha;

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
	
	public final GeneContainer Genes = new GeneContainer();
	public boolean initCompleted;
	private boolean gender;
	static final String TOGGLE_GENE = "toggle_gene_";
	public static final String ANALYZE_GENE = "analyze_gene_";
	
	public static class GeneContainer
	{
		public static final int GENE_MIN_VALUE = 0;
		public static final int GENE_aa = 0;
		public static final int GENE_Aa = 1;
		public static final int GENE_AA = 2;
		public static final int GENE_MAX_VALUE = 2;
		
		private Gene[] genes = new Gene[geneChars.length];
		private static final char[] geneChars = 
				new char[]
						{
						'a', 'b', 'g', 'd', 'f','h','k','e','x','p','r','y','l','m','u','n','t','s'
						};
		
		public GeneContainer()
		{
			for (int i = 0; i<geneChars.length;i++)
			{
				genes[i] = new Gene(i);
			}
		}
		
		public class Gene
		{
			private final int geneNum;
			private int geneValue = GENE_AA;

			private Gene(int geneNum)
			{
				checkGeneNum(geneNum);
				this.geneNum = geneNum;
			}

			public void serialize(ISerializer ser) {
				ser.writeInt(geneValue);
			}

			public void unserialize(ISerializer ser) {
				geneValue = ser.readInt();
				checkGeneValue(geneValue);
			}
			
			public String toString()
			{
				String result = getMyChar();
				switch (geneValue) {
				case GENE_aa:	
						return result + result;
				case GENE_Aa:
					return result.toUpperCase() + result;
				case GENE_AA:
					return result.toUpperCase() + result.toUpperCase();
				default:
					Utils.assert_(false);
					break;
				}
				return null;
			}

			private String getMyChar() {
				return "" + geneChars[geneNum];
			}

			public void toggle() {
				if (geneValue == GENE_MAX_VALUE)
				{
					geneValue = GENE_MIN_VALUE;
				}
				else{
					geneValue++;
				}
			}

			public String getName() {
				return getMyChar();
			}

			public String getAnalyzeResult() {
				return "ANALYZE RESULT SHOULD UPDATE " + toString();
			}
		}
		
		public Gene getGene(char geneName) {
			return genes[findGenePos(geneName)];
		}
		
		public Gene getGene(String geneName) {
			Utils.assert_(geneName.length() == 1);
			return getGene(geneName.charAt(0));
		}

		private void checkGeneValue(int geneValue) {
			Utils.assert_(geneValue >= GENE_MIN_VALUE);
			Utils.assert_(geneValue <= GENE_MAX_VALUE);
		}

		private void checkGeneNum(int geneNum) {
			Utils.assert_(geneNum >= 0);
			Utils.assert_(geneNum < geneChars.length);
		}
		
		public void serialize (ISerializer ser)
		{
			for (int i = 0; i<genes.length;i++)
			{
				genes[i].serialize(ser);
			}
		}
		
		public void unserialize (ISerializer ser)
		{
			for (int i = 0; i<genes.length;i++)
			{
				genes[i].unserialize(ser);
			}
		}

		public Gene[] asArray() {
			return genes;
		}

		public boolean isValidGeneName(String suffix) {
			if (suffix.length() != 1)
			{
				return false;
			}
			char ch = suffix.toLowerCase().charAt(0);
			return findGenePos(ch) != -1;
		}

		private int findGenePos(char ch) {
			for (int i = 0; i<geneChars.length;i++)
			{
				if (geneChars[i] == ch)
				{
					return i;
				}
			}
			return -1;
		}

	}

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
				new GeneAnalyzeMenu(this)
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
		for (int i =0; i<factions.length; i++)
		{
			factions[i].serialize(ser);
		}
		Genes.serialize(ser);
	}

	public void unserialize(ISerializer ser) {
		super.unserialize(ser);
		initCompleted = ser.readBool();
		gender = ser.readBool();
		for (int i =0; i<factions.length; i++)
		{
			factions[i].unserialize(ser);
		}
		Genes.unserialize(ser);
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

}
