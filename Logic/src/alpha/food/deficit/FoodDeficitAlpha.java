package alpha.food.deficit;

import alpha.AlphaIM;
import phones.ProcessModelBase;
import phones.Utils;

public class FoodDeficitAlpha extends alpha.food.FoodDeficitBase {

	private static final String ALPHA_DEFICIT_PSI = "alphaDeficitPsi";
	private static final String ALPHA_DEFICIT_STAY = "alphaDeficitStay";

	public FoodDeficitAlpha(ProcessModelBase model) {
		super(model);
	}

	protected String getStageMessage() {
		switch (getStage()) {
		case 1:
			return "Локоть пронзила мгновенная боль! Боль быстро прошла";
		case 2:
			return "Как болят колени! Похоже, я перетрудился.";
		case 3:
			return "Сука, как меня все достало. И этот хмырь меня так бесит, пойду скажу ему!";
		case 4:
			return "Аааа, колени болят жутко, просто ужас, наступать на них нельзя. Впрочем, ничего и не хочется, лучше полежу.";
		case HEALING_STAGE:
			return "Кажется, боль начинает проходить, уже можно встать и даже пройтись... как же круто!";
		case HEALED_STAGE:
			return "Я полностью выздоровел!";
		}
		Utils.assert_(false);
		return null;
	}

	protected void updateStatusForStage() {
		switch (getStage()) {
		case 2:
			getAlphaModel().Pain.add(getName(), AlphaIM.LOCATION_KNEE, AlphaIM.PAIN_POWER_NORMAL);
			break;
		case 3:
			addStatusMessage(ALPHA_DEFICIT_PSI, "Ненависть!");
			getAlphaModel().Pain.add(getName(), AlphaIM.LOCATION_KNEE, AlphaIM.PAIN_POWER_NORMAL);
			break;
		case 4:
			getAlphaModel().Pain.add(getName(), AlphaIM.LOCATION_KNEE, AlphaIM.PAIN_POWER_STRONG);
			addStatusMessage(ALPHA_DEFICIT_STAY, "Не могу стоять");
			addStatusMessage(ALPHA_DEFICIT_PSI, "Апатия");
			break;
		case HEALED_STAGE:
			addStatusMessage(ALPHA_DEFICIT_STAY, "Передвигаюсь с осторожностью");
		default:
			break;
		}
	}

	public void cleanupStatus() {
		removeStatusMessage(ALPHA_DEFICIT_PSI);
		removeStatusMessage(ALPHA_DEFICIT_STAY);
		getAlphaModel().Pain.remove(getName(), AlphaIM.LOCATION_KNEE);
	}

	public String getName() {
		return "FoodDeficitAlpha";
	}
}
