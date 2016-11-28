package it.nrsoft.nrlib.mapping;

import java.util.Calendar;
import java.util.List;
import java.util.Map;


/**
 * Implements the macro @now
 * @author riva
 *
 */
public class MappingMacroNow extends MappingMacro {
	
	private Calendar calendar=null;

	@Override
	public Object compute(List<String> params,Map<String,Object> env) {
		if(calendar==null)
			calendar = Calendar.getInstance();
		return calendar;
	}

}
