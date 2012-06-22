package alpha.wounds;

import alpha.AlphaIM;
import phones.ProcessModelBase;

public class TorsoWound extends WoundBase {

	public TorsoWound(ProcessModelBase model) {
		super(model, AlphaIM.LOCATION_WHOLE_BODY);
	}
	
	protected int getQuantLenInMins() {
		return 10;
	}

	protected String getStageMessage() {
		switch (getStage()) {
		case 1:
			return "Какая боль! Я отключаюсь...";
		case 2:
			return "Ничего не соображаю. Кажется, я все-таки жив{/а}...";
		case 3:
			return "Ого! Ничем хорошим это не хочется. Такой боли я никогда не испытывал{/а}";
		case 4:
			return "Мне кажется, я на грани смерти...";
		}
		return null;
	}

	protected void updateStatusForStage() {
		switch (getStage()) {
		case 1:
			addStatusMessage("БЕЗ СОЗНАНИЯ");
			break;
		case 2:
			addStatusMessage("Голова кружится");
			addPain(AlphaIM.PAIN_POWER_NORMAL);
			break;
		case 3:
			addStatusMessage("Голова кружится");
			addPain(AlphaIM.PAIN_POWER_STRONG);
			break;
		case 4:
			addStatusMessage("Все плывет, не могу стоять");
			addPain(AlphaIM.PAIN_POWER_STRONG);
			break;
		}
	}

	public String getName() {
		return "TorsoWound";
	}

}
