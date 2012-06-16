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
		protected final IProcess processTemplate;
		
		protected CommandWordDefBase(IProcess processTemplate)
		{
			this.processTemplate = processTemplate;
		}
		
		public abstract boolean isValidWord(String word);
		public abstract boolean isValidPrefix(String word);
		public abstract IProcess get(String word);
		public boolean isOneTimeCode(String code) {
			return false;
		}

		public IProcess createProcess() {
			Process process = createProcessByName(processTemplate.getName());
			Utils.assert_(process != null, "Не удалось создать процесс по имени " + processTemplate.getName());
			process.ProcessData = processTemplate.cloneData();
			return process;
		}
	}
	
	private class MenuPrefixCommandWord extends CommandWordDefBase
	{

		private final String commandWordPrefix;

		public MenuPrefixCommandWord(String commandWordPrefix, IPrefixHandler processTemplate) {
			super(processTemplate);
			this.commandWordPrefix = commandWordPrefix;
		}

		public boolean isValidWord(String word) {
			if (!word.startsWith(commandWordPrefix))
			{
				return false;
			}
			return ((IPrefixHandler)processTemplate).isValidSuffix(getSuffix(word));
		}

		public String getSuffix(String word) {
			Utils.assert_(word.length() > commandWordPrefix.length(), "Invalid word " + word);
			return word.substring(commandWordPrefix.length());
		}

		public boolean isValidPrefix(String word) {
			if (commandWordPrefix.startsWith(word))
			{
				return true;
			}
			if (!word.startsWith(commandWordPrefix))
			{
				return false;
			}
			return ((IPrefixHandler)processTemplate).isStartOfSuffix(getSuffix(word));
		}

		public IProcess get(String word) {
			Utils.assert_(isValidWord(word));
			IPrefixHandler process = (IPrefixHandler) super.createProcess();
			process.setSuffixValue(getSuffix(word));
			return process;
		}
		
	}
	
	private class FixedCommandWord extends CommandWordDefBase
	{
		private String fixedWord;
		public FixedCommandWord(String fixedWord, IProcess processTemplate)
		{
			super(processTemplate);
			this.fixedWord = fixedWord;
		}
		
		public IProcess get(String word) {
			if (isValidWord(word))
			{
				IProcess process = createProcess();
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
	
	public static abstract class Process implements IProcess
	{
		public Process (ProcessModelBase model)
		{
			this.model = model;
		}
		public abstract Descriptor handle();
		private Hashtable ProcessData = new Hashtable();
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
		protected void scheduleNow(IProcess process) {
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
		protected void scheduleAfterMins(IProcess process, int mins) {
			scheduleAfterSecs(process, mins * 60);
		}
		protected void scheduleAfterSecs(IProcess process, int secs) {
			model.schedule(process, secs);
			
		}
		protected void removeStatusMessage(String tag) {
			model.status.removeMessage(tag);
		}
		
		public Hashtable cloneData()
		{
			return (Hashtable) ProcessData.clone();
		}
		protected void addMenuItemAndBind(MenuDescriptor menu, String itemName,
				IProcess process)
		{
					if (model.checkCommandWord(process.getName()) == InteractionModel.CODE_UNKNOWN)
					{
						model.bindFixedCommandWord(process);
					}
					menu.addItem(itemName, process.getName());
		}
		
		protected void unscheduleAll(IProcess process) {
			model.scheduler.unscheduleAll(process.getName());
			
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
		public IProcess pop() {
			Stack currentStack = getCurrentStack();
			if (currentStack.empty())
			{
				return null;
			}
			return (IProcess) currentStack.pop();
		}

		public void unscheduleAll(String name) {
			for (int i = 0; i <list.size(); i++)
			{
				unscheduleFromStack((Stack)list.get(i), name);
			}
		}

		private void unscheduleFromStack(Stack stack, String name) {
			for (int i = stack.size() - 1; i >= 0; i--)
			{
				Process item = (Process) stack.get(i);
				if (item.getName() == name)
				{
					stack.remove(i);
				}
			}
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

		public void schedule(IProcess process, int offset) {
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
			for (int i =0; i< list.size(); i++)
			{
				Stack stack = (Stack)list.get(i);
				if (!stack.empty())
				{
					return i;
				}
			}
			return 1;
		}
		
	}
	
	private void checkCreatePossible(IProcess process) {
		Process sampleProcess = createProcessByName(process.getName());
		Utils.assert_(sampleProcess != null, "Don't know how to create " + process.getName() + " bind failed.");
		Utils.assert_(sampleProcess.getName().equals(process.getName()), "Try create " + process.getName() + ", but got: " + sampleProcess.getName());
	}
	
	protected void bindFixedCommandWord(String commandWord, IProcess process)
	{
		checkCreatePossible(process);
		commandWordDefs.add(new FixedCommandWord(commandWord, process));
	}
	
	protected void bindFixedCommandWord(IProcess process) {
		bindFixedCommandWord(process.getName(), process);
	}

	protected void bindPrefixCommandWord(String commandWordPrefix, IPrefixHandler process) {
		checkCreatePossible(process);
		commandWordDefs.add(new MenuPrefixCommandWord(commandWordPrefix, process));
	}
	
	protected void scheduleNow(IProcess process)
	{
		Utils.assert_(process.getName() != null);
		schedule(process, 0);
	}
	
	protected void schedule(IProcess process, int offset)
	{
		Utils.assert_(process.getName() != null);
		scheduler.schedule(process, offset);
	}
	
	protected void reset() {
		scheduler = new ProcessScheduler();
		commandWordDefs = new Vector();
		usedCodes = new Hashtable();
		status = new StatusContainer();
	}
	
	private int currentSec;
	private int targetSec;
	
	public Descriptor whatNext(int passedSecs, Date currentTime) {
		targetSec += passedSecs;
			
		while (currentSec < targetSec && scheduler.canAdvance())
		{
			currentSec++;
			scheduler.advance();
		}
		Descriptor result = null;
		while (result == null)
		{
			IProcess process = scheduler.pop();
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
		IProcess process = getProcessForCode(code);
		if (process != null)
		{
			scheduleNow(process);
		}
	}

	private IProcess getProcessForCode(String code) {
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
		bindFixedCommandWord("", new DoNothingProcess(this));
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
		Process[] process = new Process[] {
				new DoNothingProcess(this)
		};
		for (int i = 0; i < process.length; i++)
		{
			if (name == process[i].getName())
			{
				return process[i];
			}
		}
		return null;
	}
	
}
