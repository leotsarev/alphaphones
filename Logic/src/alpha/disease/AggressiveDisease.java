package alpha.disease;

import phones.ProcessModelBase;

public class AggressiveDisease extends VirusDiseaseBase {

	public AggressiveDisease(ProcessModelBase model) {
		super(model);
		setChemObj(getAlphaModel().Chemistry.getGene("z"));
	}

	protected String getStageMessage() {
		switch (getStage()) {
		case 1:
			return "Что-то сегодня меня приследует ощущение провала. Непонятно, почему.";
		case 2:
			return "Люди вокруг меня безмерно раздражают. Тупые, никчемные отродья!";
		case 3:
			return "Дерьмо! Пусть они прекратят свое вечное бормотание, разговоры о судьбах, о человечестве, о науке!";
		case 4:
			return "Да что они понимают?! Сейчас пойду и скажу это любому, а если он скажет что-нибудь наперекор - живо отделаю!";
		case 5:
			return "Фух, надо попуститься. Кажется я стал слишком агрессивным. Может к доктору заглянуть?";
		case 6:
			return "Но нет, эти свиньи бесят! \"Бла-бла-бла!\" Так и тянет уработать кого-нибудь";
		case 7:
			return "Так, кажется, в лесу есть место с рисунками на плитах. Откуда я его знаю - я что, там был? Там тихо...";
		case 8:
			return "Хм, не помню. Так вот, надо оттащить туда кого-нибудь из этих уродов туда и как следует припугнуть!";
		case 9:
			return "Да, я уже предвкушаю, как я сделаю это... или все-таки не надо? Это опасно...";
		case 10:
			return "Все, терпение лопнуло!! Бью любую из этих бесячих тварей тем, что есть под рукой и волоку к камням!";
		}
		return "";
	}

	public String getName() {
		return "AggressiveDisease";
	}

}
