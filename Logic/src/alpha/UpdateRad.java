package alpha;

import phones.InteractionModel.Descriptor;
import phones.ProcessModelBase;
import phones.Utils;

public class UpdateRad extends AlphaProcess {

	public UpdateRad(ProcessModelBase model) {
		super(model);
	}

	public Descriptor handle() {
		scheduleAfterMins(this, 10);
		int oldLevel = getAlphaModel().radStage;
		updateRadValue();
		int level = getAlphaModel().radStage;
		String message = null;
		switch (level)
		{
			case 0:
				removeStatusMessage(getName());
				return null;
			case 1:
				addStatusMessage("Головокружение, слабость и тошнота");
				message = "Слабость во всем теле к горлу подкатывает комок, живот болит";
				break;
			case 2:
				addStatusMessage("Живот сводит судорогой. Все кости болят");
				message = "Живот скручивает. Кажется, меня сейчас вырвет! Надо побыть одн{ому/й}.";
				break;
			case 3:
				addStatusMessage("От резких движений падаю в обморок");
				message = "Шок. При каждом движении в глазах вспыхивают звезды. Невозможно терпеть эту боль!";
				break;
			default:
				addStatusMessage("Барахтаюсь в черной пелене");
				message = "Падаю... их всех поглотила черная пелена. Такое уже было, это не больно, но очень страшно. Я точно это помню";
				break;
					
		}
		
		return (oldLevel < level) ? createMessage(message) : null; 
	}

	private void updateRadValue() {
		if (getAlphaModel().inHouse)
		{
			getAlphaModel().radDamage -= 1;
			if (getAlphaModel().radDamage < 0)
			{
				getAlphaModel().radDamage = 0;
			}
		}
		else
		{
			getAlphaModel().radDamage += getAlphaModel().wearingMask ? 1 : 6;
		}
		getAlphaModel().radStage = getAlphaModel().radDamage / getAlphaModel().Chemistry.getRadImmune();
	}

	public String getName() {
		return "UpdateRad";
	}

}
