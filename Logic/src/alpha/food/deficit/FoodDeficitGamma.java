package alpha.food.deficit;

import phones.ProcessModelBase;
import phones.Utils;

public class FoodDeficitGamma extends alpha.food.FoodDeficitBase {

	public FoodDeficitGamma(ProcessModelBase model) {
		super(model);
	}

	protected String getStageMessage() {
		switch (getStage()) {
		case 1:
			return "Отчаянно зудит кожа на запястьях, да и много где. Почешусь-ка от души под лопатками!";
		case 2:
			return "Эта чесотка сведет меня с ума! И они постоянно хотят от меня всякой чуши. Наору на одного, может отстанут...";
		case 3:
			return "Я очень слаб{/а}, и ноги меня плохо слушаются. Хорошо бы полежать минутку-другую...";
		case 4:
			return "Ноги совсем перестали двигаться! Кто-то должен мне помочь, пока не поздно. Заставлю их, если потребуется!!";
		case HEALING_STAGE:
			return "Уф, наконец-то. Я смог пошевелить ступней. Еще раз, и другой тоже! Как же славно, попробую встать.";
		case HEALED_STAGE:
			return "Я полностью выздоровел{/а}!";
		}
		Utils.assert_(false);
		return null;
	}

	protected void updateStatusForStage() {
		switch (getStage()) {
		case 1:
			addStatusMessage(getName(), "Зуд на запястьях");
		case 2:
			addStatusMessage(getName(), "Страшная чесотка. Окружающие раздражают");
			break;
		case 3:
			addStatusMessage(getName(), "Ноги не слушаются. Хочу лежать");
			break;
		case 4:
			addStatusMessage(getName(), "Ноги не слушаются, не могу стоять. Заставлю их помочь мне!");
		case HEALING_STAGE:
			addStatusMessage(getName(), CAN_T_STAY);
		default:
			break;
		}
	}
	
	public String getName() {
		return "FoodDeficitGamma";
	}

}
