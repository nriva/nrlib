package it.nrsoft.nrlib.script.groovy;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import it.nrsoft.nrlib.script.ScriptEngine;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map.Entry;


public class ScriptEngineGroovy extends ScriptEngine {
	
	Binding binding = new Binding();

	@Override
	protected void syncEnvToEngine() {

		for(Entry<String,Object> entry : environ.entrySet())
		{
			binding.setVariable(entry.getKey(), entry.getValue());
		}
	}

	@Override
	protected void syncEnvFromEngine() {
		
		for(String key: environ.keySet())
		{
			environ.put(key,binding.getVariable(key));
		}
	}

	@Override
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



	@Override
	public Object invoke(String script) {
		GroovyShell shell = new GroovyShell(binding);
		return shell.evaluate(script);
	}

}
