package alpha.food;

import phones.InteractionModel.Descriptor;
import phones.ProcessModelBase;
import phones.Utils;

public abstract class FoodDeficitBase extends NutrienActionBase {

	public FoodDeficitBase(ProcessModelBase model) {
		super(model);
		setIntArg("stage", 0);
	}

	protected int getMinsToDeficitProgress()
	{
		return 20;
	}
	
	protected int getMinsToDeficitHeal() {
		return 5;
	}

	public final Descriptor handle() {
		
		int stageChange = getStageChange();
		int nextStage = getStage() + stageChange;
		
		Utils.assert_(nextStage >=0);
		
		setIntArg("stage", nextStage);
		
		cleanupStatus();
		
		if (nextStage == 0) {
			getAlphaModel().sick = false;
			return null;
		} 
		
		updateStatusForStage();
		
		scheduleAfterMins(this, stageChange > 0 ? getMinsToDeficitProgress() : getMinsToDeficitHeal());
		
		if (stageChange == 0)
		{
			return null;
		}

		String message = stageChange > 0 ? getProgressMessage() : getHealMessage();
		return message !=null ? createMessage(message) : null;
	}

	

	protected abstract String getHealMessage();

	protected abstract String getProgressMessage();

	protected abstract void updateStatusForStage();

	private int getStageChange() {
		int stageChange;
		if (getStage() == getMaxStage())
		{
			stageChange = 0;
		}
		else
		{
			stageChange = getChemObj().isPresent() ? -1 : +1;
		}
		return stageChange;
	}

	protected final int getStage() {
		return getIntArg("stage");
	}

	private int getMaxStage() {
		return 5;
	}

	protected abstract void cleanupStatus();

}
