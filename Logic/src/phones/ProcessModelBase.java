package phones;

import java.util.Date;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Stack;
import java.util.Vector;

public class ProcessModelBase extends InteractionModel{
	
	private Hashtable usedCodes;
	protected StatusContainer status = new StatusContainer();
	private Vector commandWordDefs = new Vector();
	private ProcessScheduler scheduler = new ProcessScheduler();
	
	private abstract class CommandWordDefBase
	{
		public abstract boolean isValidWord(String word);
		public abstract boolean isValidPrefix(String word);
		public abstract Process get(String word);
		public boolean isOneTimeCode(String code) {
			return false;
		}
	}
	
	private class FixedCommandWord extends CommandWordDefBase
	{
		private String fixedWord, processName;
		private Hashtable processData;
		
		public FixedCommandWord(String fixedWord, String processName, Hashtable processData)
		{
			this.fixedWord = fixedWord;
			this.processName = processName;
			this.processData = processData;
		}
		
		public Process get(String word) {
			if (isValidWord(word))
			{
				Process process = createProcessByName(processName);
				process.ProcessData = (Hashtable) processData.clone();
				return process;
			}
			return null;
		}

		public boolean isValidWord(String word) {
			return word.equals(fixedWord);
		}

		public boolean isValidPrefix(String word) {
			return fixedWord.startsWith(word);
		}
	}
	
	public static abstract class Process
	{
		public Process (ProcessModelBase model)
		{
			this.ProcessData = new Hashtable();
			this.model = model;
		}
		public abstract Descriptor handle();
		private Hashtable ProcessData;
		public abstract String getName();
		protected ProcessModelBase model;
		
		protected String getStringArg(String key)
		{
			return (String) ProcessData.get(key);
		}
		
		protected void setStringArg(String key, String value)
		{
			ProcessData.put(key, value);
		}
		
		protected void rescheduleAgain()
		{
			model.schedule(this, 1);
		}
		
		public final void serializeData(ISerializer ser)
		{
			for (int i = 0; i < ProcessData.size(); i ++)
			{
				ser.writeDict(ProcessData);
			}
		}
		
		public final void unserializeData (ISerializer ser)
		{
			ProcessData = ser.readDict();
		}
		protected int getIntArg(String key) {
			return Integer.parseInt(getStringArg(key));
		}
		public void setIntArg(String key, int coord) {
			setStringArg(key, Integer.toString(coord));
		}
		protected void scheduleNow(ProcessModelBase.Process process) {
			model.scheduleNow(process);
		}
		public Descriptor createMessage(String message) {
			SleepDescriptor result = new SleepDescriptor();
			result.status = message;
			result.timeout = 0;
			return result;
		}
		protected void addStatusMessage(String statusTag, String message) {
			model.status.addMessage(statusTag, message);
			
		}
		protected void scheduleAfterMins(Process process, int mins) {
			scheduleAfterSecs(process, mins * 60);
		}
		protected void scheduleAfterSecs(Process process, int secs) {
			model.schedule(process, secs);
			
		}
		protected void removeStatusMessage(String tag) {
			model.status.removeMessage(tag);
		}
	}
	
	protected static class StatusContainer
	{
		private Hashtable table = new Hashtable();
		
		public void addMessage(String statusTag, String message) {
			table.put(statusTag, message);
		}
		

		protected void removeMessage(String tag) {
			table.remove(tag);
		}
		
		private String get() {
			StringBuffer buffer = new StringBuffer();
			Object[] statusValues = table.values().toArray();
			for (int i=0; i < statusValues.length; i++)
			{
				buffer.append((String)statusValues[i]);
				buffer.append('\n');
			}
			return buffer.toString();
		}


		public void serialize(ISerializer ser) {
			ser.writeDict(table);			
		}


		public void unserialize(ISerializer ser) {
			table = ser.readDict();
		}
	}
	
	private class ProcessScheduler
	{
// TODO (keep efficiently events planned after long pauses)
		private LinkedList list = new LinkedList();
		public Process pop() {
			Stack currentStack = getCurrentStack();
			if (currentStack.empty())
			{
				return null;
			}
			return (Process) currentStack.pop();
		}

		private Stack getCurrentStack() {
			ensureOffsetPresent(0);
			Utils.assert_(list.size() > 0);
			Stack currentStack = (Stack) list.getFirst();
			
			return currentStack;
		}

		public void advance() {
			Utils.assert_(canAdvance(), "Cannot advance until all process has handled.");
			list.removeFirst();
		}

