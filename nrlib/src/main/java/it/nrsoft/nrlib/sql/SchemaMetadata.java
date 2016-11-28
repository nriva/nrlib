package it.nrsoft.nrlib.sql;

import java.util.LinkedHashMap;
import java.util.Map;

public class SchemaMetadata {
	
	private String name="";
	
	
	private Map<String,TableMetadata> tables = new LinkedHashMap<String,TableMetadata>();

	/**
	 * @return the tables
	 */
	public Map<String, TableMetadata> getTables() {
		return tables;
	}

	/**
	 * @param name
	 */
	public SchemaMetadata(String name) {
		this.name = name;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	
	
	

}
