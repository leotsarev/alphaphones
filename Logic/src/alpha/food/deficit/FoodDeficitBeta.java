package alpha.food.deficit;

import phones.ProcessModelBase;
import phones.Utils;
import alpha.AlphaIM;
import alpha.chem.Chemistry;
import alpha.food.FoodDeficitBase;

public class FoodDeficitBeta extends FoodDeficitBase {

	public FoodDeficitBeta(ProcessModelBase model) {
		super(model);
		setChem(Chemistry.NUTRIEN + "Beta");
	}

	protected String getHealMessage() {
		switch (getStage()) {
		case 1:
			return "Интересно, рука так и останется парализованной навсегда?";
		case 2:
			return "К счастью, боль ушла, но подвижность руки не вернулась.";
		case 3:
			return "Ага, в голове проясняется. Я хоть вспомнил как кого зовут... Но боль вернулась! ";
		}
		Utils.assert_(false);
		return null;
	}

	protected String getProgressMessage() {
		switch (getStage()) {
		case 1:
			return "Руку на несколько секунд охватывает жгучая боль! Боль быстро прошла.";
		case 2:
			return "Опять болевой приступ! Все тело сводит судорогой, но затем боль уходит.";
		case 3:
			return "Все тело болит, левая рука по-прежнему не двигается. Нахлынуло смутное чувство тревоги. Похоже, меня отравили!";
		case 4:
			return "Я почти ничего не помню. Как зовут этих людей? Что было вчера? Я должен понять, кто меня отравил, пока еще не слишком поздно.";
		}
		Utils.assert_(false);
		return null;
	}

	protected void updateStatusForStage() {
		switch (getStage()) {
		case 1:
			addStatusMessage(getName() + "phys", "Левая рука не двигается");
		case 2:
			addStatusMessage(getName() + "phys", "Левая рука не двигается");
			addStatusMessage(getName() + "body", "Все тело ноет");
			break;
		case 3:
			getAlphaModel().Pain.add(getName(), AlphaIM.LOCATION_WHOLE_BODY, AlphaIM.PAIN_POWER_NORMAL);
			addStatusMessage(getName() + "phys", "Левая рука не двигается");
			break;
		case 4:
			addStatusMessage(getName() + "psi", "Надо найти отравителя, пока я не забыл все!!");
			addStatusMessage(getName() + "phys", "Левая рука не двигается");
			break;
		default:
			
			break;
		}
	}

	protected void cleanupStatus() {
		removeStatusMessage(getName() + "phys");
		removeStatusMessage(getName() + "body");
		getAlphaModel().Pain.remove(getName(), AlphaIM.LOCATION_WHOLE_BODY);
	}

	protected String getStageMessage() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getName() {
		return "FoodDeficitBeta";
	}
}
