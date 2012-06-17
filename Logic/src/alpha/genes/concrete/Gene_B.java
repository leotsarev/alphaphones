package alpha.genes.concrete;

import alpha.genes.Gene;
import alpha.genes.GeneContainer;

public class Gene_B extends Gene
{
	public Gene_B(GeneContainer container)
	{
		super(container, 'b');
	}

	protected String getDominantAnalysis() {
		return "ACTGGTGGTCTTAGCTTTTG";
	}

	protected String getRecessiveAnalysis() {
		return "ACTGGTAGTCTTAGCTTTTG";
	}
}