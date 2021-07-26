package it.nrsoft.nrlib.process;


import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class DataRow implements Map<String, Object>, Comparable<DataRow>  {
	
	private String[] compareFieldNames = null;
	
	public void setRowValues(Map<String, Object> values) {
		this.clear();
		this.putAll(values);
	}

	@Override
	public String toString() {
		
		String s = "";
		for (String key : this.keySet()) {
			if(s.length()>0) s += ",";
		    s += key + "=" + this.get(key);
		}		
		return s;
		
	}

	@Override
	public int compareTo(DataRow other) {
		
		int compareResult = 0;
		
		for(String compareFieldName : compareFieldNames) {
			Comparable v1 = (Comparable) this.get(compareFieldName);
			Comparable v2 = (Comparable) other.get(compareFieldName);
			compareResult = v1.compareTo(v2);
			if(compareResult!=0) break;
		}
		
		return compareResult;
	}

	public String[] getCompareFieldNames() {
		return compareFieldNames;
	}

	public void setCompareFieldNames(String[] compareFieldNames) {
		this.compareFieldNames = compareFieldNames;
	}
	


}
