package alpha.food;

import phones.ISerializer;
import phones.Utils;

public class Nutrient {

	private final int nutrientNum;
	private static final String[] names = {"Alpha", "Beta", "Gamma", "Delta", "Phi", "Chi"};
	public static final int VANISH_MINS = 3 * 60;
	private boolean nutrientValue;
	
	private Nutrient(int nutrientNum)
	{
		Utils.assert_(nutrientNum >= 0);
		Utils.assert_(nutrientNum < names.length);
		this.nutrientNum = nutrientNum;
		
	}
	
	public void serialize(ISerializer ser) {
		ser.writeBool(isNutrientValue());
	}

	public void unserialize(ISerializer ser) {
		setNutrientValue(ser.readBool());
	}
	
	public String toString()
	{
		return getName() + (isNutrientValue() ? "+" : "-");
	}

	public boolean isNutrientValue() {
		return nutrientValue;
	}

	public void setNutrientValue(boolean nutrientValue) {
		this.nutrientValue = nutrientValue;
	}

	static Nutrient[] createAll() {
		Nutrient[] result = new Nutrient[names.length];
		for (int i = 0; i<names.length;i++)
		{
			result[i] = new Nutrient(i);
		}
		return result;
	}

	public String getName() {
		return names[nutrientNum];
	}

	public int getNum() {
		return nutrientNum;
	}

	public String getDeficitName() {
		return getName() + "Deficit";
	}
	
}
