package phones;

import java.text.DateFormat;
import java.util.Date;

public class SampleIM extends InteractionModel {

	public void reset() {
	}

	public Descriptor whatNext(int timePassed, Date currentTime) {
		TickDescriptor result = new TickDescriptor();
		result.timeout = 60;
		String time = DateFormat.getTimeInstance().format(currentTime); 
		result.status = "It's " + time + ".";
		return result;
	}

	public int codeStatus(String code) {
		if (code.length() > 3)
			return TOO_LONG_CODE;
		return POTENTIAL_PREFIX;
	}

	public void receiveTickTimeout() {
	}

	public void receiveCode(String code) {
		Utils.assert_(false, "no codes");
	}

	public void receiveMenuTimeout() {
		Utils.assert_(false, "no menus");
	}

	public void receiveMenuOption(String option) {
		Utils.assert_(false, "no menus");
	}

	public boolean needSave() {
		return false;
	}

}
