package application;

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

	public void setIs_long_method_by_rules(boolean set) {// definir o valor do campo que indica se o metodo é ou não
															// long method pelas regras definidas
		is_long_method_by_rules = set;
	}

	public void setIs_feature_envy_by_rules(boolean set) {// definir o valor do campo que indica se o método é ou não
															// feature_envy pelas regras definidas
		is_feature_envy_by_rules = set;
	}

	public int getMethodId() {
		return methodId;
	}

	public String getPackageName() {
		return packageName;
	}

	public String getClassName() {
		return className;
	}

	public String getMethodName() {
		return methodName;
	}

	public int getLoc() {
		return loc;
	}

	public int getCyclo() {
		return cyclo;
	}

	public double getAtfd() {
		return atfd;
	}

	public double getLaa() {
		return laa;
	}

	public boolean getIs_long_method() {
		return is_long_method;
	}

	public boolean getIs_feature_envy() {
		return is_feature_envy;
	}

	public boolean getIplasma() {
		return iplasma;
	}

	public boolean getPmd() {
		return pmd;
	}

	public boolean getIs_long_method_by_rules() {
		return is_long_method_by_rules;
	}

	public boolean getIs_feature_envy_by_rules() {
		return is_feature_envy_by_rules;
	}
}
