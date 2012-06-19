import java.util.Timer;
import java.util.TimerTask;

import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.AlertType;
import javax.microedition.lcdui.Choice;
import javax.microedition.lcdui.ChoiceGroup;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CustomItem;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Item;
import javax.microedition.lcdui.ItemStateListener;
import javax.microedition.lcdui.StringItem;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

import alpha.AlphaIM;

import phones.InteractionModel;
import phones.InteractionModel.Descriptor;
import phones.InteractionModelCheckDecorator;
import phones.Utils;
import phones.InteractionModel.MenuDescriptor;
import phones.InteractionModel.SleepDescriptor;
import phones.Sample.SampleIM;


public class Main extends MIDlet implements ItemStateListener {

	private Display display;
	private Form mainScreen;
	ChoiceGroup choiceGroup;
	
	InteractionModel im;
	Descriptor descriptor;
	MenuDescriptor menuDescriptor;
	SleepDescriptor sleepDescriptor;
	
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

			String command = menuDescriptor.getCommand(index);
			im.assertCommandWord(command);
			
			Alert a = new Alert(" ", "Item "+command+" was chosen!", null, AlertType.ALARM);
			a.setTimeout(100);
			
			display.setCurrent(a, mainScreen);
			
			whatNext();
			processDescriptor();
		}
	}
	
	synchronized void whatNext() {
		descriptor = im.whatNext(0, null);
	}
	
 	synchronized void processDescriptor() {
		if (descriptor instanceof SleepDescriptor) {
			sleepDescriptor = (SleepDescriptor)descriptor;
			
			mainScreen = new Form(" ");
			mainScreen.append(new KeyCatcher());
			mainScreen.append(sleepDescriptor.status);
			mainScreen.append(new KeyCatcher());
			
			display.setCurrent(mainScreen);			
		} else {
			code = "";
			
			menuDescriptor = (MenuDescriptor)descriptor;
			
			choiceGroup = new ChoiceGroup(menuDescriptor.menuHeader, Choice.MULTIPLE);
			String[] names = menuDescriptor.getNames();
			for (int i = 0; i < names.length; i++) {
				choiceGroup.append(names[i], null);
			}

			mainScreen = new Form(" ");
			mainScreen.append(choiceGroup);

			mainScreen.setItemStateListener(this);
			display.setCurrent(mainScreen);
		}
		
		if (timerTask != null)
			timerTask.cancel();
		timerTask = new TestTimerTask();
		// TODO: -1
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
		timer = new Timer();
		
		code = "";

		im = new InteractionModelCheckDecorator(new SampleIM());
		im.reset();
		
		//descriptor = new InteractionModel.SleepDescriptor("initial");
		//descriptor.timeout = 10;
	
		whatNext();
		
		processDescriptor();
		display.setCurrent(mainScreen);
	}
	
	synchronized void keyPressed(int keyCode) {
		if (sleepDescriptor == null)
			return;
		if (keyCode >= '0' && keyCode <= '9') {
			code += (char)keyCode;
			
			int status = im.checkCommandWord(code);
			if (status == im.CODE_UNKNOWN || status == im.CODE_USED) {
				Alert a = new Alert(" ", 
						status == im.CODE_UNKNOWN ? "Code is too long" : "Code was already used", 
						null, AlertType.ERROR);
				a.setTimeout(1000);
				display.setCurrent(a, mainScreen);
				code = "";
			}
			if (status == im.CODE_VALID) {
				System.out.println("Valid code "+code);
				// TODO:
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
		if (sleepDescriptor == null) // this check is repeated because there would be a new descriptor if code was valid
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
	
}
