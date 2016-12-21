package it.nrsoft.nrlib.script;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringWriter;
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
				e.printStackTrace();
			}
		Object o = execute(script);
		return o;
	}
	
	protected abstract void syncEnvToEngine();
	
	protected abstract void syncEnvFromEngine();
	
	protected abstract Object invoke(String script);

	protected abstract String loadScript(String scriptId) throws IOException ;

	public Object execute(Reader reader) throws IOException {
		
		StringWriter writer = new StringWriter();
		int c = reader.read();
		while(c!=-1)
		{
			writer.write(c);
			c=reader.read();
		}
		
		reader.close();
		return execute(writer.toString());

	}
}
