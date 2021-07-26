package it.nrsoft.nrlib.mapping;

import java.util.List;
import java.util.Map;

public class MappingMacroConst extends MappingMacro {

	@Override
	public Object compute(List<String> params, Map<String, Object> env) {
		
		String value = params.get(0);
		
		if(value.startsWith("'") && value.endsWith("'"))
			return value.substring(1, value.length()-1);
		
		return Double.parseDouble(value);
		
	}

}
