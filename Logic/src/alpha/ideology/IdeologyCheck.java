package alpha.ideology;

import alpha.AlphaProcess;
import phones.InteractionModel.Descriptor;
import phones.ProcessModelBase;

public class IdeologyCheck extends AlphaProcess {

	public IdeologyCheck(ProcessModelBase model) {
		super(model);
	}

	public Descriptor handle() {
		if (getAlphaModel().isFanatic())
		{
			return createMessage("Не к чему вести учет поступкам — я никогда уже не сойду с избранного пути! Отмечать поступки больше не надо");
		}
		scheduleNow(new IdeologyMenu(model));
		return null;
	}

	public String getName() {
		return "IdeologyCheck";
	}

}
