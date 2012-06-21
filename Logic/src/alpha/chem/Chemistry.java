package alpha.chem;

import java.util.Vector;

import phones.ISerializer;
import phones.Utils;

public class Chemistry {
	public static final String XI = "XI";
	private static final String PI = "PI";
	private static final String PI1 = PI + 1;
	public static final String KAPPA = "Kappa";
	public static final String EPSILON = "Epsilon";
	public static final String YOTA = "Yota";
	public static final String MU = "Mu";
	public static final String GAMMA = "Gamma";
	public static final String GAMMA2 = GAMMA+"2";
	public static final String GAMMA1 = GAMMA+"1";
	public static final String RO = "Ro";
	public static final String DELTA = "Delta";
	public static final String PHI = "Phi";
	public static final String CHI = "Chi";
	public static final String BETA = "Beta";
	public static final String ALPHA = "Alpha";
	private static final String PSEUDO_NUTRIEN = "PseudoNutrien";
	public static final String NUTRIEN = "Nutrien";
	private static final String GENE = "GENE_";

	private IChemObject[] data;

	
	public static final int NUTRIEN_VANISH_MINS = 3 * 60;
	
	public Chemistry()
	{
		data = new IChemObject[]{
				new Gene("A", "CAGGATCTGTTGCACTGGCC", "CAGGATCGGTTGCACTGGCC", Gene.AA),
				new Gene("B", "ACTGGTGGTCTTAGCTTTTG", "ACTGGTAGTCTTAGCTTTTG", Gene.AA),
				new Gene("O", "AAGCTTTCGTAAGTCTTCAC", "AAGCTTTCGTAAGTCTTCAC", Gene.aa),
				new Gene("P", "TATAGCCTTCACCGAGTCAG", "AATAGCCTTCACCGAGTCAG", Gene.aa),
				new Gene("X", "TTGCACGCATGGGCAATAAT", "TTGCACGCATGGGCAATAAA", Gene.Aa),
				new Gene("R", "TCACCGTGGCCAGTCCTAAC", "TCACCGTGGCCAGTCCTAAA", Gene.aa),
				new Gene("G", "CACCGAGTTTGCACGGGTCA", "CACCGAGTTTGCACGGGTCC", Gene.AA),
				new Gene("D", "CTTCACTAGCTTGCTTCCTC", "CTTCACTAGTTTGCTTCCTC", Gene.AA),
				new Gene("F", "GGGATCTAAGACACCTATGG", "GGTATCTAAGACACCTATGG", Gene.AA),
				new Gene("H", "CACCTACGTGGTTAATACTA", "CACCTACGTGGCTAATACTA", Gene.AA),
				new Gene("K", "ACGGTCACTCGGACCGAGAC", "TCGGTCAATCGGAGCGAGGC", Gene.aa),
				new Gene("E", "TCTAGCGGTAGCATGCGCTG", "TGTAGCGGTAGCATGCGCTG", Gene.aa),
				new Gene("Y", "GTGAACCTTGACCTGCTACG", "GTGAACATTGACCTGCTACG", Gene.aa),
				new Gene("L", "TACATAGCCTGGACTAGCCA", "TACATAGCCTGGAGTAGCCA", Gene.Aa),
				new Gene("M", "CATAGACTGGTACGCTGTAA", "CATAGACTGGTACGCTGCAA", Gene.AA),
				new Gene("U", "TGCTGCCGTAACACTACGAC", "TGATGCCGTAACACTACGAC", Gene.aa),
				new Gene("N", "CAATACGTGACTTTGACCTA", "CAATACGTGACTTGGACCTA", Gene.Aa),
				new Gene("T", "TGGTACCATCAGTCACCAAG", "TGGTACCATCAGTCACTAAG", Gene.aa),
				new Gene("S", "GCATGCACGGTCTAGTCAGG", "GCATGCACGGTCTCGTCAGG", Gene.aa),
				new Gene("C", "ATATGCCTGGGACCTTCATG", "ATATGCCTGGGACATTCATG", Gene.Aa),
				new Gene("I", "TTGACCCACGGGCTTGAATC", "TTGACCCACGGGCTTGAATC", Gene.aa),
				
				new Gene("q", "TCAATCACCTCCGCTCACGC", "GGGGGGGGGGTTTTTTTTTT", Gene.aa),
				new Gene("z", "CTGCAGGTACGACTCCGGAT", "AAAAAAAAAATTTTTTTTTT", Gene.aa),
				new Gene("w", "AGAGTCAACTGCGTCAGCAA", "CCCCCCCCCCTTTTTTTTTT", Gene.aa),
				
				new Gene("v1", "ATGAGGTCAGTAGGAAGTTT", "TTTTTTTTTTGGGGGGGGGG", Gene.aa),
				new Gene("v2", "AGGTCGATGAATCTACGAAT", "TTTTTTTTTTAAAAAAAAAA", Gene.aa),
				new Gene("v3", "CGCAATACGATGCCTCCGCT", "TTTTTTTTTTCCCCCCCCCC", Gene.aa),
				
				//Nutriens
				new Nutrien(ALPHA),
				new Nutrien(BETA),
				new Nutrien(GAMMA),
				new Nutrien(DELTA),
				new Nutrien(PHI),
				new Nutrien(CHI),
				
				new Substance(ALPHA+"1"){
					public boolean isPresent()
					{
						return hasGene("a") && hasNutrienOrPseudoNutrien(ALPHA);
					}
				},
				
				new Substance(BETA+"11") {
					public boolean isPresent() {
						return has(BETA +"1") && has(GAMMA1);
					}
				},
				
				new Substance(GAMMA2) {
					public boolean isPresent() {
						return (hasGene("g") && hasNutrienOrPseudoNutrien(GAMMA))
								|| (hasGene("t") && has(GAMMA1));
					}
				},
				
				new Substance(DELTA+"11") {
					public boolean isPresent() {
						return (has(DELTA+"1") && has(PHI+"1")) || has(RO+"2");
					}
				},
				
				new Substance(PHI+"11") {
					public boolean isPresent() {
						return has(PHI+1);
					}
				},
				
				new Substance(CHI+"1") {
					public boolean isPresent() {
						return hasGene("h") && hasNutrienOrPseudoNutrien(CHI);
					}
				},
				
				new Substance(BETA+"1") {
					public boolean isPresent() {
						return hasGene("b") && hasNutrienOrPseudoNutrien(BETA);
					}
				},
				
				new Substance(GAMMA1) {
					public boolean isPresent() {
						boolean var1 = (hasGene("g") || hasGene("s")) &&  hasNutrienOrPseudoNutrien(GAMMA);
						boolean var2 = hasGene("u") && has(ALPHA + "1");
						return var1 || var2;
					}
				},
				
				new Substance(DELTA + "1") {
					public boolean isPresent() {
						return hasGene("d") && !has(MU + 1) && hasNutrienOrPseudoNutrien(DELTA);
					}
				},
				
				new Substance(MU + "1") {
					public boolean isPresent() {
						return hasGene("n") && hasGene("m") && !hasGene("l");
					}
				},
				
				new Substance(PHI + 1) {
					public boolean isPresent() {
						return hasGene("f") && hasNutrienOrPseudoNutrien(PHI) && has(DELTA + 1);
					}
				},
				
				new Substance(YOTA + 1) {
					public boolean isPresent() {
						return hasGene("y") && !hasGene("l");
					}
				},
				
				new Substance(EPSILON + 1) {
					public boolean isPresent() {
						return getGene("e").isStrictlyPresent() && has(YOTA+1) && hasGene("u");
					}
				},
				
				new Substance(KAPPA + 1) {
					public boolean isPresent() {
						return hasGene("k") && !hasGene("x") && !hasGene("g") && has(PI1);
					}
				},
				
				new Substance(PI1) {
					public boolean isPresent() {
						return hasGene("p") && !hasGene("x");
					}
				},
				
				new Substance(XI + 1) {
					public boolean isPresent() {
						return hasGene("c") && has(RO + 1);
					}
				},
				
				new Substance(RO + 1) {
					public boolean isPresent() {
						return hasGene("r") && !hasGene("o");
					}
				},
				
				new Substance(RO + 2) {
					public boolean isPresent() {
						return has(RO+1) && hasNutrienOrPseudoNutrien(BETA) && hasNutrienOrPseudoNutrien(CHI);
					}
				},
				
				new PseudoNutrien(ALPHA) {
					public boolean isPresent() {
						return hasGene("v1") || (hasNutrien(GAMMA) && has(EPSILON+1));
					}
				},
				
				new PseudoNutrien(GAMMA) {
					public boolean isPresent() {
						return hasGene("v1");
					}
				},
				
				new PseudoNutrien(BETA) {
					public boolean isPresent() {
						return hasGene("v2");
					}
				},
				
				new PseudoNutrien(CHI) {
					public boolean isPresent() {
						return hasGene("v2");
					}
				},
				
				new PseudoNutrien(DELTA) {
					public boolean isPresent() {
						return hasGene("v3");
					}
				},
				
				new PseudoNutrien(PHI) {
					public boolean isPresent() {
						return hasGene("v3");
					}
				},
		};
	}
	
