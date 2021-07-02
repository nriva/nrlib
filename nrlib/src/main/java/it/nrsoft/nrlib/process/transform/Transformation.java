package it.nrsoft.nrlib.process.transform;

import java.util.Map;

import it.nrsoft.nrlib.process.DataRow;


public abstract class Transformation {
	
	public abstract Object apply(Map<String, Object> variables, DataRow row);

}
