package phones.Sample;

//import java.text.DateFormat;
import java.util.Date;

import phones.ISerializer;
import phones.InteractionModel;
import phones.Utils;

public class SampleIM extends InteractionModel {

	String command;
	
	public void reset() {
		command = null;
	}

	public Descriptor whatNext(int timePassed, Date currentTime) {
		if ("123".equals(command)) {
			command = null;
			MenuDescriptor result = new MenuDescriptor();
			result.timeout = 20;
			result.menuHeader = "Choose one option:";
			result.addItem("Apples","APPLE");
			result.addItem("bananas", "BANANA");
			result.timeoutCommand = "TIMEOUT";
			result.alarm = ALARM_RECURRENT;
			return result;
		}
		
		SleepDescriptor result = new SleepDescriptor();
		result.timeout = 10;
		result.status = "Code 123 for menu.\nIt's "+timePassed+"s since last whatNext.";
		if (command != null) {
			result.status += "\n"+command+" was entered!";
			command = null;
		}

		return result;
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

	public void unserialize(ISerializer ser) {
		command = ser.readString();
		if (command.equals("null"))
			command = null;
		String end = ser.readString();
		Utils.assert_(end.equals("end"));
	}

	public void serialize(ISerializer ser) {
		ser.writeString(command == null ? "null" : command);
		ser.writeString("end");
	}

}
