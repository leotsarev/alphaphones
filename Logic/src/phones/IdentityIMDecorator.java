package phones;

import java.util.Date;

// convenient base class for decorators
public class IdentityIMDecorator extends InteractionModel {
	
	protected InteractionModel innerModel;
	
	public IdentityIMDecorator(InteractionModel model)
	{
		innerModel = model;
	}
	
	public void reset() {
		innerModel.reset();
	}

	public Descriptor whatNext(int passedSecs, Date currentTime) {
		return innerModel.whatNext(passedSecs, currentTime);
	}
	
	public int checkCommandWord(String commandWord) {
		return innerModel.checkCommandWord(commandWord);
	}

	public void assertCommandWord(String code) {
		innerModel.assertCommandWord(code);
	}

	public void unserialize(ISerializer ser) {
		innerModel.unserialize(ser);
	}

	public void serialize(ISerializer ser) {
		innerModel.serialize(ser);
	}
}
