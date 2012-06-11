package phones;

import java.util.Date;

public class InteractionModelCheckDecorator extends InteractionModel {
	
	private InteractionModel innerModel;
	
	// TODO: check complete transition diagram compliance
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

	public int checkCommandWord(String commandWord)
	{
		// TODO: check that when number code is accepted all it's prefixes are
		// indeed categorized as valid prefixes
		return innerModel.checkCommandWord(commandWord);
	}
	
	public void assertCommandWord(String code) {
		Utils.assert_(innerModel.checkCommandWord(code) == CODE_VALID);
		Utils.assert_(!commandWordAlreadyAsserted, "received two command words in a row");
		innerModel.assertCommandWord(code);
		commandWordAlreadyAsserted = true;
	}

	public void unserialize(ISerializer ser) {
		innerModel.unserialize(ser);
		commandWordAlreadyAsserted = false;
	}

	public void serialize(ISerializer ser) {
		// TODO: try to deserialize shit immediately and check it's the same
		innerModel.serialize(ser);
	}

}
