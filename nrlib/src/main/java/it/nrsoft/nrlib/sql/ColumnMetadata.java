package it.nrsoft.nrlib.sql;

import it.nrsoft.nrlib.sql.jdbc.JdbcMetadata;

public class ColumnMetadata implements DDLStatementBuilder {
	
	
	/**
	 * @return the tableInfo
	 */
	public TableMetadata getTableInfo() {
		return tableInfo;
	}

	private TableMetadata tableInfo=null;
	
	private String name;
	
	private String typeName;
	
	private int typeId;
	
	private int size=0;
	
	private int precision=0;

	private boolean nullable;
	
	private Object defaultValue = null;

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the typeName
	 */
	public String getTypeName() {
		return typeName;
	}

	/**
	 * @param name
	 * @param typeName
	 */
	public ColumnMetadata(TableMetadata tableInfo,String name, String typeName, int typeId) {
		this.tableInfo = tableInfo; 
		this.name = name;
		this.typeName = typeName;
		this.typeId = typeId; 
	}
	
	
	
	

	/**
	 * @return the typeId
	 */
	public int getTypeId() {
		return typeId;
	}

	/**
	 * @return the size
	 */
	public int getSize() {
		return size;
	}

	/**
	 * @return the precision
	 */
	public int getPrecision() {
		return precision;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String s = tableInfo.getCatalogInfo().quote(name) + " " + typeName;
		if(size>0 && JdbcMetadata.hasTypeSize(typeId))
		{
			s+="(" + String.valueOf(size);
			if(precision>0 && JdbcMetadata.hasTypePrecision(typeId))
				s+="," + String.valueOf(precision);
			s+=")"; 
		}
		if(nullable)
			s+=" NULL";
		else
			s+=" NOT NULL";
		if(defaultValue!=null)
			s+=" DEFAULT " + defaultValue.toString();
		return s;
	}



	/**
	 * @param tableInfo
	 * @param name
	 * @param typeName
	 * @param typeId
	 * @param size
	 * @param precision
	 */
	public ColumnMetadata(TableMetadata tableInfo, String name,
			String typeName, int typeId, int size, int precision,boolean nullable,String defaultValue) {
		this.tableInfo = tableInfo;
		this.name = name;
		this.typeName = typeName;
		this.typeId = typeId;
		this.size = size;
		this.precision = precision;
		this.nullable = nullable;
		
		if(defaultValue!=null)
		{
//			if(defaultValue.startsWith("'") && defaultValue.endsWith("'"))
//			{
//				this.defaultValue = defaultValue.substring(1, defaultValue.length()-2);
//			}
//			else
				this.defaultValue = defaultValue; 
		}
	}

	public String buildCreateStmt() {
		return toString();
	}
	
	
	
	
	

}
