package alpha.ideology;

import phones.ISerializer;
import phones.ProcessModelBase;
import phones.Utils;

public class Faction {
	private String name;
	private int[] coords;
	private int track = 0;
	
	private Faction(ProcessModelBase model, String name, int coord0, int coord1, int coord2, int coord3)
	{
		Utils.assert_(isValidCoord(coord0));
		Utils.assert_(isValidCoord(coord1));
		Utils.assert_(isValidCoord(coord2));
		Utils.assert_(isValidCoord(coord3));
		this.name = name; 
		int[] c = {coord0, coord1, coord2, coord3};
		this.coords = c;
	}

	protected boolean isValidCoord(int coord) {
		return coord == 1 || coord == -1;
	}
	
	public boolean apply (int coord, int sign)
	{
		boolean correct = coords[coord] == sign;
		if (correct)
		{
			track++;
		}
		return correct;
	}
	
	public static Faction[] createFactions(ProcessModelBase model)
	{
		Faction[] r = { 
				new Faction(model, "Флора", 
					-1,	-1, -1, +1),
				new Faction(model, "Дети Господа", 
					+1,	-1, +1, -1),
				new Faction(model, "Полдень", 
					+1,	+1, -1, +1),
				new Faction(model, "Воля Человека", 
					-1,	+1, +1, -1),
		};
		return r;
	}
	
	public void serialize(ISerializer ser)
	{
		ser.writeInt(track);
	}
	
	public void unserialize(ISerializer ser)
	{
		track = ser.readInt();
	}

	public String getName() {
		return name;
	}

	public boolean isFanatic() {
		return track >= 5;
	}

	public int getTrack() {
		return track;
	}
	
	public String toString()
	{
		return getName() + " " + getTrack();
	}
}
