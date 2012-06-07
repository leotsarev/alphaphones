package phones;

import java.util.Date;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Stack;

public class ProcessModelBase extends InteractionModel{
	
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
		private ProcessModelBase model;
		
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
				Utils.assert_(false);
				ser.writeDict(ProcessData);
			}
		}
		
		public final void unserializeData (ISerializer ser)
		{
			ProcessData = ser.readDict();
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
	
	public void scheduleNow(Process process)
	{
		scheduler.scheduleNow(process);
	}
	
	public void schedule(Process process, int offset)
	{
		scheduler.schedule(process, offset);
	}
	
	private ProcessScheduler scheduler = new ProcessScheduler();
	
	public void reset() {
		scheduler = new ProcessScheduler();
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

	protected Process getProcessForCode(String code) {
		return null;
	}

	public void unserialize(ISerializer ser) {
		scheduler.unserialize(ser);
	}

	public void serialize(ISerializer ser) {
		scheduler.serialize(ser);
	}
	
	public Process createProcessByName(String name)
	{
		return null;
	}

}
