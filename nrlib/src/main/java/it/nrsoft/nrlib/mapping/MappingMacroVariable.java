package it.nrsoft.nrlib.mapping;

import java.util.List;
import java.util.Map;

public class MappingMacroVariable extends MappingMacro {

	@Override
	public Object compute(List<String> params, Map<String, Object> env) {

		return env.get(params.get(0));
	}

}
