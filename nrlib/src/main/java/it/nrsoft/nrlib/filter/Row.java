package it.nrsoft.nrlib.filter;

import java.util.*;
import java.util.Map.Entry;

public abstract class Row {
	
	protected List<String> parts;


	public List<String> getParts() {
		return parts;
	}
	
	public String getPart(int i) {
		return parts.get(i);
	}
	
	public String setPart(int i,String v) {
		return parts.set(i,v);
	}	
	

	/**
	 * @param parts
	 */
	public Row(List<String> parts) {
		this.parts = parts;
	}

	public abstract String getString();
	
	
	public Map<String, String> getAsMap() {

		Map<String,String> map = new TreeMap<String,String>();
		int c=0;
		for(String part : parts)
		{
			map.put("_" + String.valueOf(c+1),part);
			c++;
		}
		return map;
	}

	
	public void setFromMap(Map<String, Object> map) {

		for(Entry<String,Object> entry : map.entrySet())
		{
			parts.set(Integer.parseInt(entry.getKey().substring(1))-1, entry.getValue().toString());
		}
		
	}
	



}
