package alpha.disease;

import phones.ProcessModelBase;

public class Vaskulit extends EarthDiseaseBase {

	public Vaskulit(ProcessModelBase model) {
		super(model);
	}

	protected String getStageMessage() {
		switch (getStage()) {
		case 1:
			return "В голове вертится какая-то мысль, и не просто мысль, а жизненно важная. Никак не могу вспомнить!";
		case 2:
			return "Что это! Кто-то окрикнул меня по имени и сказал: \"Обернись\". Кто это был?";
		case 3:
			return "Начинаю понимать... Это один из тех, с кем я общался совсем недавно. Он украл у меня мысль и теперь я не могу ее вспомнить!";
		case 4:
			return "Опять оно! Этот же голос с теми же интонациями отчетливо выговаривает мое имя. Проклятье, что ему нужно?!";
		case HEALING_STAGE:
			return "Так, спокойно. Мне просто не нужно обращать внимание на людей. Только полностью игнорируя их слова и действия я смогу защитить свои мысли";
		case HEALED_STAGE:
			return "Уф, как очнулся ото сна! Хорошо, что все закончилось. И что за бред я себе навоображал? Надо больше отдыхать.";
		}
		return null;
	}

	protected void updateStatusForStage() {
		switch (getStage()) {
		case 1:
			addStatusMessage("Сосредоточен. Ушел в себя");
		case 2:
			addStatusMessage("Найду, кто меня позвал!");
		case 3:
			addStatusMessage("Голова кружится. Нужно понять, кто!");
		case 4:
			addStatusMessage("Паника. Нужно попробовать скрыться");
		case HEALING_STAGE:
			addStatusMessage("Ушел в себя. Избегаю разговоров");
		}
	}


	public String getName() {
		return "Vaskulit";
	}

}
