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

	public void reset() {
		innerModel.reset();
		commandWordAlreadyAsserted = false;
	}

	public Descriptor whatNext(int passedSecs, Date currentTime) {
		commandWordAlreadyAsserted = false;
		Utils.assert_(passedSecs >= 0, "Time MUSTN'T go backwards");
		return innerModel.whatNext(passedSecs, currentTime);
	}

	public int checkCommandWord(String commandWord)
	{
		// TODO: check that when number code is accepted all it's prefixes are
		// indeed categorized as valid prefixes
		return innerModel.checkCommandWord(commandWord);
	}
	
	public void assertCommandWord(String code) {
		Utils.assert_(innerModel.checkCommandWord(code) == CODE_VALID, code);
		Utils.assert_(!commandWordAlreadyAsserted, 
				"received two command words in a row");
		innerModel.assertCommandWord(code);
		commandWordAlreadyAsserted = true;
	}

	public void unserialize(ISerializer ser) {
		innerModel.unserialize(ser);
		commandWordAlreadyAsserted = false;
	}

	public void serialize(ISerializer ser) {
		// try to deserialize shit immediately and check it's the same
		StringSerializer tmp = new StringSerializer();
		innerModel.serialize(tmp);
		byte[] bytes = tmp.getBytes();
		String firstTry = tmp.toString();
		
		tmp = new StringSerializer();
		tmp.setBytes(bytes);
		innerModel.unserialize(tmp);
		tmp = new StringSerializer();
		innerModel.serialize(tmp);
		
		String secondTry = tmp.toString();
		byte[] bytes2 = tmp.getBytes();
		
		Utils.assert_(bytes.length == bytes2.length && firstTry.length() == secondTry.length(), 
				"serialization/deserialization inconsistency (first " +bytes.length + " but second is " + bytes2.length+ " in bytes)");
		
		Utils.assert_(firstTry.length() == secondTry.length(), 
				"serialization/deserialization inconsistency (first " +firstTry.length() + " but second is " + secondTry.length() + " in chars)");
		
		for (int i =0; i<firstTry.length(); i++)
		{
			Utils.assert_(firstTry.charAt(i) == secondTry.charAt(i), "serialization/deserialization inconsistency \n" + firstTry.substring(i) + "\n:::\n" + secondTry.substring(i));
		}
		for (int i = 0; i < bytes.length; i++)
			Utils.assert_(bytes[i] == bytes2[i], 
					"serialization/deserialization inconsistency ");
		
		
		innerModel.serialize(ser);
	}

}
