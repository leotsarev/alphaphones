package alpha.food.deficit;

import phones.ProcessModelBase;
import phones.Utils;
import alpha.food.FoodDeficitBase;

public class FoodDeficitPhi extends FoodDeficitBase {

	public FoodDeficitPhi(ProcessModelBase model) {
		super(model);
	}


	protected String getStageMessage() {
		switch (getStage()) {
		case 1:
			return "Внезапная слабость во всем теле. К горлу подкатывает комок. Что-то не то я съел{/а}!";
		case 2:
			return "Что-то мутит все сильнее. Разговаривать ни с кем не хочется, просто языком тяжело ворочать.";
		case 3:
			return "Оп! Перед глазами все меркнет, колени подкашиваются!.. Падаю...";
		case 4:
			return "Ааа, все опять поплыло, снова падаю!";
		case HEALING_STAGE:
			return "Ох, силы понемногу возвращаются. Я уже могу встать без чьей-либо помощи, хотя ходить еще трудно.";
		case HEALED_STAGE:
			return "Я полностью выздоровел{/а}!";
		}
		Utils.assert_(false);
		return null;
	}

	protected void updateStatusForStage() {
		switch (getStage()) {
		case 1:
			addStatusMessage(getName(), "Слабость, тошнота, головокружение");
		case 2:
			addStatusMessage(getName(), "Дышу часто и тяжело");
			break;
		case 3:
			addStatusMessage(getName(), "Перед глазами плывет. С трудом двигаюсь.");
			break;
		case 4:
			addStatusMessage(getName(), CAN_T_STAY);
		case HEALING_STAGE:
			addStatusMessage(getName(), "Слабость. Шатает при ходьбе");
		default:
			break;
		}
	}
	
	public String getName() {
		return "FoodDeficitPhi";
	}

}
