package it.nrsoft.nrlib.util;


import java.io.*;
import java.util.*;
import java.util.Map.Entry;

	


/**
* @author nriva
*
*/
public class Properties {


	
	public static String ErrorMessage="";
	public static String PropFile="";
	/**
	 * 
	 * @param ServerURL
	 *            String IPaddress
	 * @param EnvironmentPort
	 *            String PortNumber
	 * @return properties file for dbConnection
	 * @throws IOException 
	 */
	public static java.util.Properties getProp(String inEnvironment) {
		
		java.util.Properties prop = new java.util.Properties();
		PropFile = inEnvironment.trim().replace('.', '_').replace(':', '_')
				+ ".properties";
		
		
//		try {
//
			InputStream propConnStream = Properties.class.getResourceAsStream("/" + PropFile);
		//InputStream propConnStream = Properties.class.getResourceAsStream("/connection.properties");
			if (propConnStream != null) {
				try {
					prop.load(propConnStream);
				} catch (IOException e) {
					ErrorMessage = "Properties.getProp: " + e.getMessage();
					e.printStackTrace();
				}
			} else {
				ErrorMessage = "Properties.getProp: PropConnStream == null";

			}
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		return prop;
	}
	
	public static boolean getBooleanProp(java.util.Properties properties,String name)
	{
		boolean value=false;
		if(properties.containsKey(name) )
			value = properties.getProperty(name).equalsIgnoreCase("true");
		return value;
	}
	
	public static String getStringProp(java.util.Properties properties,String name)
	{
		String value="";
		if(properties.containsKey(name) )
			value = properties.getProperty(name);
		return value;
	}
	
	public static String getStringProp(java.util.Properties properties,String name,String defaultValue)
	{
		String value=defaultValue;
		if(properties.containsKey(name) )
			value = properties.getProperty(name);
		return value;
	}
	
	public static String[] getStringArrayProp(java.util.Properties properties,String name, String separator)
	{
		String[] value=new String[]{};
		if(properties.containsKey(name) )
			value = properties.getProperty(name).split(separator);
		return value;
	}
	
	
	public static Map<String,String> prefixSubset(String prefix, java.util.Properties properties)
	{
		
		Map<String,String> map = new TreeMap<String,String>();
		
		for(Entry<Object, Object> entry : properties.entrySet())
			if( entry.getKey().toString().startsWith(prefix) )
				map.put(entry.getKey().toString().substring(prefix.length())
						, entry.getValue().toString());
		
		return map;
		
	}	
	
	
	
	

}
