package it.nrsoft.nrlib.process.transform;

import java.util.Map;

import it.nrsoft.nrlib.process.DataRow;

/**
 * A transformation compute a single value from global variables and the datarow. 
 * @author riva
 *
 */
public abstract class Transformation {
	
	public abstract Object apply(Map<String, Object> variables, DataRow row);

}
