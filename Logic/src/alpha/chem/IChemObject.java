package alpha.chem;

public interface IChemObject {
	public abstract boolean isPresent(); //Для генов 1, 0.5. Для всех остальных 1
	public abstract boolean isNotPresent(); // 0
	public abstract boolean isStrictlyPresent(); //Для генов 1. 
	public abstract String getName();
}
