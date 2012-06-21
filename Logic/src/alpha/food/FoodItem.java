package alpha.food;

import alpha.chem.Chemistry.Nutrien;
import phones.IPrefixHandler;
import phones.InteractionModel.Descriptor;
import phones.ProcessModelBase;
import phones.Utils;

public class FoodItem extends alpha.AlphaProcess implements IPrefixHandler {

	private static final int SUFFIX_LENGTH = 6;

	public FoodItem(ProcessModelBase model) {
		super(model);
	}

	public String getName() {
		return "FoodItem";
	}

	public void setSuffixValue(String suffix) {
		Utils.assert_(isValidSuffix(suffix));
		setIntArg("food", getFoodFromSuffix(suffix));
	}

	public boolean isValidSuffix(String suffix) {
		if (suffix.length() != SUFFIX_LENGTH)
		{
			return false;
		}
		int foodFlag = getFoodFromSuffix(suffix);
		int check = Integer.valueOf(suffix.substring(3, 3)).intValue();
		
		return Utils.quickHash(foodFlag) == check;
	}

	private int getFoodFromSuffix(String suffix) {
		return Integer.valueOf(suffix.substring(0, 3)).intValue();
	}

	public boolean isStartOfSuffix(String suffix) {
		return suffix.length() <= SUFFIX_LENGTH;
	}

	public Descriptor handle() {
		Nutrien[] nutriens = getAlphaModel().Chemistry.getNutrienArray();
		Utils.assert_(nutriens.length == 6);
		
		int foodFlag = getIntArg("food");
		for (int i =0; i<nutriens.length; i++)
		{
			if ((foodFlag & (1 << i))>0)
			{
				SetNutrient nutrient = new SetNutrient(model);
				nutrient.setTargetChemObj(nutriens[i]);
				scheduleNow(nutrient);
			}
		}
		return null;
	}
}
