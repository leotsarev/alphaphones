package alpha.genes.concrete;

import alpha.genes.Gene;
import alpha.genes.GeneContainer;

public class Gene_A extends Gene
{
	public Gene_A(GeneContainer container)
	{
		super(container, 'a');
	}

	protected String getDominantAnalysis() {
		return "CAGGATCTGTTGCACTGGCC";
	}

	protected String getRecessiveAnalysis() {
		return "CAGGATCGGTTGCACTGGCC";
	}
}