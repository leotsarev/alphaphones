package alpha.disease;

import alpha.AlphaIM;
import phones.ProcessModelBase;

public class ParalyzeDisease extends GeneDiseaseBase {

	public ParalyzeDisease(ProcessModelBase model) {
		super(model);
		setChemObj(getAlphaModel().Chemistry.getGene("t"))
	}

	protected String getStageMessage() {
		switch (getStage()) {
		case 1:
			return "Боль пронзает левую руку! От судороги она остается в неестественном положении и не хочет выпрямляться!";
		case 2:
			return "Судорога начинает захватывать шею! Ее сводит судорогой в неестественное положение. Я не могу повернуть или наклонить голову!";
		case 3:
			return "Настигает ужасное ощущение: язык не шевелится во рту да и челюсть тоже неподвижна! Я могу издавать лишь невразумительное мычание...";
		case 4:
			return "Что это? Я не могу сойти с места! Ноги отказали - я даже не могу просто стоять на земле!";
		case HEALING_STAGE:
			return "Какое счастье - мышцы начинают слушаться и паралич проходит!";
		case HEALED_STAGE:
			return "";
		}
		return null;
	}

	protected void updateStatusForStage() {
		switch (getStage()) {
		case 1:
			getAlphaModel().Pain.add(getName(), AlphaIM.LOCATION_LEFT_HAND);
		case 2:
			addStatusMessage("Рука и шея парализованы");
		case 3:
			addStatusMessage("Рука, шея и челюсть парализованы");
		case 4:
			addStatusMessage("Ноги парализованы");
		}
	}

	public String getName() {
		return "ParalyzeDisease";
	}

}
