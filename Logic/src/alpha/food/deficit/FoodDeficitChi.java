package alpha.food.deficit;

import phones.ProcessModelBase;
import phones.Utils;
import alpha.food.FoodDeficitBase;

public class FoodDeficitChi extends FoodDeficitBase {

	private static final String EYES = "eyes";

	public FoodDeficitChi(ProcessModelBase model) {
		super(model);
	}

	protected String getStageMessage() {
		switch (getStage()) {
		case 1:
			return "По-моему, мне нездоровится - глаза, кажется, воспалились и свет причиняет им боль.";
		case 2:
			return "Глаза начали сильно чесаться. А держать их на свету открытыми - все болезненнее.";
		case 3:
			return "Все в ярких пятнах! Я долж{ен/на} побыстрее укрыться от света.";
		case 4:
			return "Нет, только не это! Я больше ничего не вижу! Перед глазами сплошная пелена...";
		case HEALING_STAGE:
			return "О, какое счастье! Я снова могу что-то видеть - по ходу, не дальше своей руки... ";
		case HEALED_STAGE:
			return "Ну все, я больше не бол{ен/ьна}. Отлично вижу!";
		}
		Utils.assert_(false);
		return null;
	}

	protected void updateStatusForStage() {
		switch (getStage()) {
		case 1:
			addStatusMessage(EYES, "Яркий свет раздражает.");
		case 2:
			addStatusMessage(EYES, "Глаза чешутся.");
			break;
		case 3:
			addStatusMessage(EYES, "На свету не могу открыть глаза.");
			break;
		case 4:
			addStatusMessage(EYES, "Ничего не вижу.");
			break;
		case HEALING_STAGE:
			addStatusMessage(EYES, "Близорукость.");
		default:
			
			break;
		}
	}

	protected void cleanupStatus() {
		removeStatusMessage(EYES);
	}

	public String getName() {
		return "FoodDeficitChi";
	}
}
