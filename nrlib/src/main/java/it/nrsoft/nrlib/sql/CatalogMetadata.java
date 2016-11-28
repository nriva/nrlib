package it.nrsoft.nrlib.sql;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CatalogMetadata {
	
	private String pattern="";
	
	private List<String> tableNames = new ArrayList<String>();
	
	


	/**
	 * @return the tableNames
	 */
	public List<String> getTableNames() {
		return tableNames;
	}

	/**
	 * @return the pattern
	 */
	public String getPattern() {
		return pattern;
	}

	/**
	 * @return the schemas
	 */
	public Map<String, SchemaMetadata> getSchemas() {
		return schemas;
	}


	private List<String> tableTypes = new ArrayList<String>();
	
	/**
	 * @return the tableTypes
	 */
	public List<String> getTableTypes() {
		return tableTypes;
	}


	private Map<String,SchemaMetadata> schemas = new LinkedHashMap<String,SchemaMetadata>();

	// DatabaseMetaData meta = conn.getMetaData();
	
	private String name="";
	
	

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	
	private String schemaName="";

	/**
	 * @return the schemaName
	 */
	public String getSchemaName() {
		return schemaName;
	}
	
	private String quoteString="";

	/**
	 * @return the quoteString
	 */
	public String getQuoteString() {
		return quoteString;
	}
	
	public String quote(String name)
	{
		return quoteString + name + quoteString;
	}
	/**
	 * @param quoteString the quoteString to set
	 */
	public void setQuoteString(String quoteString) {
		this.quoteString = quoteString;
	}
	
	private String separator="";	

	/**
	 * @return the separator
	 */
	public String getSeparator() {
		return separator;
	}

	/**
	 * @param separator the separator to set
	 */
	public void setSeparator(String separator) {
		this.separator = separator;
	}

	/**
	 * @param name
	 * @param schemaName
	 */
	public CatalogMetadata(String name, String schemaName) {
		this.name = name;
		this.schemaName = schemaName;
	}

	/**
	 * @param pattern
	 * @param name
	 * @param schemaName
	 */
	public CatalogMetadata(String name, String schemaName,String pattern) {
		this.pattern = pattern;
		this.name = name;
		this.schemaName = schemaName;
	}

}
