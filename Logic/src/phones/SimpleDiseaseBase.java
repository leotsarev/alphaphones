package phones;

import phones.InteractionModel.Descriptor;
import phones.ProcessModelBase.Process;


public abstract class SimpleDiseaseBase extends Process {

	protected static final int START_STAGE = 0;
	protected static final int HEALING_STAGE = -1;
	protected static final int HEALED_STAGE = -2;

	public SimpleDiseaseBase(ProcessModelBase model) {
		super(model);
	}

	protected abstract int getMaxStage();

	protected abstract void cleanupStatus();

	protected abstract boolean shouldHeal();

	protected abstract String getStageMessage();

	protected abstract void updateStatusForStage();
	
	protected abstract void onDiseaseStart();
	protected abstract boolean shouldStartDisease();
	protected abstract void onDiseaseEnd();

	protected boolean shouldProgress() {
		return true;
	}

	protected int getQuantLenInMins() {
		return 30;
	}

	protected final void setStage(int stage) {
		setIntArg("stage", stage);
	}

	protected final int getStage() {
		return getIntArg("stage");
	}

	public final Descriptor handle() {
		if (getStage() == START_STAGE) {
			if (shouldStartDisease())
			{
				onDiseaseStart();
			}
			else
			{
				return null;
			}
		}
		
		boolean shouldHeal = shouldHeal();
		boolean shouldProgress = !shouldHeal && shouldProgress() && getStage() != getMaxStage();
		
		cleanupStatus();
		
		if (shouldHeal)
		{
			if (getStage() == HEALING_STAGE)
			{
				onDiseaseEnd(); //Мы больше не больны.
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