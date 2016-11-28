package it.nrsoft.nrlib.mapping.connectors;

import java.io.*;
import java.util.*;

import org.apache.log4j.Logger;



public class InputConnectorCSV extends ConnectorCSV implements InputConnector {
	
	static Logger logger = Logger.getLogger(InputConnectorCSV.class.getName());	
	
	
	
	private BufferedReader reader;
	
	
	

	/* (non-Javadoc)
	 * @see cadit.mapping.connectors.ConnectorCSV#init(java.util.Map)
	 */
	@Override
	public boolean init(Map<String, String> properties) {
		boolean ok = super.init(properties);
		if(ok)
		{
			if(!firstrowheaders)
				headers = properties.get(PROPNAME_HEADERS).split(",");
		}
		return ok;
	}

	@Override
	public boolean open() {
		logger.trace("Opening...");
		boolean ok = true;
		try {
			reader = new BufferedReader(new FileReader(filename));
		} catch (FileNotFoundException e) {
			logger.error("Error opening file", e);
			ok = false;
		}

		if(ok && firstrowheaders)
		{
			ok = next();
			if(ok)
			{
				headers = line.split(",");
			}
		}
		
		return ok;
	}

	public boolean next() {
		logger.trace("Reading next line...");
		try {
			line = reader.readLine();
			logger.trace("Line read:" + line);
		} catch (IOException e) {
			logger.error("Error reading next line", e);
			line=null;
		}
		return line!=null;
	}

	@Override
	public boolean close() {
		logger.trace("Closing...");
		boolean ok = true;
		try {
			reader.close();
		} catch (IOException e) {
			logger.error("Error closing file", e);
			ok = false;
		}
		return ok;
	}



	public Map<String, Object> getMap(Map<String, Map<String, String>> properties) throws Exception {
		
		Map<String,Object> input = new TreeMap<String,Object>();
		
		String[] values = null;

		values = splitLine();
		
		
		int c=0;
		for(String header : headers)
		{
			
			Map<String,String> props = properties.get( header );

			FormatInfo formatInfo = getFormatInfo(props);			
			Object obj=values[c].trim();
			if(formatInfo.getType()==null 
					|| "".equals(formatInfo.getType()) || formatInfo.getType().equals(TYPENAME_STRING))
			{
				obj = obj.toString();
			}
			else if(formatInfo.getType().equals(TYPENAME_DATE))
			{

				obj = formatInfo.parseDate((String) obj);							
			}
			else if(formatInfo.getType().equals(TYPENAME_DOUBLE)
			|| formatInfo.getType().equals(TYPENAME_FLOAT)
			|| formatInfo.getType().equals(TYPENAME_LONG)
			|| formatInfo.getType().equals(TYPENAME_INTEGER)
			|| formatInfo.getType().equals(TYPENAME_BIGDECIMAL)
			|| formatInfo.getType().equals(TYPENAME_BIGINTEGER))
			{
				obj = formatInfo.parseNumber(values[c]);
			}
			
			
			input.put(header, obj);
			c++;
		}
		
		dump(input);
		
		return input;
	}






}
