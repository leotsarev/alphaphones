package alpha.ideology;

import alpha.AlphaProcess;
import phones.InteractionModel.Descriptor;
import phones.ProcessModelBase;

public class IdeologyChange extends AlphaProcess {

	public IdeologyChange(ProcessModelBase model) {
		super(model);
	}
	
	public IdeologyChange(ProcessModelBase model, int coord, int sign) {
		super(model);
		setIntArg("coord", coord);
		setIntArg("sign", sign);
	}

	public Descriptor handle() {
		int coord = getIntArg("coord");
		int sign = getIntArg("sign");
		
		Faction[] factions = getAlphaModel().factions;
		 
		Faction currentFaction = getAlphaModel().currentFaction;
		int currentTrack = (currentFaction != null) ? currentFaction.getTrack() : 0;
		String message;
		
		for (int i =0; i<factions.length; i++)
		{
			factions[i].apply(coord, sign);
		}
		
		boolean currentFactionUpdated = (currentFaction != null) && currentFaction.getTrack() > currentTrack;
		
		getAlphaModel().updateCurrentFaction();
		
		Faction newFaction = getAlphaModel().currentFaction;
		
		if (currentFaction == null)
		{
			message = "Похоже, построение нового общества сложное дело! Без моральных ориентиров тут не обойтись. Вот, например, эти, как их, "
		+ newFaction.getName()+ ", похоже разумные ребята. Надо бы присмотреться поближе к их идеологии";
		}
		else if (currentFaction != newFaction)
		{
			message = "Пусть в фракции «" + currentFaction.getName() 
					+"» меня бы не одобрили, но я-то знаю, что поступил как лучше.  Кстати, «" + newFaction.getName() + "» поняли бы меня...";
		}
		else if (currentFactionUpdated)
		{
			if (currentFaction.isFanatic())
			{
				message = "Окончательное решение принято, хватит рассусоливать. Я навсегда буду верен фракции «" + currentFaction.getName();
			}
			else
			{
				message = "Моя приверженность идеалам фракции «" + currentFaction.getName() + "» укрепилась. Только мы точно знаем, как построить идеальное общество.";
			}
		}
		else
		{
			message = "Решение далось непросто. Надо обдумать на досуге, какие из этого возникают последствия";
		}
		
		return createMessage(message);
	}

	public String getName() {
		return "IdeologyChange";
	}
}
