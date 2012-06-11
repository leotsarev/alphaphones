package alpha;

import phones.InteractionModel.Descriptor;
import phones.ProcessModelBase;

public class IdeologyChange extends ProcessModelBase.Process {

	public IdeologyChange(AlphaIM model) {
		super(model);
	}
	
	public IdeologyChange(AlphaIM model, int coord, int sign) {
		super(model);
		setIntArg("coord", coord);
		setIntArg("sign", sign);
	}

	public Descriptor handle() {
		int coord = getIntArg("coord");
		int sign = getIntArg("sign");
		
		Faction[] factions = getAlphaIM().factions;
		boolean currentFactionUpdated = false; 
		Faction currentFaction = getAlphaIM().getCurrentFaction();
		String message;
		
		for (int i =0; i<factions.length; i++)
		{
			 if (factions[i].apply(coord, sign) && factions[i] == currentFaction)
			 {
				 currentFactionUpdated = true;
			 }
		}
		
		Faction newFaction = getAlphaIM().getCurrentFaction();
		
		if (currentFaction == null)
		{
			message = "Похоже, построение нового общества сложное дело! Без моральных ориентиров тут не обойтись. Вот, например, эти, как их, "
		+ newFaction.getName()+ ", похоже разумные ребята. Надо бы присмотреться поближе к их идеологии";
		}
		else if (currentFaction != newFaction)
		{
			message = "Решение далось вам непросто. В фракции «" + currentFaction.getName() 
					+"» вас бы не одобрили, но вы-то знаете, что поступили как лучше.  Кстати, кто вас поймет, так это " + newFaction.getName();
		}
		else if (currentFactionUpdated)
		{
			if (currentFaction.isFanatic())
			{
				message = "Окончательное решение принято, хватит рассусоливать. Теперь вы навсегда связали свою судьбу с фракцией «" + currentFaction.getName();
			}
			else
			{
				message = "Ваша приверженность идеалам фракции «" + currentFaction.getName() + "» укрепилась. Только вы точно знаете, как построить идеальное общество";
			}
		}
		else
		{
			message = "Решение далось вам непросто. Надо обдумать на досуге, какие из этого возникают последствия";
		}
		
		return createMessage(message);
	}

	public AlphaIM getAlphaIM() {
		return (AlphaIM)model;
	}

	public String getName() {
		return "IdeologyChange";
	}
}
