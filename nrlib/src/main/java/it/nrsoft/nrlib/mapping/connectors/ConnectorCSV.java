package it.nrsoft.nrlib.mapping.connectors;

import java.util.Map;



public abstract class ConnectorCSV extends Connector {
	
	
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
