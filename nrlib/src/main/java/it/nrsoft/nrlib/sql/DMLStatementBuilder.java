package it.nrsoft.nrlib.sql;

public interface DMLStatementBuilder {
	
	String buildSelect(TableMetadata table);
	String buildSelectAll(TableMetadata table);
	String buildInsert(TableMetadata table);
	String buildUpdate(TableMetadata table);
	String buildDelete(TableMetadata table);
}
