package phones;

import java.util.Hashtable;

import phones.InteractionModel.Descriptor;

public interface IProcess {

	public abstract Descriptor handle();

	public abstract String getName();

	public abstract Hashtable cloneData();

}