	private abstract class PseudoNutrien extends Substance
	{
		public PseudoNutrien(String name) {
			super(PSEUDO_NUTRIEN +  name);
		}
		
	}
	
	private abstract class Substance implements IChemObject
	{
		private final String name;

		public Substance(String name)
		{
			this.name = name;
		}
		
		public final boolean isNotPresent() {
			return !isPresent();
		}

		public final boolean isStrictlyPresent() {
			return isPresent();
		}

		public final String getName() {
			return name;
		}
		
		public final String toString()
		{
			return name + (isPresent() ? "+" : "-");
		}
		
		public int getNumericValue() {
			return isPresent() ? 1: 0;
		}
	}
	
	public void serialize(ISerializer ser)
	{
		for (int i = 0; i < data.length; i++)
		{
			if (data[i] instanceof IPersistentChemObject)
			{
				((IPersistentChemObject)data[i]).serialize(ser);
			}
		}
	}
	
	public void unserialize(ISerializer ser)
	{
		for (int i = 0; i < data.length; i++)
		{
			if (data[i] instanceof IPersistentChemObject)
			{
				((IPersistentChemObject)data[i]).unserialize(ser);
			}
		}
	}
	
	public IChemObject getByName(String name) {
		for (int i = 0; i <data.length; i++)
		{
			if (data[i].getName().equals(name))
			{
				return data[i];
			}
		}
		return null;
	}
		
