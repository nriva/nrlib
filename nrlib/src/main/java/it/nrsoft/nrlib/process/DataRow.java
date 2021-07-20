package it.nrsoft.nrlib.process;


import java.util.Collection;
import java.util.Map;
import java.util.Set;

public abstract class DataRow implements Map<String, Object>  {
	
	public void setRowValues(Map<String, Object> values) {
		this.clear();
		this.putAll(values);
	}

	@Override
	public String toString() {
		
		StringBuilder sb = new StringBuilder();
		
		for (Entry<String, Object> entry : this.entrySet()) {
		    sb.append(entry.getKey() + "=" + entry.getValue() + ",");
		}		
		
		return sb.toString();
		
	}
	


}
