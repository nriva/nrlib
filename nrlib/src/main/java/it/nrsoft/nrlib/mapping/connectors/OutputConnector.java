package it.nrsoft.nrlib.mapping.connectors;

import java.util.Map;

public interface OutputConnector {
	
	void putMap(Map<String, Map<String, String>> properties, Map<String, Object> map) throws Exception ;


}
