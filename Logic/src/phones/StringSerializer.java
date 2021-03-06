package phones;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;


public class StringSerializer implements ISerializer {

	Vector strings = new Vector();

	/*
	 * (non-Javadoc)
	 * 
	 * @see phones.ISerializer#getBytes()
	 */
	public byte[] getBytes() {
		String result = "";
		for (int i = 0; i < strings.size(); i++) {
			String s = (String) strings.elementAt(i);
			if (s.indexOf('\n') != -1)
				throw new RuntimeException("newlines in strings");
			result += s + "\n";
		}
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(baos);
		try {
			dos.writeUTF(result);
		} catch (IOException e) {
			System.out.println("Serialization error: " + e);
		}

		return baos.toByteArray();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see phones.ISerializer#setBytes(byte[])
	 */
	public void setBytes(byte[] data) {
		ByteArrayInputStream bais = new ByteArrayInputStream(data);
		DataInputStream dis = new DataInputStream(bais);
		String s;
		try {
			s = dis.readUTF();
		} catch (IOException e) {
			System.out.println("Deserialization error: " + e);
			s = "\n";
		}
		int i = 0;
		while (i < s.length()) {
			int j = s.indexOf("\n", i);
			if (j == -1)
				throw new RuntimeException("Deserialization: newline not found");
			strings.addElement(s.substring(i, j));
			i = j + 1;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see phones.ISerializer#print()
	 */
	public void print() {
		for (int i = 0; i < strings.size(); i++) {
			System.out.println(strings.elementAt(i));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see phones.ISerializer#writeString(java.lang.String)
	 */
	public void writeString(String s) {
		strings.addElement(s);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see phones.ISerializer#writeInt(int)
	 */
	public void writeInt(int i) {
		writeString("" + i);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see phones.ISerializer#writeInt(java.lang.String, int)
	 */
	public void writeInt(String name, int i) {
		writeString(name + " = " + i);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see phones.ISerializer#writeBool(boolean, java.lang.String,
	 * java.lang.String)
	 */
	public void writeBool(boolean b, String t, String f) {
		if (b)
			writeString(t);
		else
			writeString(f);
	}

	// keys and values have to be strings
	/*
	 * (non-Javadoc)
	 * 
	 * @see phones.ISerializer#writeDict(java.util.Hashtable)
	 */
	public void writeDict(Hashtable dict) {
		writeInt("dict size", dict.size());
		String[] keys = SerializeUtils.getSortedDictKeys(dict);
		for (int j = 0; j < dict.size(); j++) {
			writeString(keys[j]);
			writeString((String) dict.get(keys[j]));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see phones.ISerializer#readString()
	 */
	public String readString() {
		// potentially quadratic, but who cares?
		String result = (String) strings.elementAt(0);
		strings.removeElementAt(0);
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see phones.ISerializer#readInt()
	 */
	public int readInt() {
		return Integer.parseInt(readString());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see phones.ISerializer#readInt(java.lang.String)
	 */
	public int readInt(String name) {
		String prefix = name + " = ";
		String s = readString();
		if (s.startsWith(prefix))
			return Integer.parseInt(s.substring(prefix.length()));
		else
			throw new RuntimeException("expected named value " + name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see phones.ISerializer#readBool(java.lang.String, java.lang.String)
	 */
	public boolean readBool(String t, String f) {
		String s = readString();
		if (s.equals(t))
			return true;
		else if (s.equals(f))
			return false;
		else
			throw new RuntimeException("Can't read bool " + t + "/" + f + ", got : " + s);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see phones.ISerializer#readDict()
	 */
	public Hashtable readDict() {
		int size = readInt("dict size");
		Hashtable result = new Hashtable();
		for (int i = 0; i < size; i++) {
			String key = readString();
			String value = readString();
			result.put(key, value);
		}
		return result;
	}

	public boolean readBool() {
		return readBool("true", "false");
	}

	public void writeBool(boolean bool) {
		writeBool(bool, "true", "false");
	}

	public String toString()
	{
		Enumeration en = strings.elements();
		StringBuffer sb = new StringBuffer();
		while (en.hasMoreElements())
		{
			sb.append(en.nextElement());
			sb.append('\n');
		}
		return sb.toString();
	}

}
