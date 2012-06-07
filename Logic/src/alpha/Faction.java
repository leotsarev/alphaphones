package alpha;

import phones.ISerializer;

public class Faction {
	private String name;
	private int[] coords;
	private int track = 0;
	
	private Faction(AlphaIM model, String name, int coord0, int coord1, int coord2, int coord3)
	{
		this.name = name; 
		int[] c = {coord0, coord1, coord2, coord3};
		this.coords = c;
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
	
	public static Faction[] createFactions(AlphaIM model)
	{
		Faction[] r = { 
				new Faction(model, "Флора", -1, 
						-1, -1, 1),
				new Faction(model, "Дети Господа", +1, 
						-1, 1, -11),
				new Faction(model, "Полдень", +1, 
						1, -1, 1),
				new Faction(model, "Воля Человека", -1, 
						1, 1, -11),
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
}
