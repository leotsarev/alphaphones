package alpha.genes;

import alpha.genes.concrete.*;
import phones.ISerializer;
import phones.Utils;

public abstract class Gene
{
	private static final int GENE_MIN_VALUE = 0;
	private static final int GENE_aa = 0;
	private static final int GENE_Aa = 1;
	private static final int GENE_AA = 2;
	private static final int GENE_MAX_VALUE = 2;
	
	private int geneValue = GENE_AA;
	private final GeneContainer containter;
	private final char myChar;

	protected Gene(GeneContainer containter, char myChar)
	{
		this.containter = containter;
		this.myChar = myChar;
	}
	
	public void serialize(ISerializer ser) {
		ser.writeInt(geneValue);
	}

	public void unserialize(ISerializer ser) {
		geneValue = ser.readInt();
		checkGeneValue(geneValue);
	}
	
	public String getValueString()
	{
		return convertGeneToString(geneValue);
	}
	
	/** Ген с учетом того, выключают ли его другие гены
	 * */
	public int getCondition()
	{
		return geneValue;
	}
	
	public String getConditionString()
	{
		return convertGeneToString(getCondition());
	}

	private String convertGeneToString(int val) {
		String result = getMyChar();
		switch (val) {
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
		return ""+ myChar;
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
	
	protected abstract String getDominantAnalysis();

	protected abstract String getRecessiveAnalysis();

	static Gene[] createAll(GeneContainer container) {
		Gene[] result = {
				new Gene_A(container),
				new Gene_B(container)
		};
		return result;
	}
	
	private static void checkGeneValue(int geneValue) {
		Utils.assert_(geneValue >= GENE_MIN_VALUE);
		Utils.assert_(geneValue <= GENE_MAX_VALUE);
	}

}