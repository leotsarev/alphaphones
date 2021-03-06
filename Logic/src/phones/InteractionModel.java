package phones;

import java.util.Date;
import java.util.Vector;

public abstract class InteractionModel {
	public static final int ALARM_RECURRENT = 100;
    public static final int ALARM_SINGLE = 50;
    public static final int ALARM_SILENT = 1;
    
	public abstract void reset();

	public static abstract class Descriptor {
		public int timeout; // in seconds; -1 for infinity, 0 for now
		public int alarm = ALARM_SILENT;
	}
	
	

	public static class SleepDescriptor extends Descriptor {
		public SleepDescriptor (String status)
		{
			this.status = status;
		}
		public SleepDescriptor()
		{
			
		}
		public String status;
		
		public String toString()
		{
			return "SleepDescriptor: " + timeout + " sec"; 
		}
	}
	
	public static class MenuDescriptor extends Descriptor {
		
		private static final int DEFAULT_MENU_TIMEOUT = 30;

		public MenuDescriptor()
		{
			timeout = DEFAULT_MENU_TIMEOUT;
		}
		
		static class MenuItem
	    {
			public MenuItem(String name, String command)
			{
				ItemName = name;
				ItemCommand = command;
			}
	        public String ItemName;
	        public String ItemCommand;
	    }
		
		public String menuHeader;
        Vector options = new Vector();
        public String timeoutCommand = "";
        
		public void addItem(String name, String command) {
			options.addElement(new MenuItem(name, command));
		}
		
		public String getCommand(int index) {
			return (String) ((MenuItem) options.elementAt(index)).ItemCommand;
		}
		
		public String[] getNames()
		{
			String[] result = new String[options.size()];
			for (int i = 0; i < result.length; i++)
			{
				result[i] = ((MenuItem) options.elementAt(i)).ItemName;
			}
			return result;
		}
		
		public int getCount()
		{
			return options.size();
		}
	}
	
	
	public abstract Descriptor whatNext(int passedSecs, Date currentTime);

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
