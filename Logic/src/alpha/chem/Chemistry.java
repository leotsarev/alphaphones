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
				new Gene("A", "_", "_"),
				new Gene("B", "_", "_"),
				new Gene("O", "_", "_"),
				new Gene("P", "_", "_"),
				new Gene("X", "_", "_"),
				new Gene("R", "_", "_"),
				new Gene("G", "_", "_"),
				new Gene("D", "_", "_"),
				new Gene("F", "_", "_"),
				new Gene("H", "_", "_"),
				new Gene("K", "_", "_"),
				new Gene("E", "_", "_"),
				new Gene("Y", "_", "_"),
				new Gene("L", "_", "_"),
				new Gene("M", "_", "_"),
				new Gene("U", "_", "_"),
				new Gene("N", "_", "_"),
				new Gene("T", "_", "_"),
				new Gene("S", "_", "_"),
				new Gene("C", "_", "_"),
				new Gene("I", "_", "_"),
				//TODO q,z,w
				
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
		private static final int GENE_aa = 0;
		private static final int GENE_Aa = 1;
		private static final int GENE_AA = 2;
		private static final int GENE_MAX_VALUE = 2;
		private int geneValue = GENE_MIN_VALUE;
		
		private final String myChar;
		private final String dominantAnalysis;
		private final String recessiveAnalysis;
		
		private Gene(String myChar, String dominantAnalysis, String recessiveAnalysis)
		{
			this.myChar = myChar.toLowerCase();
			this.dominantAnalysis = dominantAnalysis;
			this.recessiveAnalysis = recessiveAnalysis;
		}

		public boolean isPresent() {
			return !isNotPresent();
		}

		public boolean isNotPresent() {
			return geneValue == GENE_aa;
		}

		public boolean isStrictlyPresent() {
			return geneValue == GENE_AA;
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
			geneValue = GENE_AA;
		}

		public void setNotPresent() {
			geneValue = GENE_aa;	
		}

		public void setHalfPresent() {
			geneValue = GENE_Aa;
		}
		
		private void checkGeneValue(int geneValue) {
			Utils.assert_(geneValue >= GENE_MIN_VALUE);
			Utils.assert_(geneValue <= GENE_MAX_VALUE);
		}
		
		public String toString()
		{
			switch (geneValue) {
			case GENE_aa:	
					return myChar + myChar;
			case GENE_Aa:
				return myChar.toUpperCase() + myChar;
			case GENE_AA:
				return myChar.toUpperCase() + myChar.toUpperCase();
			default:
				Utils.assert_(false);
				break;
			}
			return null;
		}
		
		public final String getAnalyzeResult() {
			switch (geneValue) {
			case GENE_aa:	
					return getRecessiveAnalysis() + getRecessiveAnalysis();
			case GENE_Aa:
				return getDominantAnalysis() + getRecessiveAnalysis();
			case GENE_AA:
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
