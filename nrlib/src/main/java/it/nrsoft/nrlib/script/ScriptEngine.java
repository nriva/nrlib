package it.nrsoft.nrlib.script;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringWriter;
import java.util.*;
import javax.script.*;

public abstract class ScriptEngine {
	
	// protected Map<String,String> scriptCache = new HashMap<String,String>();
	
	
	protected ScriptProvider scriptProvider;
	

	
	protected abstract void syncEnvToEngine(ScriptEngineEnviron environ);
	
	protected abstract void syncEnvFromEngine(ScriptEngineEnviron environ);
	
	protected abstract Object invoke(String scriptText, ScriptEngineEnviron environ);

	
	/*
	protected String loadScript(String scriptId) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(scriptId + ".groovy"));
		String myscript="";
		String line = reader.readLine();
		
		while(line!=null)
		{
			myscript += line + " ";
			line = reader.readLine();
		}
		reader.close();
		scriptCache.put(scriptId,myscript);
		return myscript;
	}
	*/
	
	
	public Object execute(String scriptId, ScriptEngineEnviron environ)
	{
		String scriptText = scriptProvider.getScriptText(scriptId);
		
		Object o = invoke(scriptText, environ);
		return o;
	}

	public ScriptEngine(ScriptProvider scriptProvider) {
		super();
		this.scriptProvider = scriptProvider;
	}
	
	

}
