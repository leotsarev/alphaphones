package alpha.food.deficit;

import phones.ProcessModelBase;
import phones.Utils;
import alpha.AlphaIM;
import alpha.food.FoodDeficitBase;

public class FoodDeficitBeta extends FoodDeficitBase {

	public FoodDeficitBeta(ProcessModelBase model) {
		super(model);
	}

	protected String getStageMessage() {
		switch (getStage()) {
		case 1:
			return "Руку на несколько секунд охватывает жгучая боль! Боль быстро прошла.";
		case 2:
			return "Опять болевой приступ! Все тело сводит судорогой, но затем боль уходит.";
		case 3:
			return "Паралич распространяется на всю половину тела. Нахлынуло смутное чувство тревоги. Похоже, меня отравили!";
		case 4:
			return "Я почти ничего не помню. Как зовут этих людей? Что было вчера? Я долж{ен/на} понять, кто меня отравил, пока еще не слишком поздно.";
		case HEALING_STAGE:
			return "Как пелена спала. Я снова все помню, да и рукой могу немного двигать. Похоже, я не на шутку запаниковал{/а}!";
		case HEALED_STAGE:
			return "Ну все, я здоров{/а}.";
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
		case HEALING_STAGE:
			addStatusMessage(getName()  + "phys", "Левая рука плохо слушается");
		default:
			
			break;
		}
	}

	public void cleanupStatus() {
		removeStatusMessage(getName() + "psi");
		removeStatusMessage(getName() + "phys");
		removeStatusMessage(getName() + "body");
		getAlphaModel().Pain.remove(getName(), AlphaIM.LOCATION_WHOLE_BODY);
	}

	public String getName() {
		return "FoodDeficitBeta";
	}
}
