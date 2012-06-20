package alpha.food;

import alpha.chem.*;
import alpha.food.deficit.*;
import phones.InteractionModel.Descriptor;
import phones.ProcessModelBase;

public class CheckFoodDeficits extends alpha.AlphaProcess {

	public CheckFoodDeficits(ProcessModelBase model) {
		super(model);
	}

	public Descriptor handle() {
		if (!getAlphaModel().sick)
		{
			boolean deficitStarted = startDeficitIfNeeded(new FoodDeficitAlpha(model), Chemistry.ALPHA + 1)
					|| startDeficitIfNeeded(new FoodDeficitBeta(model), Chemistry.BETA + 11)
					|| startDeficitIfNeeded(new FoodDeficitGamma(model), Chemistry.GAMMA + 2) 
					|| startDeficitIfNeeded(new FoodDeficitDelta(model), Chemistry.DELTA + 11)
					
					|| (!getAlphaModel().Chemistry.has(Chemistry.KAPPA + 1) &&
							(
									startDeficitIfNeeded(new FoodDeficitPhi(model), Chemistry.BETA + 11) 
									|| startDeficitIfNeeded(new FoodDeficitChi(model), Chemistry.BETA + 11)
							)
						);
			if (deficitStarted)
			{
				//Remove warning
			}
		}
		
		scheduleAfterMins(this, 10);
		return null;
	}

	private boolean startDeficitIfNeeded(ChemActionBase deficit, String chemName) {
		IChemObject targetChemObj = getAlphaModel().Chemistry.getByName(chemName);
		boolean willStart = targetChemObj.isNotPresent() && saveThrowFailed();
		
		if (willStart)
		{
			getAlphaModel().sick = true;
			deficit.setTargetChemObj(targetChemObj);
			scheduleNow(deficit);
		}
		return willStart;
	}

	private boolean saveThrowFailed() {
		return getAlphaModel().randomInt(100) == 1;
	}

	public String getName() {
		return "CheckFoodDeficits";
	}

}
