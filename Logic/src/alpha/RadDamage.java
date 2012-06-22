package alpha;

import phones.IPrefixHandler;
import phones.InteractionModel.Descriptor;
import phones.ProcessModelBase;

public class RadDamage extends AlphaProcess implements IPrefixHandler {

	public RadDamage(ProcessModelBase model) {
		super(model);
	}

	public void setSuffixValue(String suffix) {
		setIntArg("damage", Integer.valueOf(suffix.substring(0,1)).intValue() * 6);
	}

	public boolean isValidSuffix(String suffix) {
		return suffix.length() == 4;
	}

	public boolean isStartOfSuffix(String suffix) {
		return suffix.length() < 4;
	}

	public Descriptor handle() {
		getAlphaModel().radDamage += getIntArg("damage");
		return nothing();
	}

	public String getName() {
		return "RadDamage";
	}

}
