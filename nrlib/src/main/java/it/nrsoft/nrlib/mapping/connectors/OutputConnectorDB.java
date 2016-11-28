package it.nrsoft.nrlib.mapping.connectors;

import java.sql.*;
import java.util.Map;

import it.nrsoft.nrlib.sql.ColumnMetadata;


public class OutputConnectorDB extends ConnectorDB implements OutputConnector {


	private static final String PROPNAME_CLEANTABLE = "cleantable";
	private PreparedStatement prepStmt;
	private boolean cleanTable = false;
	
	private boolean tableCleaned = false;
	private PreparedStatement prepStmtClean;
	
	private boolean errorFound = false;

	public void putMap(Map<String, Map<String, String>> properties, Map<String, Object> map) throws Exception {
		
		
		if(cleanTable && !tableCleaned)
		{
			prepStmtClean.executeUpdate();
			tableCleaned = true;
		}

		int parameterIndex=1;
		for(ColumnMetadata column : tableMetadata.getColumns().values() )
		{
			Object obj = map.get(column.getName());
			
			if(obj instanceof java.util.Date)
			{
				
				if(column.getTypeId()==java.sql.Types.TIMESTAMP)
					obj = new java.sql.Timestamp( ((java.util.Date)obj).getTime() );
				else
					obj = new java.sql.Date( ((java.util.Date)obj).getTime() );
			}
			else if(obj instanceof java.util.Calendar)
			{
				if(column.getTypeId()==java.sql.Types.TIMESTAMP)
					obj = new java.sql.Timestamp( ((java.util.Calendar)obj).getTimeInMillis() );
				else
					obj = new java.sql.Date( ((java.util.Calendar)obj).getTimeInMillis() );
				
			}
			
			prepStmt.setObject(parameterIndex, obj);
			parameterIndex++;
		}
		try {
			prepStmt.executeUpdate();
		}
		catch(SQLException e)
		{
			logger.error("Error executing insert",e);
			errorFound = true;
		}
	}

	/* (non-Javadoc)
	 * @see cadit.mapping.connectors.ConnectorDB#init(java.util.Map)
	 */
	@Override
	public boolean init(Map<String, String> properties) {
		super.init(properties);
		cleanTable = "true".equals(properties.get(PROPNAME_CLEANTABLE));
		return true;
	}

	@Override
	public boolean open() {
		boolean ok = super.open();
		if(ok)
		{
			String insertSql = builder.buildInsert(tableMetadata);
			prepStmt = connection.prepareStatement(insertSql);
			
			String deleteSql = builder.buildDeleteAll(tableMetadata);
			prepStmtClean = connection.prepareStatement(deleteSql);
			
			errorFound = false;
		}
		return ok;
	}

	@Override
	public boolean close() {
		boolean ok = true;
		try {
			
			if(errorFound)
			{
				logger.info("Rolling back...");
				connection.getConnection().rollback();
			}
			else
			{
				logger.info("Committing...");
				connection.getConnection().commit();
			}
			
			prepStmt.close();
		} catch (SQLException e) {

			logger.error("Error closing",e);
			ok = false;
		}
		if(!super.close())
			ok = false;
		return ok;
	}



}
