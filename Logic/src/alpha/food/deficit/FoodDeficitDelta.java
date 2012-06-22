package alpha.food.deficit;

import alpha.AlphaIM;
import phones.ProcessModelBase;
import phones.Utils;

public class FoodDeficitDelta extends alpha.food.FoodDeficitBase {

	private static final String LIGHT = "light";

	public FoodDeficitDelta(ProcessModelBase model) {
		super(model);
	}

	protected final String getStageMessage() {
		switch (getStage()) {
		case 1:
			return "Что-то мир вокруг яркий прямо до боли!";
		case 2:
			return "Проклятье! Когда я посмотрел{/а} на свет, запястье пронзила мгновенная боль! На несколько секунд кисть неестественно вывернулась.";
		case 3:
			return "Новый приступ боли, уже в колене, чуть не упал! Боль утихает, но не до конца.";
		case 4:
			return "Жизненно необходимо ПРЯМО СЕЙЧАС забраться в защищенное от света место!";
		case HEALING_STAGE:
			return "Так, я уже могу думать о солнечном свете без приступов паники. И эти внезапные боли прошли. Похоже, все будет хорошо";
		case HEALED_STAGE:
			return "Наконец-то я чувствую себе бодрым и здоровым!";
		}
		Utils.assert_(false);
		return null;
	}

	protected void updateStatusForStage() {
		switch (getStage()) {
		case 1:
			addStatusMessage(LIGHT, "Яркий свет раздражает.");
			break;
		case 2:
			addStatusMessage(LIGHT, "Щурюсь на ярком свету");
			break;
		case 3:
			getAlphaModel().Pain.add(getName(), AlphaIM.LOCATION_KNEE, AlphaIM.PAIN_POWER_NORMAL);
			addStatusMessage(LIGHT, "Свет вызывает панику");
			break;
		case 4:
			addStatusMessage(LIGHT, "На свету падаю в обморок и прихожу в себя только в темноте");
		case HEALING_STAGE:
			addStatusMessage(LIGHT, "Опасаюсь хорошо освещенных мест");
		default:
			break;
		}
	}

	public void cleanupStatus() {
		getAlphaModel().Pain.remove(getName(), AlphaIM.LOCATION_KNEE);
		removeStatusMessage(LIGHT);
	}

	public String getName() {
		return "FoodDeficitDelta";
	}
}
