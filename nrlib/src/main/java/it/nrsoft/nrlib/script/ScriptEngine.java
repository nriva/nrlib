package it.nrsoft.nrlib.script;

import java.io.IOException;
import java.util.*;
import javax.script.*;

public abstract class ScriptEngine {
	
	protected Map<String,Object> environ = new HashMap<String,Object>();
	
	protected Map<String,String> scriptCache = new HashMap<String,String>();
	
	public Map<String, Object> getEnviron() {
		return environ;
	}

	public void setEnviron(Map<String, Object> environ) {
		this.environ = environ;
	}

	
	public Object execute(String script)
	{
		syncEnvToEngine();
		Object o = invoke(script);
		syncEnvFromEngine();
		return o;
	}
	
	public Object executeByName(String scriptId)
	{
		String script = null;
		
		if(!scriptCache.containsKey(scriptId))
			try {
				script = loadScript(scriptId);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		Object o = execute(script);
		return o;
	}
	
	protected abstract void syncEnvToEngine();
	
	protected abstract void syncEnvFromEngine();
	
	protected abstract Object invoke(String script);

	protected abstract String loadScript(String scriptId) throws IOException ;
}
