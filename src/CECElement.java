import fr.univ_lille1.m2iagl.dd.ChainElement;

public class CECElement implements ChainElement {
	
	private String line;
	private Object var;
	private String description;
	
	public CECElement(String line, Object var, String description) {
		this.line = line;
		this.var = var;
		this.description = description;
	}

	@Override
	public String getLine() {
		return line;
	}

	@Override
	public String getVariable() {
		return var.toString();
	}

	@Override
	public String getDescription() {
		return description;
	}

}
