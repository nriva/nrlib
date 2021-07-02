package it.nrsoft.nrlib.script;

public interface ScriptProvider {
	
	
	String getScriptText(String scriptId);
	
	void setScriptText(String scriptId, String scriptText);

}
