package it.nrsoft.nrlib.script;

import java.util.HashMap;
import java.util.Map;

public class CachedScriptProvider implements ScriptProvider {
	
	
	protected Map<String,String> scriptCache = new HashMap<String,String>();
	

	@Override
	public String getScriptText(String scriptId) {
		return scriptCache.get(scriptId);
	}


	@Override
	public void setScriptText(String scriptId, String scriptText) {

		scriptCache.put(scriptId, scriptText);
		
	}



}
