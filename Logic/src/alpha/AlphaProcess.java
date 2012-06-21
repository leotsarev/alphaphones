package alpha;

import phones.ProcessModelBase;
import phones.ProcessModelBase.Process;

public abstract class AlphaProcess extends Process {

	public AlphaProcess(ProcessModelBase model) {
		super(model);
	}

	protected AlphaIM getAlphaModel() {
		return (AlphaIM)model;
	}

	protected String gender(String m, String f) {
		return getAlphaModel().gender ? m: f;
	}

}