		private boolean canAdvance() {
			Stack currentStack = getCurrentStack();
			return currentStack.isEmpty();
		}

		public void schedule(Process process, int offset) {
			ensureOffsetPresent(offset);
			Stack targetStack = (Stack) list.get(offset);
			targetStack.push(process);
		}

		private void ensureOffsetPresent(int offset) {
			ensureSize(offset + 1);
		}

		private void ensureSize(int targetSize) {
			int offsetToAdd = targetSize - list.size();
			for (int i =0; i<offsetToAdd; i++)
			{
				list.addLast(new Stack());
			}
		}

		public void serialize(ISerializer ser) {
			ser.writeInt(list.size());
			for (int i = 0; i <list.size(); i++)
			{
				writeStack(ser, (Stack)list.get(i));
			}
		}

		private void writeStack(ISerializer ser, Stack stack) {
			ser.writeInt(stack.size());
			for (int i = stack.size() - 1; i >= 0; i--)
			{
				Process item = (Process) stack.get(i);
				ser.writeString(item.getName());
				item.serializeData(ser);
			}
		}

		public void unserialize(ISerializer ser) {
			int size = ser.readInt();
			ensureSize(size);
			for (int i = 0; i <list.size(); i++)
			{
				readStack(ser, (Stack)list.get(i));
			}
		}

		private void readStack(ISerializer ser, Stack stack) {
			int size = ser.readInt();
			for (int i =0; i< size; i++)
			{
				String name = ser.readString();
				Process process = createProcessByName(name);
				process.unserializeData(ser);
				stack.push(process);
			}
		}

		public int getPossibleSleep() {
			// TODO Auto-generated method stub
			return 1;
		}
		
	}
	
	protected void bindFixedCommandWord(String commandWord, Process process)
	{
		commandWordDefs.add(new FixedCommandWord(commandWord, process.getName(), process.ProcessData));

	}
	
	protected void scheduleNow(Process process)
	{
		Utils.assert_(process.getName() != null);
		schedule(process, 0);
	}
	
	protected void schedule(Process process, int offset)
	{
		scheduler.schedule(process, offset);
	}
	
	protected void reset() {
		scheduler = new ProcessScheduler();
		commandWordDefs = new Vector();
		usedCodes = new Hashtable();
		status = new StatusContainer();
	}
	
	private int currentSec;
	
	public Descriptor whatNext(int secsFromWorldStart, Date currentTime) {
		while (currentSec < secsFromWorldStart && scheduler.canAdvance())
		{
			currentSec++;
			scheduler.advance();
		}
		Descriptor result = null;
		while (result == null)
		{
			Process process = scheduler.pop();
			if (process == null)
			{
				result = new SleepDescriptor(status.get());
				result.timeout = scheduler.getPossibleSleep();
				return result;
			}
			result = process.handle();
		}
		return result;
	}



	public void assertCommandWord(String code) {
		Process process = getProcessForCode(code);
		if (process != null)
		{
			scheduleNow(process);
		}
	}

	private Process getProcessForCode(String code) {
		for (int i = 0; i < commandWordDefs.size(); i++)
		{
			CommandWordDefBase def = (CommandWordDefBase) commandWordDefs.get(i);
			if (def.isValidWord(code))
			{
				if (def.isOneTimeCode(code))
				{
					usedCodes.put(code, "");
				}
				return def.get(code);
			}
		}
		Utils.assert_(false, "Не удалось привязать код: " + code);
		return null;
	}
	
	public ProcessModelBase()
	{
		reset();
	}
	
	public int checkCommandWord(String commandWord) {
		if (usedCodes.containsKey(commandWord))
		{
			return CODE_USED;
		}
		int status = super.checkCommandWord(commandWord);
		Utils.assert_(commandWordDefs.size() > 0);
		for (int i = 0; i < commandWordDefs.size(); i++)
		{
			CommandWordDefBase def = (CommandWordDefBase) commandWordDefs.get(i);
			if (def.isValidWord(commandWord))
			{
				return CODE_VALID;
			}
			if (def.isValidPrefix(commandWord))
			{
				status = CODE_PREFIX;
			}
		}
		return status;
	}

	public void unserialize(ISerializer ser) {
		reset();
		scheduler.unserialize(ser);
		usedCodes = ser.readDict();
		status.unserialize(ser);
	}

	public void serialize(ISerializer ser) {
		scheduler.serialize(ser);
		ser.writeDict(usedCodes);
		status.serialize(ser);
	}
	
	public Process createProcessByName(String name)
	{
		return null;
	}
}
