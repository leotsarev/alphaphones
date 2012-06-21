package alpha.food;

import alpha.ThreeDigitCodeBase;
import alpha.chem.Chemistry.Nutrien;
import phones.InteractionModel.Descriptor;
import phones.ProcessModelBase;
import phones.Utils;

public class FoodItem extends ThreeDigitCodeBase {

	public FoodItem(ProcessModelBase model) {
		super(model);
	}

	public String getName() {
		return "FoodItem";
	}

	public Descriptor handle() {
		Nutrien[] nutriens = getAlphaModel().Chemistry.getNutrienArray();
		Utils.assert_(nutriens.length == 6);
		
		int foodFlag = 97 - getCode();
		for (int i =0; i<nutriens.length; i++)
		{
			int bitFlag = foodFlag & (1 << i);
			if (bitFlag>0)
			{
				getAlphaModel().consumeNutrien(nutriens[i]);
			}
		}
		return createMessage("Я поел{/а}");
	}


}
