package alpha.food;

import alpha.AlphaIM;
import alpha.chem.*;
import alpha.food.deficit.*;
import phones.InteractionModel.Descriptor;
import phones.ProcessModelBase;

public class CheckFoodDeficits extends alpha.AlphaProcess {

	private boolean deficitStarted;

	public CheckFoodDeficits(ProcessModelBase model) {
		super(model);
	}

	public Descriptor handle() {
		if (AlphaIM.DEMO_MODE) {
			return null;
		} 
		startDeficitIfNeeded(new FoodDeficitAlpha(model), Chemistry.ALPHA + 1);
		startDeficitIfNeeded(new FoodDeficitBeta(model), Chemistry.BETA + 11);
		startDeficitIfNeeded(new FoodDeficitGamma(model), Chemistry.GAMMA + 2);
		startDeficitIfNeeded(new FoodDeficitDelta(model), Chemistry.DELTA + 11);
		if (!getAlphaModel().Chemistry.has(Chemistry.KAPPA + 1))
		{
			startDeficitIfNeeded(new FoodDeficitPhi(model), Chemistry.PHI + 11); 
			startDeficitIfNeeded(new FoodDeficitChi(model), Chemistry.CHI + 1);
		}
		
		scheduleAfterMins(this, 10);
		return null;
	}

	private void startDeficitIfNeeded(IChemAction deficit, String chemName) {
		if (deficitStarted)
		{
			return;
		}
		IChemObject targetChemObj = getAlphaModel().Chemistry.getByName(chemName);
		boolean willStart = targetChemObj.isNotPresent() && saveThrowFailed();
		
		if (willStart)
		{
			deficitStarted = true;
			deficit.setChem(targetChemObj);
			scheduleNow(deficit);
		}
	}

	private boolean saveThrowFailed() {
		return getAlphaModel().randomInt(100) == 1;
	}

	public String getName() {
		return "CheckFoodDeficits";
	}

}
