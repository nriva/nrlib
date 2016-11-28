package it.nrsoft.nrlib.sql;

public class IndexColumnMetadata implements DDLStatementBuilder {
	
	private ColumnMetadata column;
	private boolean ascending;
	private IndexMetadata index;
	/**
	 * @return the column
	 */
	public ColumnMetadata getTableColumn() {
		return column;
	}
	/**
	 * @return the ascending
	 */
	public boolean isAscending() {
		return ascending;
	}
	/**
	 * @param column
	 * @param ascending
	 */
	public IndexColumnMetadata(IndexMetadata index ,ColumnMetadata column, boolean ascending) {
		this.column = column;
		this.ascending = ascending;
		this.index = index;
	}
	
	public String buildCreateStmt() {
		return column.getTableInfo().getCatalogInfo().quote(column.getName()) + " " + (isAscending()?"ASC":"DESC");
	}
	
	
	

}