	public final class Nutrien implements IPersistentChemObject {
		
		private boolean nutrienValue;
		private final String nutrienName;
		
		private Nutrien(String nutrienName)
		{
			this.nutrienName = nutrienName;	
		}

		public boolean isPresent() {
			return !isNotPresent();
		}

		public boolean isNotPresent() {
			return !nutrienValue;
		}

		public boolean isStrictlyPresent() {
			return 	nutrienValue;
		}

		public String getName() {
			return Chemistry.NUTRIEN + nutrienName;
		}

		public void serialize(ISerializer ser) {
			ser.writeBool(nutrienValue);
		}

		public void unserialize(ISerializer ser) {
			nutrienValue = ser.readBool();
		}

		public void masterToggle() {
			nutrienValue = !nutrienValue;
		}

		public void setPresent() {
			nutrienValue = true;
		}

		public void setNotPresent() {
			nutrienValue = false;
		}

		public void setHalfPresent() {
			Utils.assert_(false, "Nutrien can't be halfset!");
		}
		
		public String toString()
		{
			return getName() + (nutrienValue ? "+" : "-");
		}
		
		public boolean eligbleForAnalysis() {
			return false;
		}

		public int getNumericValue() {
			return nutrienValue ? 1: 0;
		}
		
	}
	
	public final class Gene implements IPersistentChemObject {
		
		private static final int GENE_MIN_VALUE = 0;
		private static final int aa = 0;
		private static final int Aa = 1;
		private static final int AA = 2;
		private static final int GENE_MAX_VALUE = 2;
		private int geneValue = GENE_MIN_VALUE;
		
		private final String myChar;
		private final String dominantAnalysis;
		private final String recessiveAnalysis;
		
		private Gene(String myChar, String dominantAnalysis, String recessiveAnalysis, int geneValue)
		{
			this.myChar = myChar.toLowerCase();
			this.dominantAnalysis = dominantAnalysis;
			this.recessiveAnalysis = recessiveAnalysis;
			this.geneValue = geneValue;
		}
		
		private Gene (String myChar, String dominantAnalysis, String recessiveAnalysis)
		{
			this (myChar, dominantAnalysis, recessiveAnalysis, aa);
		}

		public boolean isPresent() {
			return !isNotPresent();
		}

		public boolean isNotPresent() {
			return geneValue == aa;
		}

		public boolean isStrictlyPresent() {
			return geneValue == AA;
		}

		public String getName() {
			return Chemistry.GENE + myChar;
		}

		public final void serialize(ISerializer ser) {
			ser.writeInt(geneValue);
		}

		public final void unserialize(ISerializer ser) {
			geneValue = ser.readInt();
			checkGeneValue(geneValue);
		}

		public void masterToggle() {
			if (geneValue == GENE_MAX_VALUE)
			{
				geneValue = GENE_MIN_VALUE;
			}
			else{
				geneValue++;
			}
		}

