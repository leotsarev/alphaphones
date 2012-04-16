package phones;

import java.util.Date;

public abstract class InteractionModel {
	
	public abstract void reset();

	public static abstract class Descriptor {
		public int timeout; // in seconds; -1 for infinity
	}

	// TODO: pick a proper name instead of 'tick'
	public static class TickDescriptor extends Descriptor {
		public String status;
	}
	
	public static class MenuDescriptor extends Descriptor {
		public String question;
		public String[] options;
		public boolean beepAtStart;
		public int reminder; // 15 means that it will beep every 15 seconds
							 // 0 means it's disabled
	}
	
	
	public abstract Descriptor whatNext(int timePassed, Date currentTime);

	
	public static final int VALID_CODE = 1;
	public static final int USED_CODE = 2;
	public static final int TOO_LONG_CODE = 3;
	public static final int POTENTIAL_PREFIX = 4;
	public abstract int codeStatus(String code);
	
	
	public abstract void receiveTickTimeout();
	public abstract void receiveCode(String code);
	
	public abstract void receiveMenuTimeout();
	public abstract void receiveMenuOption(int option);
	
	public abstract boolean needSave();
	
	// TODO: serialize&deserialize
}
