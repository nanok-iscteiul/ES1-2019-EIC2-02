package main;

public class Method {
	
	private int methodId;
	private String name;
	
	public Method(int methodId, String name) {
		this.methodId=methodId;
		this.name=name;
	}

	public int getMethodId() {
		return methodId;
	}

	public String getName() {
		return name;
	}
	
	public String toString() {
		return "MethodId: " + methodId + " ,Method name: "+ name+".";
	}

}
