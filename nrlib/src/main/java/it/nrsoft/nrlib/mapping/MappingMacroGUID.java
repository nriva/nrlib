package it.nrsoft.nrlib.mapping;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class MappingMacroGUID extends MappingMacro {

	@Override
	public Object compute(List<String> params, Map<String, Object> env) {
		// TODO Auto-generated method stub
		return UUID.randomUUID().toString();
	}

}
