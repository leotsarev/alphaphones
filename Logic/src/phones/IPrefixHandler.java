package phones;

public interface IPrefixHandler extends IProcess {
	void setSuffixValue(String suffix);

	boolean isValidSuffix(String suffix);

	boolean isStartOfSuffix(String suffix);
}
