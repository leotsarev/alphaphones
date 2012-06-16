package phones.Sample;

//import java.text.DateFormat;
import java.util.Date;

import phones.ISerializer;
import phones.InteractionModel;

public class SampleIM extends InteractionModel {

	boolean state;
	public void reset() {
		state = true;
	}

	public Descriptor whatNext(int timePassed, Date currentTime) {
		state = !state;
		if (state && false) {
			SleepDescriptor result = new SleepDescriptor();
			result.timeout = 60;
			String time = "fucking jme";//DateFormat.getTimeInstance().format(currentTime); 
			result.status = "It's " + time + ".";
			return result;
		} else {
			MenuDescriptor result = new MenuDescriptor();
			result.timeout = 30;
			result.menuHeader = "Choose one option:";
			result.addItem("Apples","APPLE");
			result.addItem("bananas", "BANANA");
			result.timeoutCommand = "TIMEOUT";
			return result;
		}
	}

	public int checkCommandWord(String commandWord) {
		if (commandWord.equals("APPLE"))
			return CODE_VALID;
		if (commandWord.equals("BANANA"))
			return CODE_VALID;
		if (commandWord.equals("TIMEOUT"))
			return CODE_VALID;
		if (commandWord.equals("123"))
			return CODE_VALID;
		if (commandWord.length() > 3)
			return CODE_UNKNOWN;
		return CODE_PREFIX;
	}

	public void assertCommandWord(String code) {
		System.out.println("SampleIM: code "+code+" received.");
		
	}

	public void unserialize(ISerializer serializedData) {
		// TODO Auto-generated method stub
		
	}

	public void serialize(ISerializer serializedData) {
		// TODO Auto-generated method stub
	}

}
