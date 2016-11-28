package it.nrsoft.nrlib.mapping.connectors;

import java.text.*;
import java.util.Map;

public interface InputConnector {

	boolean next();
	
	Map<String,Object> getMap(Map<String, Map<String, String>> properties) throws Exception;
	
	


}
