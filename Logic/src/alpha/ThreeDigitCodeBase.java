package alpha;

import phones.IPrefixHandler;
import phones.ProcessModelBase;
import phones.Utils;

public abstract class ThreeDigitCodeBase extends AlphaProcess implements IPrefixHandler {

	private static final int SUFFIX_LENGTH = 6;

	public ThreeDigitCodeBase(ProcessModelBase model) {
		super(model);
	}

	public final void setSuffixValue(String suffix) {
		Utils.assert_(isValidSuffix(suffix));
		setIntArg("code", getCodeFromSuffix(suffix));
	}

	public final boolean isValidSuffix(String suffix) {
		if (suffix.length() != SUFFIX_LENGTH)
		{
			return false;
		}
		int foodFlag = getCodeFromSuffix(suffix);
		int check = Integer.valueOf(suffix.substring(3, 6)).intValue();
		
		return Utils.quickHash(foodFlag) == check;
	}

	private int getCodeFromSuffix(String suffix) {
		return Integer.valueOf(suffix.substring(0, 3)).intValue();
	}

	public final boolean isStartOfSuffix(String suffix) {
		return suffix.length() < SUFFIX_LENGTH;
	}

	protected final int getCode() {
		return getIntArg("code");
	}

}