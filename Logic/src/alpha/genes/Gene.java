package alpha.genes;

import phones.ISerializer;
import phones.Utils;

public class Gene
{
	public static final int GENE_MIN_VALUE = 0;
	public static final int GENE_aa = 0;
	public static final int GENE_Aa = 1;
	public static final int GENE_AA = 2;
	public static final int GENE_MAX_VALUE = 2;
	
	private final int geneNum;
	private int geneValue = GENE_AA;
	private static final char[] geneChars = 
	new char[]
			{
			'a', 'b', 'g', 'd', 'f','h','k','e','x','p','r','y','l','m','u','n','t','s'
			};

	private Gene(int geneNum)
	{
		Utils.assert_(geneNum >= 0);
		Utils.assert_(geneNum < Gene.geneChars.length);
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
		return "" + Gene.geneChars[geneNum];
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

	static int findGenePos(char ch) {
		for (int i = 0; i<Gene.geneChars.length;i++)
		{
			if (Gene.geneChars[i] == ch)
			{
				return i;
			}
		}
		return -1;
	}

	public static boolean isValidGeneName(String suffix) {
		if (suffix.length() != 1)
		{
			return false;
		}
		char ch = suffix.toLowerCase().charAt(0);
		return Gene.findGenePos(ch) != -1;
	}

	static Gene[] createAll() {
		Gene[] result = new Gene[Gene.geneChars.length];
		for (int i = 0; i<Gene.geneChars.length;i++)
		{
			result[i] = new Gene(i);
		}
		return result;
	}
	
	private static void checkGeneValue(int geneValue) {
		Utils.assert_(geneValue >= GENE_MIN_VALUE);
		Utils.assert_(geneValue <= GENE_MAX_VALUE);
	}

}