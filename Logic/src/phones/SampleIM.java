package phones;

import java.text.DateFormat;
import java.util.Date;

public class SampleIM extends InteractionModel {

	boolean state;
	public void reset() {
		state = false;
	}

	public Descriptor whatNext(int timePassed, Date currentTime) {
		state = !state;
		if (state) {
			TickDescriptor result = new TickDescriptor();
			result.timeout = 60;
			String time = DateFormat.getTimeInstance().format(currentTime); 
			result.status = "It's " + time + ".";
			return result;
		} else {
			MenuDescriptor result = new MenuDescriptor();
			result.timeout = 30;
			result.question = "Choose one option:";
			String[] options = { "Apples", "Bananas" };
			result.options = options;
			return result;
		}
	}

	public int codeStatus(String code) {
		if (code.length() > 3)
			return TOO_LONG_CODE;
		if (code.equals("123"))
			return VALID_CODE;
		return POTENTIAL_PREFIX;
	}

	public void receiveTickTimeout() {
	}

	public void receiveCode(String code) {
		System.out.println("SampleIM: code "+code+" received.");
	}

	public void receiveMenuTimeout() {
		System.out.println("SampleIM: no option was chosen.");
	}

	public void receiveMenuOption(int option) {
		System.out.println("SampleIM: option "+option+" received.");
	}

	public boolean needSave() {
		return false;
	}

}
