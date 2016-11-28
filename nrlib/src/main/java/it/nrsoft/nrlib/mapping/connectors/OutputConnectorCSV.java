package it.nrsoft.nrlib.mapping.connectors;

import java.io.*;
import java.math.*;
import java.text.*;
import java.util.Map;

import org.apache.log4j.Logger;

import it.nrsoft.nrlib.util.StringUtil;

public class OutputConnectorCSV extends ConnectorCSV implements OutputConnector {
	
	static Logger logger = Logger.getLogger(OutputConnectorCSV.class.getName());
	
	BufferedWriter writer;
	
	boolean headersLineWritten = false;

	public void putMap(Map<String, Map<String, String>> properties, Map<String, Object> map) throws Exception {
		StringBuilder builder = new StringBuilder();
		
		

		if(headers==null)
		{
			headers = new String[properties.keySet().size()];
			int i=0;
			for(Map.Entry<String, Map<String,String>> entry : properties.entrySet())
			{
				headers[i] = entry.getValue().get(DEF_PROPNAME_LABEL);
				if(headers[i]==null)
					headers[i] = entry.getKey();
				i++;
			}
			
		}
		
		if(firstrowheaders && !headersLineWritten) {
			
			String outline=StringUtil.join(headers, separator);
			logger.trace("Writing line: " + outline);
			writer.write(outline+"\r\n");
			headersLineWritten = true;
		}	
		
		for(String header : properties.keySet())
		{
			if(builder.length()>0)
				builder.append(separator);
			
			Object obj = map.get(header);
			Map<String,String> props = properties.get(header);
			
			if(!props.containsKey(DEF_PROPNAME_TYPE))
			{
				if(obj instanceof java.util.Date)
					props.put(DEF_PROPNAME_TYPE, TYPENAME_DATE);
			}
			
			FormatInfo info = getFormatInfo(props);
			
//			if(formatInfo.type==null|| "".equals(formatInfo.type) ||formatInfo.type.equals("java.lang.String"))
//			{
				if(obj instanceof String)
					builder.append( obj.toString() );
				else if(obj instanceof Double
						|| obj instanceof Float
						|| obj instanceof Long
						|| obj instanceof Integer
						|| obj instanceof BigInteger
						|| obj instanceof BigDecimal)
				{
					builder.append( info.formatNumber(obj) );
				}
				else if(obj instanceof java.util.Date)
				{
					builder.append( info.formatDate(obj) );
				}
				else if(obj==null)
					throw new Exception("Null value");
				else
					throw new Exception("Output not supported for data Type " + obj.getClass().getCanonicalName()); 
//			}
		}
		try {
			String outline=builder.toString();
			logger.trace("Writing line: " + outline);
			writer.write(outline+"\r\n");
		} catch (IOException e) {
			logger.error("Error writing",e);
		}

	}

	@Override
	public boolean open() {
		logger.trace("Opening...");
		boolean ok = true;
		try {
			headersLineWritten = false;
			writer = new BufferedWriter(new FileWriter(filename));
		} catch (FileNotFoundException e) {
			logger.error("Error opening",e);
			ok = false;
		} catch (IOException e) {
			logger.error("Error opening",e);
			ok = false;
		}
		
		return ok;	
	}

	@Override
	public boolean close() {
		logger.trace("Closing...");
		boolean ok = true;
		try {
			writer.close();
		} catch (IOException e) {
			logger.error("Error closing",e);
			ok = false;
		}
		return ok;
	}

	@Override
	public boolean init(Map<String, String> properties) {
		super.init(properties);
		if(properties.containsKey("headers"))
			headers=properties.get("headers").trim().split(",");
		return true;
	}

}
