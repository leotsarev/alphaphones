package alpha.chem;

import java.util.Vector;

import phones.ISerializer;
import phones.Utils;

public class Chemistry {
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
				
				new Gene("q", "ANALYSIS_FAILED_CALL_MASTER", "ANALYSIS_FAILED_CALL_MASTER", Gene.aa),
				new Gene("z", "ANALYSIS_FAILED_CALL_MASTER", "ANALYSIS_FAILED_CALL_MASTER", Gene.aa),
				new Gene("w", "ANALYSIS_FAILED_CALL_MASTER", "ANALYSIS_FAILED_CALL_MASTER", Gene.aa),
				
				//Nutriens
				new Nutrien("Alpha"),
				new Nutrien("Beta"),
				new Nutrien("Gamma"),
				new Nutrien("Delta"),
				new Nutrien("Phi"),
				new Nutrien("Chi"),
		};
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
			return nutrienName;
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

		public String getDeficitName() {
			return "FoodDeficit" + getName();
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
			return "GENE_" + myChar;
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
}
