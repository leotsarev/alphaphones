package alpha.chem;

import phones.ISerializer;

public interface IPersistentChemObject extends IChemObject{
	public void serialize (ISerializer ser);
	public void unserialize (ISerializer ser);
	public void masterToggle();
	public void setPresent();
	public void setNotPresent();
	public void setHalfPresent();
}
