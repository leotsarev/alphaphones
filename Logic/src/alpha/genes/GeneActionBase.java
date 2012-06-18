package alpha.genes;

import alpha.chem.ChemActionBase;
import alpha.chem.IChemObject;
import alpha.chem.Chemistry.Gene;
import phones.ProcessModelBase;
import phones.Utils;

public abstract class GeneActionBase extends  ChemActionBase {

	public GeneActionBase(ProcessModelBase model) {
		super(model);
	}

	protected Gene getTargetGene() {
		IChemObject targetGene = getChemObj();
		Utils.assert_(targetGene instanceof Gene, getChemName() + "isn't gene!");
		return (Gene) targetGene;
	}
}
