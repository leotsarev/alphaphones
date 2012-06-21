package alpha.disease;

import phones.ProcessModelBase;

public class EmpathicDisease extends VirusDiseaseBase {

	public EmpathicDisease(ProcessModelBase model) {
		super(model);
		setChemObj(getAlphaModel().Chemistry.getGene("q"));
	}
	
	protected String getStageMessage() {
		switch (getStage()) {
		case 1:
			return "Насколько же все-таки чудесна жизнь! Внезапная мысль, но это правда, ведь можно убить кого-нибудь и сделать счастливым";
		case 2:
			return "...или можно никого не убивать, и все равно сделать его счастливым. Счастье так относительно и безотносительно одновременно.";
		case 3:
			return "И все противоречия можно ведь опровергнуть одной мыслью: ВСЕ ЛЮДИ ПРЕКРАСНЫ!";
		case 4:
			return "Итак, как ни странно это сознавать, я люблю людей. Как же, как же дать им это знать? Необходимо сделать что-то чертовски важное для окружающих!";
		case 5:
			return "Стоп. Что-то со мной не так что за бредовые мысли? Может, сходить к врачу?";
		case 6:
			return "Нет, это был не бред. Это правда. Итак, если меня попросить о чем-то - я ведь никогда не откажу! Пусть кто-нибудь меня попросит!";
		case 7:
			return "Но этого ждать... лучше сам предложу что-нибудь приятное, важное и полезное. Главное - приятное.";
		case 8:
			return "Да. Я должен выбрать человека. Я отдам ему вещь. Она спрятана в лесу. Там что-то похожее на плиту или камень с надписями.";
		case 9:
			return "Надо уговорить его пойти со мной! Он ведь должен получить эту вещь - я так хочу, чтобы он ее получил!";
		case 10:
			return "Все. Иду. С ним, или без него. Алтарь в лесу. Я отыщу.";
		}
		return "";
	}

	public String getName() {
		return "EmpathicDisease";
	}

}
