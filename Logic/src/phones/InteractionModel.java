package phones;

import java.util.Date;

public abstract class InteractionModel {
	public static final int PRIORITY_FUCKING_ANNOYING = 100;
    public static final int PRIORITY_WHATEVER = 50;
    public static final int PRIORITY_SILENT = 1;
    
	protected abstract void reset();

	public static abstract class Descriptor {
		public int timeout; // in seconds; -1 for infinity, 0 for now
		public boolean saveRequired = true;
		public int priority = PRIORITY_WHATEVER;
	}
	
	public static class MenuItem
    {
		public MenuItem(String name, String command)
		{
			ItemName = name;
			ItemCommand = command;
		}
        public String ItemName;
        public String ItemCommand;
    }

	public static class SleepDescriptor extends Descriptor {
		public String status;
	}
	
	public static class MenuDescriptor extends Descriptor {
		public String menuHeader;
        public MenuItem[] options; 
        public String timeoutCommand;
	}
	
	
	public abstract Descriptor whatNext(int minsFromWorldStart, Date currentTime);

	public static final int CODE_UNKNOWN = 0;
	public static final int CODE_VALID = 1;
	public static final int CODE_USED = 2;
	public static final int CODE_PREFIX = 3;
	public int checkCommandWord(String commandWord)
	{
		return CODE_UNKNOWN;
	}
	
	
	public abstract void assertCommandWord(String code);
    
	public abstract void unserialize(ISerializer ser);
	public abstract void serialize(ISerializer ser);
}
