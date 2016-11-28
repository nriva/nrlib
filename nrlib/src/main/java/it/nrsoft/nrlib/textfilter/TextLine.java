package it.nrsoft.nrlib.textfilter;

@Deprecated
public class TextLine {
	
	private String line="";
	
	private boolean output=true;

	public String getLine() {
		return line;
	}

	public void setLine(String line) {
		this.line = line;
	}

	public boolean isOutput() {
		return output;
	}

	public void setOutput(boolean output) {
		this.output = output;
	}

	public TextLine(String line) {
		super();
		this.line = line;
	}
	
	

}
