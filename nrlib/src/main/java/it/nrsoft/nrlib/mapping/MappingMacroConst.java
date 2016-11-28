package it.nrsoft.nrlib.mapping;

import java.util.List;
import java.util.Map;

public class MappingMacroConst extends MappingMacro {

	@Override
	public Object compute(List<String> params, Map<String, Object> env) {
		// TODO Auto-generated method stub
		return params.get(0);
	}

}
