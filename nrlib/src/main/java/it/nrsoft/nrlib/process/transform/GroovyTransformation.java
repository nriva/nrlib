package it.nrsoft.nrlib.process.transform;



import java.util.Map;
import java.util.Map.Entry;

import it.nrsoft.nrlib.process.DataRow;
import it.nrsoft.nrlib.script.ScriptEngineEnviron;
import it.nrsoft.nrlib.script.ScriptEngineEnvironSimple;
import it.nrsoft.nrlib.script.groovy.ScriptEngineGroovy;

public class GroovyTransformation extends Transformation {
	
	
	String scriptId = null;
	
	public GroovyTransformation(ScriptEngineGroovy engine, String scriptId) {
		super();
		this.engine = engine;
		this.scriptId = scriptId;
	}

	private ScriptEngineGroovy engine = null;

	@Override
	public Object apply(Map<String, Object> variables,DataRow row) {
		
		ScriptEngineEnviron environ = new ScriptEngineEnvironSimple();

		/*
		for(Entry<String, Object> entry: row.entrySet()) {
			environ.put(entry.getKey(), entry.getValue());	
		}
		*/
		environ.put("row", row);
		
		
		for(Entry<String, Object> entry: variables.entrySet()) {
			environ.put(entry.getKey(), entry.getValue());	
		}

		
		Object value = engine.execute(scriptId, environ);
		
		
		for(Entry<String, Object> entry: environ.entrySet()) {
			if( variables.containsKey(entry.getKey()))
				variables.put(entry.getKey(), entry.getValue());	
		}
		
		
		
		return value;
	}


}
