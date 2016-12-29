package dd.impl;
import dd.ChainElement;

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
		if(var==null){
			return null;
		}
		return var.toString();
	}

	@Override
	public String getDescription() {
		return description;
	}
	
	public void setLine(String l){
		line=l;
	}

}
