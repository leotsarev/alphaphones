package alpha.chem;

import phones.IProcess;

public interface IChemAction extends IProcess {
	void setChem(String chemName);
	void setChem(IChemObject chemObj);
}
