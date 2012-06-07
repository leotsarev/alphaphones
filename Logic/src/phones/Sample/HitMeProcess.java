package phones.Sample;

import phones.OneTimeMessageBase;
import phones.ProcessModelBase;

public class HitMeProcess extends OneTimeMessageBase {

	public HitMeProcess(ProcessModelBase model) {
		super(model, "Ты ударил себя. Это больно");
	}

	public String getName() {
		return "HitMe";
	}

}
