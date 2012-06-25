package phones;

import java.util.Hashtable;

import phones.InteractionModel.Descriptor;

public interface IProcess {

	public abstract Descriptor handle();

	public abstract String getName();

	public abstract Hashtable cloneData();

	public abstract void unserializeData(ISerializer ser);

	public abstract void serializeData(ISerializer ser);
	
	public abstract void cleanup();

}