		public void setPresent() {
			geneValue = AA;
		}

		public void setNotPresent() {
			geneValue = aa;	
		}

		public void setHalfPresent() {
			geneValue = Aa;
		}
		
		private void checkGeneValue(int geneValue) {
			Utils.assert_(geneValue >= GENE_MIN_VALUE);
			Utils.assert_(geneValue <= GENE_MAX_VALUE);
		}
		
		public String toString()
		{
			switch (geneValue) {
			case aa:	
					return myChar + myChar;
			case Aa:
				return myChar.toUpperCase() + myChar;
			case AA:
				return myChar.toUpperCase() + myChar.toUpperCase();
			default:
				Utils.assert_(false);
				break;
			}
			return null;
		}
		
		public final String getAnalyzeResult() {
			switch (geneValue) {
			case aa:	
					return getRecessiveAnalysis() + getRecessiveAnalysis();
			case Aa:
				return getDominantAnalysis() + getRecessiveAnalysis();
			case AA:
				return getDominantAnalysis() + getDominantAnalysis();
			default:
				Utils.assert_(false);
				break;
			}
			return null;
		}
		
		
		protected String getDominantAnalysis() {
			return dominantAnalysis;
		}

		protected String getRecessiveAnalysis() {
			return recessiveAnalysis;
		}

		public boolean eligbleForAnalysis() {
			return isPresent() || !isSecretGene();
		}

		private boolean isSecretGene() {
			final String[] secretGenes = new String[]{"q", "w", "z", "v1", "v2", "v3"};
			for (int i = 0; i< secretGenes.length; i++)
			{
				if (secretGenes[i].equals(myChar))
				{
					return true;
				}
			}
			return false;
		}

		public void setValue(int value) {
			switch (value) {
			case 0:
				setNotPresent();
				break;
			case 1:
				setHalfPresent();
			case 2:
				setPresent();
			default:
				break;
			}
		}

		public int getNumericValue() {
			return geneValue;
		}
	}

	public Gene[] getGeneArray() {
		Vector interim = new Vector();
		for (int i = 0; i < data.length; i++)
		{
			if (data[i] instanceof Gene)
			{
				interim.addElement(data[i]);
			}
		}
		Gene[] result = new Gene[interim.size()];
		for (int i = 0; i < interim.size(); i++)
		{
			result[i] = (Gene) interim.elementAt(i);
		}
		return result;
	}
	
	public Nutrien[] getNutrienArray() {
		Vector interim = new Vector();
		for (int i = 0; i < data.length; i++)
		{
			if (data[i] instanceof Nutrien)
			{
				interim.addElement(data[i]);
			}
		}
		Nutrien[] result = new Nutrien[interim.size()];
		for (int i = 0; i < interim.size(); i++)
		{
			result[i] = (Nutrien) interim.elementAt(i);
		}
		return result;
	}

	public IChemObject[] getSubstanceArray() {
		Vector interim = new Vector();
		for (int i = 0; i < data.length; i++)
		{
			if (data[i] instanceof Substance)
			{
				interim.addElement(data[i]);
			}
		}
		IChemObject[] result = new IChemObject[interim.size()];
		for (int i = 0; i < interim.size(); i++)
		{
			result[i] = (IChemObject) interim.elementAt(i);
		}
		return result;
	}

	public boolean isCorrectNamePrefix(String suffix) {
		for (int i = 0; i <data.length; i++)
		{
			if (data[i].getName().startsWith(suffix))
			{
				return true;
			}
		}
		return false;
	}

	public IChemObject getGene(String geneName) {
		return getByName(GENE + geneName);
	}

	private boolean hasNutrienOrPseudoNutrien(String nutrienName) {
		return hasNutrien(nutrienName) || hasPseudoNutrien(nutrienName);
	}

	private boolean hasPseudoNutrien(String nutrienName) {
		return has(PSEUDO_NUTRIEN + nutrienName);
	}

	private boolean hasNutrien(String nutrienName) {
		return has(NUTRIEN + nutrienName);
	}

	public boolean has(String chemObjName) {
		return getByName(chemObjName).isPresent();
	}

	private boolean hasGene(String geneName) {
		return getGene(geneName).isPresent();
	}

	public int getImmunityValue() {
		//TODO Так переводится формула с гуманитарного на человеческий. Но! Гарантии никакой. Надо перепроверить
		return numericValue(GENE + "l")
				+ numericValue(PI1) 
				- 2 * numericValue(GAMMA2);
	}

	private int numericValue(String name) {
		return getByName(name).getNumericValue();
	}
}
