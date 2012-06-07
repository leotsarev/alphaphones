package phones;

import java.util.Hashtable;

public interface ISerializer {

	public abstract byte[] getBytes();

	public abstract void setBytes(byte[] data);

	public abstract void print();

	public abstract void writeString(String s);

	public abstract void writeInt(int i);

	public abstract void writeInt(String name, int i);

	public abstract void writeBool(boolean b, String t, String f);

	// keys and values have to be strings
	public abstract void writeDict(Hashtable dict);

	public abstract String readString();

	public abstract int readInt();

	public abstract int readInt(String name);

	public abstract boolean readBool(String t, String f);

	public abstract Hashtable readDict();

}