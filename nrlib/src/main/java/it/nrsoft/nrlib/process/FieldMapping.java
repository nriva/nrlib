package it.nrsoft.nrlib.process;

import java.util.HashMap;
import java.util.Map;

/**
 * Syntax:
 * 	SRC1:DEST1, SRC2: DEST2
 * @author riva
 *
 */
public class FieldMapping {
	
	
	public FieldMapping(String mapAsString) {
		
		String[] maps = mapAsString.split(",");
		
		this.map = new HashMap<>(maps.length, 1);
		
		for(String map:maps) {
			String[] parts = map.split(":");
			this.map.put( parts[0].trim(), parts[1].trim()  );
		}
		
		
	}
	
	
	private Map<String,String> map = null;
	
	public String mapField(String fieldName) {
		return map.get(fieldName);
	}

	public boolean hasMapForField(String fieldName) {

		return map.containsKey(fieldName);
	}
	
	
	
	
	

}
