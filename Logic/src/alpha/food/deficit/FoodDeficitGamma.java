package alpha.food.deficit;

import phones.ProcessModelBase;
import phones.Utils;

public class FoodDeficitGamma extends alpha.food.FoodDeficitBase {

	public FoodDeficitGamma(ProcessModelBase model) {
		super(model);
	}


	protected String getHealMessage() {
		switch (getStage()) {
		case 1:
			return "Все еще чешется...";
		case 2:
			return "А вот и чесотка, я скучал без нее... И как же бесят эти рожи! Зато ноги больше не беспокоят...";
		case 3:
			return "Кажется, я могу встать на ноги. Но лучше не буду...";
		}
		Utils.assert_(false);
		return null;
	}

	protected String getProgressMessage() {
		switch (getStage()) {
		case 1:
			return "Отчаянно зудит кожа на запястьях, да и много где. Почешусь-ка от души под лопатками!";
		case 2:
			return "Эта чесотка сведет меня с ума! И они постоянно хотят от меня всякой чуши. Наору на одного, может отстанут...";
		case 3:
			return "Я очень слаб, и ноги меня плохо слушаются. Хорошо бы полежать минутку-другую...";
		case 4:
			return "Ноги совсем перестали двигаться! Кто-то должен мне помочь, пока не поздно. Заставлю их, если потребуется!!";
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
			addStatusMessage(getName(), "Ноги не слушаются, не могу стоять. Срочно нужна помощь");
		default:
			break;
		}
	}

	protected void cleanupStatus() {
		removeStatusMessage(getName());
	}

	public String getName() {
		// TODO Auto-generated method stub
		return "FoodDeficitGamma";
	}

}
