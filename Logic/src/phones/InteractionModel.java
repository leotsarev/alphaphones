package phones;

import java.util.Date;

public abstract class InteractionModel {

	static abstract class Descriptor {
		public int timeout; // in seconds
	}

	// TODO: pick a proper name instead of 'tick'
	static class TickDescriptor extends Descriptor {
		public String status;
	}
	
	static class MenuDescriptor extends Descriptor {
		public String question;
		public String[] options;
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
	public abstract void receiveMenuOption(String option);
	
	public abstract boolean needSave();
	
	// TODO: serialize&deserialize
}
