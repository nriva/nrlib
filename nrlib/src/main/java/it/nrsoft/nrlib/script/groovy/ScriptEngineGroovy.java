package it.nrsoft.nrlib.script.groovy;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import it.nrsoft.nrlib.script.ScriptEngine;
import it.nrsoft.nrlib.script.ScriptEngineEnviron;
import it.nrsoft.nrlib.script.ScriptProvider;

import java.util.Map.Entry;


public class ScriptEngineGroovy extends ScriptEngine {
	
	public ScriptEngineGroovy(ScriptProvider scriptProvider) {
		super(scriptProvider);
		// TODO Auto-generated constructor stub
	}





	Binding binding = new Binding();

	@Override
	protected void syncEnvToEngine(ScriptEngineEnviron environ) {

		for(Entry<String,Object> entry : environ.entrySet())
		{
			binding.setVariable(entry.getKey(), entry.getValue());
		}
	}

	@Override
	protected void syncEnvFromEngine(ScriptEngineEnviron environ) {
		
		for(String key: environ.keySet())
		{
			environ.put(key,binding.getVariable(key));
		}
	}





	@Override
	public Object invoke(String scriptText, ScriptEngineEnviron environ) {
		
		syncEnvToEngine(environ);
		
		GroovyShell shell = new GroovyShell(binding);
		
		Object result = shell.evaluate(scriptText);
		
		syncEnvFromEngine(environ);
		return result;
	}

}
