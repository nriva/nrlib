package it.nrsoft.nrlib.sql;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class TableMetadata implements DDLStatementBuilder {
	
	
	private String name;
	private CatalogMetadata catalogInfo;
	private SchemaMetadata schemaInfo;
	
	private Map<String,ColumnMetadata> columns = new LinkedHashMap<String,ColumnMetadata>();
	
	private Set<ColumnMetadata> primaryKey = new LinkedHashSet<ColumnMetadata>();
	
	private Map<String,IndexMetadata> indexes = new LinkedHashMap<String, IndexMetadata>();

	/**
	 * @return the indexes
	 */
	public Map<String, IndexMetadata> getIndexes() {
		return indexes;
	}

	/**
	 * @return the columns
	 */
	public Map<String, ColumnMetadata> getColumns() {
		return columns;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the catalogInfo
	 */
	public CatalogMetadata getCatalogInfo() {
		return catalogInfo;
	}

	/**
	 * @return the schemaname
	 */
	public SchemaMetadata getSchemaInfo() {
		return schemaInfo;
	}


	/**
	 * 
	 * @param catalogInfo
	 * @param schemaInfo
	 * @param name
	 */
	public TableMetadata(CatalogMetadata catalogInfo,
			SchemaMetadata schemaInfo,String name) {
		this.name = name;
		this.catalogInfo = catalogInfo;
		this.schemaInfo = schemaInfo;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
	
		return catalogInfo.getName() + "." + schemaInfo.getName() + "." + name;
	}
	


	public Set<ColumnMetadata> getPrimaryKey() {
		return primaryKey;
	}
	
	protected String fmtTableName = "%1$s%4$s%2$s%4$s%3$s";
	
	
	public String getFullyQualifiedName()
	{
		
		return String.format(fmtTableName,
				getCatalogInfo().getName() 
				, getSchemaInfo().getName() 
				, getCatalogInfo().quote(name)
				, getCatalogInfo().getSeparator());
	}

	public String buildCreateStmt() {
		String s = "CREATE TABLE ";
		if(catalogInfo.getName().length()>0) {
			s += catalogInfo.quote(catalogInfo.getName()); 
			s += "." + catalogInfo.quote(schemaInfo.getName()); 
			s +=  ".";
		}
		
		s += catalogInfo.quote(name); 
		s += " (";
		boolean first=true;
		for(ColumnMetadata column : columns.values())
		{
			if(!first)
				s+= ",";
			first = false;
			s += column.buildCreateStmt();
		}
		
		if(primaryKey.size()>0)
		{
			s+= ", PRIMARY KEY (";
			first = true;
			for(ColumnMetadata column : primaryKey)
			{
				if(!first)
					s+=",";
				first = false;
				s+= catalogInfo.quote( column.getName());
			}
			s+=")";
				
		}
		s += ")";
		
		
		
		return s;
	}

}
