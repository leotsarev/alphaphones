package alpha.disease;

import phones.ProcessModelBase;

public class OkrDisease extends GeneDiseaseBase {

	public OkrDisease(ProcessModelBase model) {
		super(model);
		setChemObj(getAlphaModel().Chemistry.getGene("s"));
	}
	
	protected String getStageMessage() {
		switch (getStage()) {
		case 1:
			return "Так, у меня, кажется, какая-то грязь на одежде! Надо ее счистить. Кто знает, как местные микробы могут внедриться в мое тело!";
		case 2:
			return "Мне необходимо осмотреть свои руки, одежду и подошвы на наличие местной флоры или фауны. Любую грязь надо тщательно счистить.";
		case 3:
			return "Плохо может получиться, если какой-нибудь клещ прыгнет на меня с дерева или травы. ";
		case 4:
			return "Думаю, безопасной может считаться только тщательно вытоптанная дорога на расстоянии от водоемов - там могут быть змеи или паразиты.";
		case HEALING_STAGE:
			return "Уф, как очнулся ото сна! И что за бред я себе навоображал? Надо больше отдыхать.";
		case HEALED_STAGE:
			return "";
		}
		return null;
	}

	protected void updateStatusForStage() {
		switch (getStage()) {
		case 1:
			addStatusMessage("Периодически отряхиваюсь");
		case 2:
			addStatusMessage("Периодически отряхиваюсь");
		case 3:
			addStatusMessage("Избегаю контакта с растениями");
		case 4:
			addStatusMessage("Хожу только безопасными путями. Отряхиваюсь");
		}
	}


	public String getName() {
		return "OkrDisease";
	}

}
