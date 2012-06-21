package alpha.food.deficit;

import alpha.chem.Chemistry;
import phones.ProcessModelBase;
import phones.Utils;

public class FoodDeficitDelta extends alpha.food.FoodDeficitBase {

	public FoodDeficitDelta(ProcessModelBase model) {
		super(model);
		setChem(Chemistry.NUTRIEN + "Delta");
	}


	protected String getHealMessage() {
		switch (getStage()) {
		case 1:
			return "__";
		case 2:
			return "__";
		case 3:
			return "__";
		}
		Utils.assert_(false);
		return null;
	}

	protected String getProgressMessage() {
		switch (getStage()) {
		case 1:
			return "__";
		case 2:
			return "__";
		case 3:
			return "__";
		case 4:
			return "__";
		}
		Utils.assert_(false);
		return null;
	}

	protected void updateStatusForStage() {
		switch (getStage()) {
		case 2:
			
			break;
		case 3:
			
			break;
		case 4:
			
		default:
			
			break;
		}
	}

	protected void cleanupStatus() {
		// TODO Auto-generated method stub

	}

	protected String getStageMessage() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getName() {
		return "FoodDeficitDelta";
	}
}
