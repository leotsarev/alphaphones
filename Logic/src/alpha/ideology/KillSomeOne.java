package alpha.ideology;

import phones.InteractionModel.Descriptor;
import phones.ProcessModelBase;
import alpha.AlphaProcess;

public class KillSomeOne extends AlphaProcess {

	public KillSomeOne(ProcessModelBase model) {
		super(model);
	}

	public Descriptor handle() {
		return createMessage("Я готов совершить убийство во имя фракции «" + getAlphaModel().currentFaction.getName() + "», если потребуется! (Если решишься, подойди к мастерам за чипом)");
	}

	public String getName() {
		return "KillSomeOne";
	}

}
