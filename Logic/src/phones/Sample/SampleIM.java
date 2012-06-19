package phones.Sample;

//import java.text.DateFormat;
import java.util.Date;

import phones.ISerializer;
import phones.InteractionModel;

public class SampleIM extends InteractionModel {

	boolean state;
	String command;
	
	public void reset() {
		state = true;
		command = null;
	}

	public Descriptor whatNext(int timePassed, Date currentTime) {
		if ("123".equals(command)) {
			command = null;
			MenuDescriptor result = new MenuDescriptor();
			result.timeout = 10;
			result.menuHeader = "Choose one option:";
			result.addItem("Apples","APPLE");
			result.addItem("bananas", "BANANA");
			result.timeoutCommand = "TIMEOUT";
			result.alarm = ALARM_SINGLE;
			return result;
		}
		
		SleepDescriptor result = new SleepDescriptor();
		result.timeout = 10;
		if (command != null) {
			result.status = command+" was entered!";
			command = null;
		} else {
			String time = "fucking jme";//DateFormat.getTimeInstance().format(currentTime); 
			result.status = "It's " + time + ".";
		}
		return result;
		
		/*state = !state;
		if (state) {
			SleepDescriptor result = new SleepDescriptor();
			result.timeout = 10;
			String time = "fucking jme";//DateFormat.getTimeInstance().format(currentTime); 
			result.status = "It's " + time + ".";
			if (command != null)
				result.status = command+" was entered!";
			command = null;
			return result;
		} else {
			MenuDescriptor result = new MenuDescriptor();
			result.timeout = 10;
			result.menuHeader = "Choose one option:";
			result.addItem("Apples","APPLE");
			result.addItem("bananas", "BANANA");
			result.timeoutCommand = "TIMEOUT";
			command = null;
			return result;
		}*/
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
		command = code;
	}

	public void unserialize(ISerializer serializedData) {
		// TODO Auto-generated method stub
		
	}

	public void serialize(ISerializer serializedData) {
		// TODO Auto-generated method stub
	}

}
