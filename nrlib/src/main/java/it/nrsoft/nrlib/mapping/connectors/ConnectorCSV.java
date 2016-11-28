package it.nrsoft.nrlib.mapping.connectors;

import java.util.Map;

import org.apache.log4j.Logger;

public abstract class ConnectorCSV extends Connector {
	
	static Logger logger = Logger.getLogger(ConnectorCSV.class.getName());	
	
	protected String line = null;
	
	protected boolean firstrowheaders = false;
	protected String[] headers = null;
	protected String filename="";
	protected String separator=",";
	
	
	protected String[] splitLine() {
		String[] values;
		values = line.split(separator);
		return values;
	}
	


	@Override
	public boolean init(Map<String, String> properties) {
		super.init(properties);
		filename=properties.get(PROPNAME_FILENAME).trim();
		if(properties.containsKey(PROPNAME_SEPARATOR))
			separator = properties.get(PROPNAME_SEPARATOR);
		firstrowheaders = "true".equals(properties.get(PROPNAME_FIRSTROWHEADERS));
		return true;
	}
}
