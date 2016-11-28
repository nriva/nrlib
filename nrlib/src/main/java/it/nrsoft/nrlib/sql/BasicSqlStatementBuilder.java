package it.nrsoft.nrlib.sql;

public class BasicSqlStatementBuilder implements DMLStatementBuilder {
	
	
	private boolean qualifiedName = false;
	
	private boolean updatePrimaryKey = false;
	
	private boolean selectFieldExplicit = false;
	
	private boolean quoteIdent = false;
	
	private String getTableName(TableMetadata table)
	{
		String name="";
		if(qualifiedName)
			name = table.getCatalogInfo().getName() + "." + table.getSchemaInfo().getName() + ".";
		if(quoteIdent)
			name += table.getCatalogInfo().quote(table.getName());
		else
			name += table.getName();
		return name;
	}
	
	

	protected String getPrimaryKeyConditions(TableMetadata table)
	{
		
		String s ="";
		for(ColumnMetadata cmd : table.getPrimaryKey())
		{
			if(s!="")
				s+=" AND ";
			s+= getColumnName(table,cmd.getName()) + "=?";
		}
		return s;
	}
	
	private String getColumnName(TableMetadata table,String name) {
		if(quoteIdent)
			return table.getCatalogInfo().quote(name);
		return name;
	}



	protected String getPrimaryKeyOrderBy(TableMetadata table)
	{
		
		String s ="";
		for(ColumnMetadata cmd : table.getPrimaryKey())
		{
			if(s!="")
				s+=" , ";
			s+= getColumnName(table,cmd.getName());
		}
		return "ORDER BY " + s;
	}
	

	public String buildInsert(TableMetadata table) {
		StringBuilder stmt = new StringBuilder("INSERT INTO " + getTableName(table));
		StringBuilder values = new StringBuilder();
		stmt.append("(");
		int i=0;
		for(String colname : table.getColumns().keySet())
		{
			if(i!=0)
			{
				stmt.append(",");
				values.append(",");
			}
			stmt.append(getColumnName(table,colname));
			values.append("?");
			i++;
		}
		stmt.append(") VALUES (");
		stmt.append(values);
		stmt.append(")");
		return stmt.toString();
	}
	

	public String buildUpdate(TableMetadata table) {
		StringBuilder stmt = new StringBuilder("UPDATE " + getTableName(table) + " SET "); 
		
		int i=0;
		for(String colname : table.getColumns().keySet())
		{
			
			ColumnMetadata col = table.getColumns().get(colname);
			boolean doAppend = true;
			if(table.getPrimaryKey()!=null)
			{
				if(!updatePrimaryKey && table.getPrimaryKey().contains(col))
					doAppend = false;
					
			}
			
			if(doAppend) {
				if(i!=0)
				{
					stmt.append(",");
	
				}
				stmt.append(getColumnName(table,colname) + "=?" );
				i++;
			}
		}
		
		stmt.append(" WHERE " + getPrimaryKeyConditions(table));
		return stmt.toString();
	}

	public String buildDelete(TableMetadata table) {
		StringBuilder stmt = new StringBuilder("DELETE FROM " + getTableName(table));
		stmt.append(" WHERE " + getPrimaryKeyConditions(table));
		return stmt.toString();
	}
	
	public String buildDeleteAll(TableMetadata table) {
		StringBuilder stmt = new StringBuilder("DELETE FROM " + getTableName(table));
		return stmt.toString();
	}	

	public String buildSelect(TableMetadata table) {

		StringBuilder stmt = new StringBuilder(buildSelectAll(table));
		stmt.append(" WHERE " + getPrimaryKeyConditions(table));
		stmt.append(" " + getPrimaryKeyOrderBy(table));
		return stmt.toString();

	}

	/**
	 * @param qualifiedName
	 */
	public BasicSqlStatementBuilder(boolean qualifiedName
			,boolean updatePrimaryKey
			,boolean selectFieldExplicit
			,boolean quoteIdent) {
		this.qualifiedName = qualifiedName;
		this.updatePrimaryKey = updatePrimaryKey;
		this.selectFieldExplicit = selectFieldExplicit;
		this.quoteIdent = quoteIdent;
	}
	
	public BasicSqlStatementBuilder() {}



	/**
	 * @return the qualifiedName
	 */
	public boolean isQualifiedName() {
		return qualifiedName;
	}



	/**
	 * @param qualifiedName the qualifiedName to set
	 */
	public void setQualifiedName(boolean qualifiedName) {
		this.qualifiedName = qualifiedName;
	}



	/**
	 * @return the updatePrimaryKey
	 */
	public boolean isUpdatePrimaryKey() {
		return updatePrimaryKey;
	}



	/**
	 * @param updatePrimaryKey the updatePrimaryKey to set
	 */
	public void setUpdatePrimaryKey(boolean updatePrimaryKey) {
		this.updatePrimaryKey = updatePrimaryKey;
	}



	/**
	 * @return the selectFieldExplicit
	 */
	public boolean isSelectFieldExplicit() {
		return selectFieldExplicit;
	}



	/**
	 * @param selectFieldExplicit the selectFieldExplicit to set
	 */
	public void setSelectFieldExplicit(boolean selectFieldExplicit) {
		this.selectFieldExplicit = selectFieldExplicit;
	}



	public String buildSelectAll(TableMetadata table) {
		StringBuilder stmt = new StringBuilder("SELECT ");
		if(!selectFieldExplicit)
			stmt.append("*");
		else
		{
			int i=0;
			for(String colname : table.getColumns().keySet())
			{
				if(i!=0)
				{
					stmt.append(",");
				}
				stmt.append(getColumnName(table,colname));
				i++;
			}
			
		}
		stmt.append(" FROM " + getTableName(table));
		return stmt.toString();
	}

}
