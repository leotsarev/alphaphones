import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.AlertType;
import javax.microedition.lcdui.Choice;
import javax.microedition.lcdui.ChoiceGroup;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.CustomItem;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Item;
import javax.microedition.lcdui.ItemStateListener;
import javax.microedition.lcdui.StringItem;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;
import javax.microedition.rms.RecordEnumeration;
import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreException;

import alpha.AlphaIM;

import phones.InteractionModel;
import phones.InteractionModel.Descriptor;
import phones.InteractionModelCheckDecorator;
import phones.StringSerializer;
import phones.Utils;
import phones.InteractionModel.MenuDescriptor;
import phones.InteractionModel.SleepDescriptor;
import phones.Sample.SampleIM;


public class Main extends MIDlet implements ItemStateListener, CommandListener {

	private Display display;
	private Form mainScreen;
	ChoiceGroup choiceGroup;
	Command ok;
	
	InteractionModel im;
	Descriptor descriptor;
	MenuDescriptor menuDescriptor;
	SleepDescriptor sleepDescriptor;
	
	Date prevTime;
	
	String prevStatus = null;
	String code;
	
	Timer timer;
	TimerTask timerTask = null;
	
	
	synchronized public void itemStateChanged(Item item) {
		boolean[] flags = new boolean[choiceGroup.size()];
		choiceGroup.getSelectedFlags(flags);
		
		int index = -1;
		for (int i = 0; i < flags.length; i++)
			if (flags[i]) {
				index = i;
				break;
			}
		
		if (index != -1) {
			for (int i = 0; i < flags.length; i++)
				flags[i] = false;
			choiceGroup.setSelectedFlags(flags);

			String cmd = menuDescriptor.getCommand(index);
			im.assertCommandWord(cmd);
			
			whatNext();
			processDescriptor();
		}
	}
	
	synchronized public void commandAction(Command command, Displayable displayable) {
		if (command == ok) {
			String cmd = menuDescriptor.getCommand(0);
			im.assertCommandWord(cmd);
			
			whatNext();
			processDescriptor();
		}
	}
	
	
	synchronized void whatNext() {
		StringSerializer ser = new StringSerializer();
		im.serialize(ser);
		saveRecord(ser.getBytes());
		
		Date time = new Date();
		long dt = (time.getTime()-prevTime.getTime()+500)/1000;
		prevTime = time;
		descriptor = im.whatNext((int)dt, time);
	}
	
	void createMenu() {
		choiceGroup = new ChoiceGroup(menuDescriptor.menuHeader, Choice.MULTIPLE);
		String[] names = menuDescriptor.getNames();
		for (int i = 0; i < names.length; i++) {
			choiceGroup.append(names[i], null);
		}

		mainScreen = new Form(" ");
		mainScreen.append(choiceGroup);

		mainScreen.setItemStateListener(this);
		display.setCurrent(mainScreen);

		if (menuDescriptor.alarm != InteractionModel.ALARM_SILENT)
			AlertType.ALARM.playSound(display);
	}
	
	void createMessageBox() {
		Alert a = new Alert(" ", menuDescriptor.menuHeader, null, AlertType.ALARM);
		a.setTimeout(Alert.FOREVER);
		a.setCommandListener(this);
		a.addCommand(ok);
		
		display.setCurrent(a);
	}
	
 	synchronized void processDescriptor() {
		if (descriptor instanceof SleepDescriptor) {
			menuDescriptor = null;
			sleepDescriptor = (SleepDescriptor)descriptor;
			
			if (!sleepDescriptor.status.equals(prevStatus)) {
				code = "";
				prevStatus = sleepDescriptor.status;
				
				mainScreen = new Form(" ");
				mainScreen.append(new KeyCatcher());
				mainScreen.append(sleepDescriptor.status);
				mainScreen.append(new KeyCatcher());
				
				display.setCurrent(mainScreen);
			}
			
			// this check is for unlikely situation when code reported as valid
			// prefix at the previous tick was now given different status
			if (im.checkCommandWord(code) != InteractionModel.CODE_PREFIX) {
				code = "";
				setMainText(sleepDescriptor.status);
			}
		} else {
			sleepDescriptor = null;
			menuDescriptor = (MenuDescriptor)descriptor;
			
			prevStatus = null;
			code = "";
		
			if (menuDescriptor.getCount() == 1 && 
				menuDescriptor.getNames()[0].equals("Ok") &&false)
				createMessageBox();
			else
				createMenu();
		
			// TODO: recurring alarms
		}

		if (descriptor.timeout == -1)
			descriptor.timeout = 500000; // should be enough for anyone

		Utils.assert_(descriptor.timeout >= 0);
		
		if (timerTask != null)
			timerTask.cancel();
		timerTask = new TestTimerTask();
		timer.schedule(timerTask, descriptor.timeout*1000);
		
	}
 	
