package alpha;

import alpha.chem.Chemistry.Nutrien;
import alpha.food.CheckFoodDeficits;
import alpha.oxygen.*;
import phones.InteractionModel.Descriptor;
import phones.ProcessModelBase;

public class AlphaInit extends AlphaProcess {

	public AlphaInit(ProcessModelBase model) {
		super(model);
	}

	public Descriptor handle() {
		getAlphaModel().setInitCompleted();
		getAlphaModel().male = true;
		getAlphaModel().updateCurrentFaction();
		getAlphaModel().male = true;
		getAlphaModel().inHouse = true;
		getAlphaModel().wearingMask = false;
		scheduleNow(new ScheduleNextOxygen(model));
		scheduleAfterMins(new CheckFoodDeficits(model), 0);
		
		scheduleAfterMins(new UpdateRad(model), 10);
		
		Nutrien[] n = getAlphaModel().Chemistry.getNutrienArray();
		for (int i=0;i<n.length;i++)
		{
			getAlphaModel().consumeNutrien(n[i]);
		}
		return null;
	}

	public String getName() {
		return "AlphaInit";
	}
}
