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
import phones.InteractionModelCheckDecorator;
import phones.Utils;
import phones.InteractionModel.MenuDescriptor;
import phones.InteractionModel.SleepDescriptor;
import phones.Sample.SampleIM;


public class Main extends MIDlet implements ItemStateListener {

	private Display display;
	private Form mainScreen;
	private Form menuScreen;
	ChoiceGroup choiceGroup;
	
	InteractionModel im;
	InteractionModel.Descriptor descriptor;
	
	public void itemStateChanged(Item item) {
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

			Alert a = new Alert(" ", "Item "+index+" was chosen!", null, AlertType.ALARM);
			a.setTimeout(1000);
			
			display.setCurrent(a, mainScreen);
		}
	}
	
	void processDescriptor() {
		if (descriptor instanceof SleepDescriptor) {
			SleepDescriptor sleep = (SleepDescriptor)descriptor;
			Utils.assert_(false, "not implemented yet");
		} else {
			MenuDescriptor menu = (MenuDescriptor)descriptor;
			choiceGroup = new ChoiceGroup(menu.menuHeader, Choice.MULTIPLE);
			choiceGroup.setLabel(menu.menuHeader);
			choiceGroup.deleteAll();
			String[] names = menu.getNames();
			for (int i = 0; i < names.length; i++) {
				choiceGroup.append(names[i], null);
			}

			menuScreen = new Form(" ");
			menuScreen.append(choiceGroup);

			menuScreen.setItemStateListener(this);
		}
	}
	
	public Main() {
		
		display = Display.getDisplay(this);
		//menuScreen = new Form(" ");
		//mainScreen.append("Select option:");
		
		

		im = new InteractionModelCheckDecorator(new SampleIM());
		descriptor = im.whatNext(0, null);
		//descriptor = new InteractionModel.SleepDescriptor("initial");
		//descriptor.timeout = 10;
	
		processDescriptor();
	}

	protected void startApp() throws MIDletStateChangeException {
		display.setCurrent(menuScreen);
	}
	
	protected void destroyApp(boolean arg0) throws MIDletStateChangeException {
	}

	protected void pauseApp() {
	}

}
