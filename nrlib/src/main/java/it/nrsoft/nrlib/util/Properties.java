package it.nrsoft.nrlib.util;


import java.io.*;
import java.util.*;
import java.util.Map.Entry;

	


/**
* @author nriva
*
*/
public class Properties {
	
	private List<String> multipleValuePropNames = new ArrayList<String>(); 
	
	private java.util.Properties properties = null;
	
	public void addMulipleValuePropName(String name)
	{
		multipleValuePropNames.add(name);
	}
	
	public Map<String,String> getMultipleValueProp(String name)
	{
		
		Map<String,String> map = new TreeMap<String,String>();
		
		
		boolean found = false;
		for(Object key : properties.keySet())
		{
			if(key.toString().startsWith(name))
				found = true;
		}
		
		if(!found)
			return null;
		
		int id = 1;
		found = false;
		for(Object key : properties.keySet())
		{
			if(key.toString().startsWith(name + "." + String.valueOf(id)))
				found = true;
			
			if(found)
			{
				String[] keyParts = key.toString().split("\\.");
				
				String value = properties.getProperty(key.toString());
				map.put(keyParts[2], value );
			}
			
		}		
		
		return map;
		
			
		
	}
	
	public void loadProperties(InputStream propConnStream) throws Exception {
		
		properties = new java.util.Properties();

		if (propConnStream != null)
			properties.load(propConnStream);
		else
			throw new Exception("Properties.getProp: PropConnStream == null");

	}
	
	public boolean getBooleanProp(String name)
	{
		boolean value=false;
		if(properties.containsKey(name) )
			value = properties.getProperty(name).equalsIgnoreCase("true");
		return value;
	}
	
	public String getStringProp(String name)
	{
		String value="";
		if(properties.containsKey(name) )
			value = properties.getProperty(name);
		return value;
	}
	
	public String getStringProp(String name,String defaultValue)
	{
		String value=defaultValue;
		if(properties.containsKey(name) )
			value = properties.getProperty(name);
		return value;
	}
	
	public String[] getStringArrayProp(String name, String separator)
	{
		String[] value=new String[]{};
		if(properties.containsKey(name) )
			value = properties.getProperty(name).split(separator);
		return value;
	}
	
	
	public Map<String,String> prefixSubset(String prefix, java.util.Properties properties)
	{
		
		Map<String,String> map = new TreeMap<String,String>();
		
		for(Entry<Object, Object> entry : properties.entrySet())
			if( entry.getKey().toString().startsWith(prefix) )
				map.put(entry.getKey().toString().substring(prefix.length())
						, entry.getValue().toString());
		
		return map;
		
	}	
	
	
	
	

}
