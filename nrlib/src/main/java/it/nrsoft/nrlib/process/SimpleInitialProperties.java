package it.nrsoft.nrlib.process;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

public class SimpleInitialProperties implements InitialProperties {
	
	private Properties properties = new Properties(); 

	@Override
	public String getProperty(String name, String defaultValue) {
		String value = getProperty(name);
		if(value==null) value = defaultValue;
		return value;
	}

	@Override
	public String getProperty(String name) {
		String prop = properties.getProperty(name);
		
		if(prop!=null)
			prop = prop.replace("${java.io.tmpdir}", System.getProperty("java.io.tmpdir"));
		
		return prop;
	}
	
	@Override
	public String addProperty(String name, String value) {
		properties.put(name, value);
		return value;
	}

	@Override
	public void setProperties(Map<String, String> properties) {
		this.properties.clear();
		for(Entry<String, String> entry : properties.entrySet()) {
			this.properties.setProperty(entry.getKey(), entry.getValue());
		}
		
	}

	@Override
	public Map<String, String> getProperties() {
		Map<String,String> map = new HashMap<>();
		for(Entry<Object, Object> entry: properties.entrySet()) {
			map.put(entry.getKey().toString(), entry.getValue().toString());
		}
		return map;
	}


}
