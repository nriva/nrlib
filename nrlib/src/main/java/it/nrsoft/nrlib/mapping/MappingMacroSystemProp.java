package it.nrsoft.nrlib.mapping;

import java.util.*;

public class MappingMacroSystemProp extends MappingMacro {

	@Override
	public Object compute(List<String> params, Map<String, Object> env) {

		try {
			return System.getProperty(params.get(0));
		}
		catch(Exception e)
		{
			return "unknown";
		}
	}

}
