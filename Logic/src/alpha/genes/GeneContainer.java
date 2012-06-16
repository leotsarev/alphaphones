package alpha.genes;

import phones.ISerializer;
import phones.Utils;

public class GeneContainer
{
	private Gene[] genes = Gene.createAll();

	public Gene get(String geneName) {
		for (int i = 0; i <genes.length; i++)
		{
			if (genes[i].getName() == geneName)
			{
				return genes[i];
			}
		}
		Utils.assert_(false, "Can't find gen:" + geneName);
		return null;
	}

	public void serialize (ISerializer ser)
	{
		for (int i = 0; i<genes.length;i++)
		{
			genes[i].serialize(ser);
		}
	}
	
	public void unserialize (ISerializer ser)
	{
		for (int i = 0; i<genes.length;i++)
		{
			genes[i].unserialize(ser);
		}
	}

	public Gene[] asArray() {
		return genes;
	}

}