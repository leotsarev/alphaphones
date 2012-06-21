package alpha.food.deficit;

import phones.ProcessModelBase;
import phones.Utils;
import alpha.chem.Chemistry;
import alpha.food.FoodDeficitBase;

public class FoodDeficitPhi extends FoodDeficitBase {

	public FoodDeficitPhi(ProcessModelBase model) {
		super(model);
		setChem(Chemistry.NUTRIEN + "Phi");
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
		return "FoodDeficitPhi";
	}

}
