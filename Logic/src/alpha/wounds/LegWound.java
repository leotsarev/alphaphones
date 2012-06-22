package alpha.wounds;

import phones.ProcessModelBase;

public class LegWound extends WoundBase {

	public LegWound(ProcessModelBase model, int location) {
		super(model, location);
	}

	protected String getStageMessage() {
		switch (getStage()) {
		case 1:
			return "А, сука, как же болит нога! Сейчас сдохну! ";
		case 2:
			return "Не так сильно болит, как мне казалось. К доктору можно не ходить";
		case 3:
			return "Нога все-таки разболелась. Невозможно наступить";
		case 4:
			return "Ноги не держат...";
		}
		return null;
	}

	public String getName() {
		return "LegWound";
	}

	protected void updateStatusForStage() {
		addDefaultPain();
		switch (getStage()) {
		case 3:
			addStatusMessage("Трудно ходить");
		case 4:
			addStatusMessage("Не могу ходить без помощи");
		}
		
	}

}
