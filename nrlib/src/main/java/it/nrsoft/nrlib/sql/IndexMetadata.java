package it.nrsoft.nrlib.sql;

import java.util.*;

public class IndexMetadata implements DDLStatementBuilder {
	
	private String name="";
	
	private List<IndexColumnMetadata> columns = new ArrayList<IndexColumnMetadata>();

	private boolean unique;

	private boolean clustered;
	
	private TableMetadata table=null;

	/**
	 * @return the unique
	 */
	public boolean isUnique() {
		return unique;
	}

	/**
	 * @param name
	 */
	public IndexMetadata(TableMetadata table,String name,boolean unique,boolean clustered) {
		this.name = name;
		this.unique = unique;
		this.clustered = clustered;
		this.table = table;
	}

	/**
	 * @return the clustered
	 */
	public boolean isClustered() {
		return clustered;
	}

	/**
	 * @return the columns
	 */
	public List<IndexColumnMetadata> getColumns() {
		return columns;
	}

	public String buildCreateStmt() {
		String s = "CREATE " + (isUnique()? "UNIQUE ":"") + (clustered?"CLUSTERED ": "") +"INDEX " 
					+ table.getCatalogInfo().quote(name) + "(";
		boolean first = true;
		for(IndexColumnMetadata column : columns)
		{
			if(!first)
				s+=",";
			first=false;
			s+=column.buildCreateStmt();
		}
		s+=")";
		return s;
	}
	
	

}
