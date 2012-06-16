package alpha.oxygen;

import alpha.AlphaProcess;
import phones.ProcessModelBase;

public abstract class OxygenProcess extends AlphaProcess {

	public OxygenProcess(ProcessModelBase model) {
		super(model);
	}

	protected static final String BREATH = "breath"; 

}
