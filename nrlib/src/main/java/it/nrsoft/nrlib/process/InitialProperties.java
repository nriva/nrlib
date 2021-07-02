package it.nrsoft.nrlib.process;

import java.util.Map;

public interface InitialProperties {
	
	
	String getProperty(String name, String defaultValue);
	
	String getProperty(String name);
	
	String addProperty(String name, String value);
	
	void setProperties(Map<String,String> properties);
	
	Map<String,String> getProperties();
	

}
