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
	
	
	public List<Map<String,String>> getMultipleValueProp(String name)
	{
		
		
		List<Map<String,String>> maps = new LinkedList<Map<String,String>>();
		
		
		boolean found = false;
		for(Object key : properties.keySet())
		{
			if(key.toString().startsWith(name))
				found = true;
		}
		
		if(!found)
			return null;
		
		Map<String,String> map = null;
		
		for(Object key : properties.keySet())
		{
			if(key.toString().startsWith(name))
			{
				
				String[] keyParts = key.toString().split("\\.");
				
				// 1, 2, 3, ...
				int id = Integer.valueOf(keyParts[1]);
				
				try {
					map = maps.get(id-1);
				}
				catch(IndexOutOfBoundsException e)
				{
					if(id>=maps.size())
					{
						map=null;
						for(int i=maps.size();i<=id-1;i++)
							maps.add(i,map);
					}
				}
				if(map==null) {
					map = new TreeMap<String,String>();
					maps.set(id-1, map);
				}
				
				
				String value = properties.getProperty(key.toString());
				map.put(keyParts[2], value );
			}
		}
			
			

		
		return maps;
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
