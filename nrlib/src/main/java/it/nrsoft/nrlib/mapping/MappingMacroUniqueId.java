package it.nrsoft.nrlib.mapping;

import java.util.List;
import java.util.Map;


/**
 * Implements the macro @id (a long unique id)
 * @author riva
 *
 */
public class MappingMacroUniqueId extends MappingMacro {
	
	private long id = 0L;

	@Override
	public Object compute(List<String> params,Map<String,Object> env) {
		id++;
		return new Long(id);
	}

}
