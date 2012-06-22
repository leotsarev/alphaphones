package alpha.wounds;

import phones.ProcessModelBase;

public class ArmWound extends WoundBase {

	public ArmWound(ProcessModelBase model, int location) {
		super(model, location);
	}

	public String getName() {
		return "ArmWound";
	}
	
	protected void updateStatusForStage() {
		addDefaultPain();
		switch (getStage()) {
		case 3:
			addStatusMessage("Голова кружится");
		case 4:
			addStatusMessage("Перед глазами все плывет");
		}
	}

	protected String getStageMessage() {
		switch (getStage()) {
		case 1:
			return "А, сука, как же болит рука! Сейчас сдохну! ";
		case 2:
			return "Не так сильно болит, как мне казалось. К доктору можно не ходить";
		case 3:
			return "Рука все-таки разболелась. Ей почти невозможно пользоваться. И голова кружится.";
		case 4:
			return "Голова кружится чудовищно. Ничего не соображаю.";
		}
		return null;
	}

}
