package alpha;

import phones.InteractionModel.Descriptor;
import phones.ProcessModelBase;

public class IdeologyChange extends ProcessModelBase.Process {

	public IdeologyChange(AlphaIM model) {
		super(model);
	}
	
	public IdeologyChange(AlphaIM model, int coord, int sign) {
		super(model);
		setIntArg("coord", coord);
		setIntArg("sign", sign);
	}

	public Descriptor handle() {
		int coord = getIntArg("coord");
		int sign = getIntArg("sign");
		((AlphaIM)model).coords[coord] += sign;
		scheduleNow(new UpdateFaction(model));
		return null;
	}

	public String getName() {
		return "IdeologyChange";
	}
}
