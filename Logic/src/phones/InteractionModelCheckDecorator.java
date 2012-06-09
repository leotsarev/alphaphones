package phones;

import java.util.Date;

public class InteractionModelCheckDecorator extends InteractionModel {
	
	private InteractionModel innerModel;
	private boolean commandWordAlreadyAsserted = false;
	
	public InteractionModelCheckDecorator(InteractionModel model)
	{
		innerModel = model;
	}

	protected void reset() {
		innerModel.reset();
		commandWordAlreadyAsserted = false;
	}

	public Descriptor whatNext(int minsFromWorldStart, Date currentTime) {
		commandWordAlreadyAsserted = false;
		return innerModel.whatNext(minsFromWorldStart, currentTime);
	}

	public void assertCommandWord(String code) {
		Utils.assert_(!commandWordAlreadyAsserted, "Дважды получили командное слово");
		innerModel.assertCommandWord(code);
		commandWordAlreadyAsserted = true;
	}

	public void unserialize(ISerializer ser) {
		innerModel.unserialize(ser);
		commandWordAlreadyAsserted = false;
	}

	public void serialize(ISerializer ser) {
		innerModel.serialize(ser);
	}

}
