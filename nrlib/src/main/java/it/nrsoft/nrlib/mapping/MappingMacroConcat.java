package it.nrsoft.nrlib.mapping;

import java.util.List;
import java.util.Map;


public class MappingMacroConcat extends MappingMacro {

	@Override
	public Object compute(List<String> params,Map<String, Object> env) {
		
		StringBuilder builder = new StringBuilder();
		
		for(String param : params)
			builder.append(env.get(param.trim()).toString().trim());
		return builder.toString();
	}

}
