package it.nrsoft.nrlib.process;


import java.util.Collection;
import java.util.Map;
import java.util.Set;

public abstract class DataRow implements Map<String, Object>  {
	
	public void setRowValues(Map<String, Object> values) {
		this.clear();
		this.putAll(values);
	}
	


}
