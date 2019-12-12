package application;

/**
 * @author ESI-2019-EIC2-02
 *
 */
public class MethodData {
	private int methodId;//id do metodo obtido do excel
	private String packageName, className, methodName;// nome do package, da classe, e nome do metodo obtidos do excel
	private int loc, cyclo;//numero de linhas de codigo e complexidade ciclomatica obtidos do excel
	private double atfd, laa;//numero de atfd e laa obtidos do excel
	private boolean is_long_method, is_feature_envy, iplasma, pmd;//valores de longMethod,feature_envy,iplasma e pmd obtidos do excel
	private boolean is_long_method_by_rules, is_feature_envy_by_rules;//valores de longMethod e feature_envy para as regras definidas pelo utilizador

	public MethodData(int methodId, String packageName, String className, String methodName, int loc, int cyclo,
			double atfd, double laa, boolean is_long_method, boolean is_feature_envy, boolean iplasma, boolean pmd) {
		this.methodId = methodId;
		this.packageName = packageName;
		this.className = className;
		this.methodName = methodName;
		this.loc = loc;
		this.cyclo = cyclo;
		this.atfd = atfd;
		this.laa = laa;
		this.is_long_method = is_long_method;
		this.is_feature_envy = is_feature_envy;
		this.iplasma = iplasma;
		this.pmd = pmd;
	}

	/**
	 * Defines the value of the field that points if the method 
	 * is or isn't a long method by the rules specified
	 * @param sets the value of the specified field
	 */
	public void setIs_long_method_by_rules(boolean set) {
		is_long_method_by_rules = set;
	}

	/**
	 * Defines the value of the field that points if the method 
	 * is or isn't a feature envy method by the rules specified
	 * @param sets the value of the specified field
	 */
	public void setIs_feature_envy_by_rules(boolean set) {
		is_feature_envy_by_rules = set;
	}

	/**
	 * @return the id of the method obtained from the Excel file
	 */
	public int getMethodId() {
		return methodId;
	}

	/**
	 * @return the name of the Package
	 */
	public String getPackageName() {
		return packageName;
	}

	/**
	 * @return the name of the Class
	 */
	public String getClassName() {
		return className;
	}

	
	/**
	 * @return the method name
	 */
	public String getMethodName() {
		return methodName;
	}

	/**
	 * @return the number of lines of code
	 */
	public int getLoc() {
		return loc;
	}

	/**
	 * @return the cyclomatic complexity
	 */
	public int getCyclo() {
		return cyclo;
	}

	/**
	 * @return the number of accesses of
	 * the method to methods of other Classes
	 */
	public double getAtfd() {
		return atfd;
	}

	/**
	 * @return the number of accesses of
	 * the method to attributes of own Class
	 */
	public double getLaa() {
		return laa;
	}

	/**
	 * @return true if is_long_method
	 * 		   false otherwise
	 */
	public boolean getIs_long_method() {
		return is_long_method;
	}

	/**
	 * @return true if is_feature_envy
	 * 		   false otherwise
	 */
	public boolean getIs_feature_envy() {
		return is_feature_envy;
	}

	/**
	 * @return true is is iPlasma
	 * 		   false otherwise
	 */
	public boolean getIplasma() {
		return iplasma;
	}

	/**
	 * @return true if is Pmd
	 * 		   false otherwise
	 */
	public boolean getPmd() {
		return pmd;
	}

	/**
	 * @return true is is_long_method_by_rules
	 * 		   false otherwise
	 */
	public boolean getIs_long_method_by_rules() {
		return is_long_method_by_rules;
	}

	/**
	 * @return true id is_feature_envy_by_rules
	 * 		   false otherwise
	 */
	public boolean getIs_feature_envy_by_rules() {
		return is_feature_envy_by_rules;
	}
}
