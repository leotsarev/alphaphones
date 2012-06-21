package disease;
import alpha.AlphaIM;
import phones.ProcessModelBase;

public class Malaria extends EarthDiseaseBase {

	public Malaria(ProcessModelBase model) {
		super(model);
	}
	
	protected String getStageMessage() {
		switch (getStage()) {
		case 1:
			return "Как-то мне нездоровится. Бьет озноб и суставы ломит. Хочется выпить горячего и завернуться во что-нибудь.";
		case 2:
			return "Холодно, аж дрожь бьет. И голова разболелась. Надо прилечь как можно скорее - должно пройти.";
		case 3:
			return "Это уже не дрожь, а судороги от холода! Нужно обмотаться ВСЕМ ЧЕМ УГОДНО и лечь, где потеплее.";
		case 4:
			return "Зачем мне люди, когда ангелы окружили меня, чтобы вознести на Солнце! Радуемся всему небу и морю...";
		case HEALING_STAGE:
			return "Кажется, мне намного лучше. Было очень холодно. А сейчас, вроде, ничего.";
		case HEALED_STAGE:
			return "Я полностью выздоровел!";
		}
		return null;
	}

	protected void updateStatusForStage() {
		switch (getStage()) {
		case 1:
			addStatusMessage("Холодно. Ломит суставы.");
		case 2:
			getAlphaModel().Pain.add(getName(), AlphaIM.LOCATION_HEAD, AlphaIM.PAIN_POWER_NORMAL);
			addStatusMessage("Голова болит. Холодно - никак не согреться");
		case 3:
			addStatusMessage("Ничего не соображаю. Вокруг чьи-то голоса...");
		case 4:
			addStatusMessage("Рассказываю об ангелах - торжественно и нараспев");
		case HEALING_STAGE:
			addStatusMessage("Слабость, прихожу в себя");
		}
	}

	public String getName() {
		return "Malaria";
	}

}
