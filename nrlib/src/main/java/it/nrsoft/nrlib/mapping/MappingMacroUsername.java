package it.nrsoft.nrlib.mapping;

import java.util.List;
import java.util.Map;


/**
 * Implements the macro @user 
 * @author riva
 *
 */
public class MappingMacroUsername extends MappingMacro {

	@Override
	public Object compute(List<String> params,Map<String,Object> env) {

		try {
			return System.getProperty("user.name");
		}
		catch(Exception e)
		{
			return "unknown";
		}
	}

}