	void setMainText(String s) {
		if (mainScreen.size() != 3) {
			System.out.println("Can't set main text because!!!");
			return;
		}
		StringItem textItem = (StringItem) mainScreen.get(1);
		if (!textItem.getText().equals(s)) // to avoid autorewind
			textItem.setText(s); 
	}
	
	public Main() {
		
		display = Display.getDisplay(this);
		ok = new Command("Ok", Command.OK, 1);
		
		timer = new Timer();
		
		code = "";

		im = new InteractionModelCheckDecorator(new AlphaIM());

		try {
			StringSerializer ser = new StringSerializer();
			byte[] data = loadRecord();
			if (data == null)
				System.out.println("record not found");
			System.out.println("loaded "+data.length+" bytes");
			ser.setBytes(data);
			im.unserialize(ser);
			System.out.println("deserialization ok");
			
		} catch (Exception e) {
			System.out.println("Loading or deserialization error: "+e);
			im.reset();
		}

		prevTime = new Date();
		whatNext();
	
		processDescriptor();
	}
	
	synchronized void keyPressed(int keyCode) {
		if (sleepDescriptor == null)
			return;
		if (keyCode >= '0' && keyCode <= '9') {
			code += (char)keyCode;
			
			int status = im.checkCommandWord(code);
			if (status == InteractionModel.CODE_UNKNOWN || 
				status == InteractionModel.CODE_USED) {
				Alert a = new Alert(" ", 
						status == InteractionModel.CODE_UNKNOWN ? 
							"Code is too long" : 
							"Code was already used", 
						null, AlertType.ERROR);
				a.setTimeout(1000);
				display.setCurrent(a, mainScreen);
				code = "";
			}
			if (status == InteractionModel.CODE_VALID) {
				System.out.println("Valid code "+code);
				im.assertCommandWord(code);
				whatNext();
				processDescriptor();
				code = "";
			}
		}
		if (keyCode == (int)'#' || keyCode == (int)'*') {
			code = "";
		}
		if (keyCode < 0) {
			if (code.length() > 0)
				code = code.substring(0, code.length()-1);
		}
		
		// this check is repeated because there 
		// would be a new descriptor if code was valid
		if (sleepDescriptor == null) 
			return;
		if (code.equals(""))
			setMainText(sleepDescriptor.status);
		else
			setMainText("Code: "+code+"...");
	}

	protected void startApp() throws MIDletStateChangeException {
		display.setCurrent(mainScreen);
	}
	
	protected void destroyApp(boolean arg0) throws MIDletStateChangeException {
	}

	protected void pauseApp() {
	}

	private class TestTimerTask extends TimerTask {
		public final void run() {
			synchronized (Main.this) {
				
				// this check is for unlikely event that task was cancelled right
				// after run method started executing but before it entered
				// critical section
				if (timerTask == null)
					return;
				
				if (sleepDescriptor != null) {
					// send timeout?
					whatNext();
					processDescriptor();
				}
				else {
					im.assertCommandWord(menuDescriptor.timeoutCommand);
					whatNext();
					processDescriptor();
				}
			}
		}
	}
	
	class KeyCatcher extends CustomItem {
		protected void keyPressed(int keyCode) {
			Main.this.keyPressed(keyCode);
		}
		public KeyCatcher() {
			super("");
		}
		public int getMinContentWidth() {
			return 1;
		}
		public int getMinContentHeight() {
			return 1;
		}
		public int getPrefContentWidth(int width) {
			return getMinContentWidth();
		}
		public int getPrefContentHeight(int height) {
			return getMinContentHeight();
		}
		protected void paint(Graphics g, int w, int h) {
		}

	}

	
	byte[] loadRecord() {
		byte[] result = null;
		try {
			RecordStore rs = RecordStore.openRecordStore("state", true);
			
			RecordEnumeration re = rs.enumerateRecords(null, null, false);
			while (re.hasNextElement()) {
				System.out.println("reading record");
				result = re.nextRecord();
				break;
			}
			rs.closeRecordStore();
		} catch (RecordStoreException e) {
			System.out.println("RS read problem "+e);
		}
		return result;
	}
	
	void saveRecord(byte[] data) {
		try {
			RecordStore rs = RecordStore.openRecordStore("state", true);
			
			RecordEnumeration re = rs.enumerateRecords(null, null, false);
			int cnt = 0;
			while (re.hasNextElement()) {
				int id = re.nextRecordId();
				System.out.println("writing record");
				rs.setRecord(id, data, 0, data.length);
				cnt++;
			}
			if (cnt == 0) {
				System.out.println("creating record");
				rs.addRecord(data, 0, data.length);
			}
			rs.closeRecordStore();
		} catch (RecordStoreException e) {
			System.out.println("RS write problem "+e);
		}
	}
	
}
