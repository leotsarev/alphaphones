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


public class Main extends MIDlet implements ItemStateListener {

	private Display display;
	private Form mainScreen;
	ChoiceGroup choiceGroup;

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
	
	
	public Main() {
		display = Display.getDisplay(this);
		mainScreen = new Form(" ");
		//mainScreen.append("Select option:");
		
		choiceGroup = new ChoiceGroup("Question:", Choice.MULTIPLE);
		for (int i = 0; i < 20; i++)
			choiceGroup.append("zzz whatever "+i, null);
		mainScreen.append(choiceGroup);
		
		mainScreen.setItemStateListener(this);
	}

	protected void startApp() throws MIDletStateChangeException {
		display.setCurrent(mainScreen);
	}
	
	protected void destroyApp(boolean arg0) throws MIDletStateChangeException {
	}

	protected void pauseApp() {
	}

}
