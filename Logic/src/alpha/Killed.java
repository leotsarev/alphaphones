package alpha;

import phones.InteractionModel.Descriptor;
import phones.ProcessModelBase;

public class Killed extends AlphaProcess {

	public Killed(ProcessModelBase model) {
		super(model);
	}

	public Descriptor handle() {
		getAlphaModel().dead = true;
		return createMessage("Я умер. Окончательно и бесповоротно, похоже ничего поделать уже нельзя.");
	}

	public String getName() {
		return "Killed";
	}

}
