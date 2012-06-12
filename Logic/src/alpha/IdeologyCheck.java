package alpha;

import phones.InteractionModel.Descriptor;
import phones.ProcessModelBase;
import phones.ProcessModelBase.Process;

public class IdeologyCheck extends AlphaProcess {

	public IdeologyCheck(ProcessModelBase model) {
		super(model);
	}

	public Descriptor handle() {
		if (getAlphaModel().isFanatic())
		{
			return createMessage("Вы уже тверды в своем выборе и никогда не сойдете с избранного пути! Отмечать поступки больше не надо");
		}
		scheduleNow(new IdeologyMenu(model));
		return null;
	}

	public String getName() {
		return "IdeologyCheck";
	}

}
