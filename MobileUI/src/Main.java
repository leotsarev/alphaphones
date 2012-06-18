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
import javax.microedition.lcdui.Item;
import javax.microedition.lcdui.ItemStateListener;
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
			System.out.println(sleepDescriptor.status);
			mainScreen.append(sleepDescriptor.status);
			display.setCurrent(mainScreen);			
		} else {
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
		timer.schedule(timerTask, descriptor.timeout*1000);
	}
	
	public Main() {
		
		display = Display.getDisplay(this);
		timer = new Timer();

		im = new InteractionModelCheckDecorator(new SampleIM());
		im.reset();
		
		//descriptor = new InteractionModel.SleepDescriptor("initial");
		//descriptor.timeout = 10;
	
		whatNext();
		
		processDescriptor();
		display.setCurrent(mainScreen);
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
	
	
}
