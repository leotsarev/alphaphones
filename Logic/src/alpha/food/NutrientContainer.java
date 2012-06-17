package alpha.food;

import phones.ISerializer;

public class NutrientContainer {
	private Nutrient[] content = Nutrient.createAll();

	public Nutrient get(String geneName) {
		for (int i = 0; i <content.length; i++)
		{
			if (content[i].getName().equals(geneName))
			{
				return content[i];
			}
		}
		return null;
	}

	public void serialize (ISerializer ser)
	{
		for (int i = 0; i<content.length;i++)
		{
			content[i].serialize(ser);
		}
	}
	
	public void unserialize (ISerializer ser)
	{
		for (int i = 0; i<content.length;i++)
		{
			content[i].unserialize(ser);
		}
	}

	public Nutrient[] asArray() {
		return content;
	}

	public Nutrient get(int nutrientNum) {
		return content[nutrientNum];
	}
}
