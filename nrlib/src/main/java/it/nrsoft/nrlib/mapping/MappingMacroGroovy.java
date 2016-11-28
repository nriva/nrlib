package it.nrsoft.nrlib.mapping;

import java.util.List;
import java.util.Map;

import it.nrsoft.nrlib.script.ScriptEngine;
import it.nrsoft.nrlib.script.groovy.ScriptEngineGroovy;

public class MappingMacroGroovy extends MappingMacro {
	
	
	ScriptEngine engine = new ScriptEngineGroovy();
	

	@Override
	public Object compute(List<String> params, Map<String, Object> env) {

		
		engine.setEnviron(env);
		return engine.executeByName(params.get(0));


//		assert value.equals(new Integer(20));
//		assert binding.getVariable("x").equals(new Integer(123));

	}

}
