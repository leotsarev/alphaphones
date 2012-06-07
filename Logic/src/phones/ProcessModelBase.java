package phones;

import java.util.Date;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Stack;
import java.util.Vector;

public class ProcessModelBase extends InteractionModel{
	
	private Hashtable usedCodes;
	private Vector commandWordDefs = new Vector();
	
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
	}
	
	private class ProcessScheduler
	{

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

		public void scheduleNow(Process process) {
			Stack currentStack = getCurrentStack();
			currentStack.push(process);
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
		scheduler.scheduleNow(process);
	}
	
	protected void schedule(Process process, int offset)
	{
		scheduler.schedule(process, offset);
	}
	
	private ProcessScheduler scheduler = new ProcessScheduler();
	
	protected void reset() {
		scheduler = new ProcessScheduler();
		commandWordDefs = new Vector();
		usedCodes = new Hashtable();
	}
	
	private int currentMin;
	
	public Descriptor whatNext(int minsFromWorldStart, Date currentTime) {
		while (currentMin < minsFromWorldStart && scheduler.canAdvance())
		{
			currentMin++;
			scheduler.advance();
		}
		Process process = scheduler.pop();
		if (process == null)
		{
			SleepDescriptor result = new SleepDescriptor();
			result.saveRequired = false;
			result.status = "";
			result.timeout = scheduler.getPossibleSleep();
			return result;
		}
		return process.handle();
	}

	public void assertCommandWord(String code) {
		Process process = getProcessForCode(code);
		if (process != null)
		{
			scheduler.scheduleNow(process);
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
	}

	public void serialize(ISerializer ser) {
		scheduler.serialize(ser);
		ser.writeDict(usedCodes);
	}
	
	public Process createProcessByName(String name)
	{
		return null;
	}

}
