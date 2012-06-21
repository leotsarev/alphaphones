package alpha;

import phones.InteractionModel.Descriptor;
import phones.ProcessModelBase;

public abstract class SimpleDiseaseBase extends AlphaProcess  {

	protected static final int START_STAGE = 0;
	protected static final int HEALING_STAGE = -1;
	protected static final int HEALED_STAGE = -2;

	public SimpleDiseaseBase(ProcessModelBase model) {
		super(model);
		setStage(0);
	}
	
	protected abstract int getMaxStage();
	protected abstract void cleanupStatus();
	protected abstract boolean shouldHeal();
	protected abstract String getStageMessage();
	protected abstract void updateStatusForStage();
	protected boolean shouldProgress() {
		return true;
	}
	protected int getQuantLenInMins() {
		return 30;
	}

	private final void setStage(int stage) {
		setIntArg("stage", stage);
	}
	
	protected final int getStage() {
		return getIntArg("stage");
	}

	public final Descriptor handle() {
		if (getStage() == START_STAGE)
		{
			if (getAlphaModel().sick)
			{
				return null; // Мы уже чем-то больны, не будем болеть еще раз
			}
			getAlphaModel().sick = true;
		}
		
		boolean shouldHeal = shouldHeal();
		boolean shouldProgress = !shouldHeal && shouldProgress() && getStage() != getMaxStage();
		
		cleanupStatus();
		
		if (shouldHeal)
		{
			if (getStage() == HEALING_STAGE)
			{
				getAlphaModel().sick = false; //Мы больше не больны.
				setStage(HEALED_STAGE);
			}
			else
			{
				setStage(HEALING_STAGE); //Последнее усилие
			}
		} 
		
		if (shouldProgress)
		{
			setStage(getStage()+1);
		}
		
		if (getStage() != HEALED_STAGE)
		{
			updateStatusForStage();
			scheduleAfterMins(this, getQuantLenInMins());
		}
		
		String message = getStageMessage();
		return message != null ? createMessage(message) : null;
	}

}
