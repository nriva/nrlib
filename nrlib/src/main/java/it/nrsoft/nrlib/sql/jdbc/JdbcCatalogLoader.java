package it.nrsoft.nrlib.sql.jdbc;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;

import org.apache.log4j.Logger;

import it.nrsoft.nrlib.sql.CatalogMetadata;
import it.nrsoft.nrlib.sql.ColumnMetadata;
import it.nrsoft.nrlib.sql.IndexColumnMetadata;
import it.nrsoft.nrlib.sql.IndexMetadata;
import it.nrsoft.nrlib.sql.SchemaMetadata;
import it.nrsoft.nrlib.sql.TableMetadata;

public class JdbcCatalogLoader {
	
	private static Logger logger = Logger.getLogger(JdbcCatalogLoader.class.getName());
	
	
	/**
	 * 
	 * @param catalog
	 * @param meta
	 * @throws Exception
	 */
	public void loadMetadata(CatalogMetadata catalog,DatabaseMetaData meta) throws Exception {
		
		// TABLE_TYPE String => table type. Typical types are "TABLE", "VIEW",
		// "SYSTEM TABLE", "GLOBAL TEMPORARY", "LOCAL TEMPORARY", "ALIAS",
		// "SYNONYM".
		
		catalog.setQuoteString(meta.getIdentifierQuoteString().trim());
		catalog.setSeparator(meta.getCatalogSeparator());
		
		catalog.getTableTypes().clear();
		ResultSet tableTypes = meta.getTableTypes();

		while (tableTypes.next())
			catalog.getTableTypes().add(tableTypes.getString(1));
		
		

		// TABLE_CAT String => catalog name
		 ResultSet catalogs = meta.getCatalogs();
		 boolean found = false;
		
		 while (catalogs.next() && !found) {
			 String s = catalogs.getString(1);
			 logger.trace("Catalog [" + s + "]");
			 found = s.equals(catalog.getName());
		 }
		 
		 if(!found && !catalog.getName().equals(""))
			 throw new Exception("Catalog [" + catalog.getName() + "] not found ");

		// TABLE_SCHEM String => schema name
		// TABLE_CATALOG String => catalog name (may be null)

		
		catalog.getSchemas().clear();
		ResultSet _schemas = meta.getSchemas();
		found = false;
		while (_schemas.next())
		{
			String schemaname = _schemas.getString(1);
			logger.trace("Schema [" + schemaname + "]");
			found |= catalog.getSchemaName().equals(schemaname);
			if (catalog.getName().equals(_schemas.getString(2)) || _schemas.getString(2)==null)
				catalog.getSchemas().put(schemaname, new SchemaMetadata(schemaname));
		}
		
		if(!found)
			throw new Exception("Schema [" + catalog.getSchemaName() + "] not found ");


		/*
		 * TABLE_CAT String => table catalog (may be null) 
		 * TABLE_SCHEM String => table schema (may be null) 
		 * TABLE_NAME String => table name 
		 * TABLE_TYPE String => table type. Typical types are "TABLE", "VIEW",
		 * "SYSTEM TABLE", "GLOBAL TEMPORARY", "LOCAL TEMPORARY", "ALIAS",
		 * "SYNONYM". 
		 * REMARKS String => explanatory comment on the table
		 * TYPE_CAT String => the types catalog (may be null)
		 * TYPE_SCHEM String => the types schema (may be null)
		 * TYPE_NAME String => type name (may be null)
		 * SELF_REFERENCING_COL_NAME String => name of the designated
		 * "identifier" column of a typed table (may be null)
		 * REF_GENERATION String => specifies how values in SELF_REFERENCING_COL_NAME are
		 * created. Values are "SYSTEM", "USER", "DERIVED". (may be null)
		 */
		
		SchemaMetadata currentSchema = catalog.getSchemas().get(catalog.getSchemaName());

		ResultSet tables = meta.getTables(catalog.getName(), catalog.getSchemaName(), null,
				new String[] { "TABLE" });

		boolean addTable = true;
		
		while (tables.next()) {
			
			String tn = tables.getString(3);
			if(!catalog.getPattern().equals(""))
			{
				
				addTable=tn.matches(catalog.getPattern());
			}
			else if(catalog.getTableNames().size()>0)
			{
				addTable=catalog.getTableNames().contains(tn);
			}
			
			if(addTable) {
				logger.trace("Table ["+tables.getString(3)+"]");
				TableMetadata value = new TableMetadata(catalog
						, currentSchema
						, tables.getString(3));
				currentSchema.getTables().put(
						tables.getString(3),
						value);
			}
		}

//		ResultSet views = meta.getTables(catalogname, schemaname, null,
//				new String[] { "VIEW" });
//
//		while (views.next()) {
//			System.out.println("view: " + views.getString(1) + "."
//					+ views.getString(2) + "." + views.getString(3));
//
//		}

		/*
		 * TABLE_CAT String => table catalog (may be null) 
		 * TABLE_SCHEM String => table schema (may be null) 
		 * TABLE_NAME String => table name
		 * COLUMN_NAME String => column name
		 * DATA_TYPE int => SQL type from java.sql.Types 
		 * TYPE_NAME String => Data source dependent type name, for a UDT the type name is fully qualified 
		 * COLUMN_SIZE int => column size. BUFFER_LENGTH is not used. 
		 * DECIMAL_DIGITS int => the number of fractional digits. Null is returned for data types where DECIMAL_DIGITS is not applicable. 
		 * NUM_PREC_RADIX int => Radix (typically either 10 or 2) 
		 * NULLABLE int => is NULL allowed.
		 * 	columnNoNulls - might not allow NULL values 
		 * 	columnNullable - definitely allows NULL values 
		 * 	columnNullableUnknown - nullability unknown 
		 * REMARKS String => comment describing column (may be null)
		 * COLUMN_DEF String => default value for the column, which should be interpreted as a string 
		 * 						when the value is enclosed in single quotes (may be null) 
		 * SQL_DATA_TYPE int => unused 
		 * SQL_DATETIME_SUB int => unused 
		 * CHAR_OCTET_LENGTH int => for char types the maximum number of bytes in the column 
		 * ORDINAL_POSITION int => index of column in table (starting at 1) 
		 * IS_NULLABLE String => ISO rules are used to determine the nullability for a column.
		 * 	YES --- if the parameter can include NULLs 
		 * 	NO --- if the parameter cannot include NULLs 
		 * 	empty string --- if the nullability for the parameter is unknown 
		 * SCOPE_CATLOG String => catalog of table that is the scope of a reference attribute (null if DATA_TYPE isn't REF) 
		 * SCOPE_SCHEMA String => schema of table that is the scope of a reference attribute (null if the DATA_TYPE isn't  REF) 
		 * SCOPE_TABLE String => table name that this the scope of a reference attribure (null if the DATA_TYPE isn't REF)
		 * SOURCE_DATA_TYPE short => source type of a distinct type or user-generated Ref type, SQL type from java.sql.Types (null if DATA_TYPE isn't DISTINCT or user-generated REF) 
		 * IS_AUTOINCREMENT String => Indicates whether this column is auto incremented 
		 * 	YES --- if the column is auto incremented
		 * 	NO --- if the column is not auto incremented 
		 * 	empty string --- if it cannot be determined whether the column is auto incremented parameter is unknown
		 */
		
		
		
		
		for(String tablename : currentSchema.getTables().keySet() ) {

			ResultSet columns = meta.getColumns(catalog.getName(), catalog.getSchemaName(), tablename, null);
			
			TableMetadata currentTable = currentSchema.getTables().get(tablename);
	
			while (columns.next()) {
				
				currentTable.getColumns().put(columns.getString("COLUMN_NAME"),
						new ColumnMetadata(currentTable
								, columns.getString("COLUMN_NAME")
								, columns.getString("TYPE_NAME")
								, columns.getInt("DATA_TYPE")
								, columns.getInt("COLUMN_SIZE")
								, columns.getInt("DECIMAL_DIGITS")
								, columns.getString("IS_NULLABLE").equals("YES")
								, columns.getString("COLUMN_DEF")));
	
	//			System.out.println("column: " + columns.getString("TABLE_CAT")
	//					+ "." + columns.getString("TABLE_SCHEM") + "."
	//					+ columns.getString("TABLE_NAME") + "."
	//					+ columns.getString("COLUMN_NAME") + " ["
	//					+ columns.getString("DATA_TYPE") + " size "
	//					+ columns.getString("COLUMN_SIZE") + "  IS_NULLABLE?="
	//					+ columns.getString("IS_NULLABLE") + "]");
	
			}
			
			ResultSet primaryKeys = meta.getPrimaryKeys(catalog.getName(), catalog.getSchemaName(), tablename);	
			
			while (primaryKeys.next()) {
				currentTable.getPrimaryKey().add(currentTable.getColumns().get(
						primaryKeys.getString(4)));
			}
			
//			TABLE_CAT String => table catalog (may be null) 
//			TABLE_SCHEM String => table schema (may be null) 
//			TABLE_NAME String => table name 
//			NON_UNIQUE boolean => Can index values be non-unique. false when TYPE is tableIndexStatistic 
//			INDEX_QUALIFIER String => index catalog (may be null); null when TYPE is tableIndexStatistic 
//			INDEX_NAME String => index name; null when TYPE is tableIndexStatistic 
//			TYPE short => index type: 
//			tableIndexStatistic - this identifies table statistics that are returned in conjuction with a table's index descriptions 
//			tableIndexClustered - this is a clustered index 
//			tableIndexHashed - this is a hashed index 
//			tableIndexOther - this is some other style of index 
//			ORDINAL_POSITION short => column sequence number within index; zero when TYPE is tableIndexStatistic 
//			COLUMN_NAME String => column name; null when TYPE is tableIndexStatistic 
//			ASC_OR_DESC String => column sort sequence, "A" => ascending, "D" => descending, may be null if sort sequence is not supported; null when TYPE is tableIndexStatistic 
//			CARDINALITY int => When TYPE is tableIndexStatistic, then this is the number of rows in the table; otherwise, it is the number of unique values in the index. 
//			PAGES int => When TYPE is tableIndexStatisic then this is the number of pages used for the table, otherwise it is the number of pages used for the current index. 
//			FILTER_CONDITION String => Filter condition, if any. (may be null) 
			
			
			ResultSet indexes = meta.getIndexInfo(catalog.getName(), currentSchema.getName(), tablename, false, false);
			String currentIndexName = "";
			IndexMetadata currentIndex = null;
			
			String indexFields = "";
			while(indexes.next())
			{
				
				if(indexes.getString("INDEX_NAME")!=null && (indexes.getInt("TYPE")==1 || indexes.getInt("TYPE")==3))
					
				{
					
					if(currentIndex == null)
					{
						currentIndex = new IndexMetadata(currentTable
								, indexes.getString("INDEX_NAME")
								, !indexes.getBoolean("NON_UNIQUE")
								, indexes.getInt("TYPE")==1);	
						currentIndexName = indexes.getString("INDEX_NAME");
					}
					
				
					if(!currentIndexName.equals(indexes.getString("INDEX_NAME")))
					{
						
						logger.trace("Index [" + currentTable.getName() + "." + currentIndexName + "(" + indexFields+  ")" 
						+ (currentIndex.isUnique()?",Unique":"") 
						+ (currentIndex.isClustered()?",Clustered":"")
						+ "]");
						if(currentIndex.getColumns().size()>0)
							currentTable.getIndexes().put(currentIndexName, currentIndex);
						else
							logger.trace("Skipping index with no columns");
						indexFields="";
						
						currentIndexName = indexes.getString("INDEX_NAME");
						currentIndex = new IndexMetadata(currentTable,currentIndexName
								, !indexes.getBoolean("NON_UNIQUE")
								, indexes.getInt("TYPE")==1);
						
						
						
					}
					
					currentIndex.getColumns().add( new IndexColumnMetadata(currentIndex,
							currentTable.getColumns().get(indexes.getString("COLUMN_NAME"))  
							, indexes.getString("ASC_OR_DESC").equals("A")) );
					
					if(!"".equals(indexFields))
						indexFields +=",";
					
					indexFields += indexes.getString("COLUMN_NAME");
	
				}
				
			}
			
			if(currentIndex!=null && currentIndex.getColumns().size()>0)
				currentTable.getIndexes().put(currentIndexName, currentIndex);
			
		}
		
		
		

	}
	

}
