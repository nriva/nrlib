package it.nrsoft.nrlib.process.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.nrsoft.nrlib.sql.CatalogMetadata;
import it.nrsoft.nrlib.sql.TableMetadata;
import it.nrsoft.nrlib.sql.jdbc.JdbcCatalogLoader;
import it.nrsoft.nrlib.process.DataRow;
import it.nrsoft.nrlib.process.InitialProperties;
import it.nrsoft.nrlib.process.ProcessData;
import it.nrsoft.nrlib.process.SimpleDataRow;
import it.nrsoft.nrlib.process.StepResult;

public class SqlStepDML extends SqlStep {
	
	public enum DMLOperation {
		INSERT,
		UPDATE,
		DELETE
	}
	
	private static Logger logger = LoggerFactory.getLogger(SqlStepDML.class);
	
	public static final String PROP_SQL_DML_TABLE = "sql.dml.tablename";
	public static final String PROP_SQL_DML_OPERATION= "sql.dml.operation";
	
	public static final String PROP_SQL_DML_KEYS= "sql.dml.keys";
	public static final String PROP_SQL_DML_VALUES= "sql.dml.values";
	
	public static final String PROP_SQL_DML_JDBC_CATALOGNAME = "sql.dml.jdbc.catalogname";
	public static final String PROP_SQL_DML_JDBC_SCHEMANAME = "sql.dml.jdbc.schemaname";
	
	protected String tableName = "";
	protected DMLOperation dmlOperation;
	protected String catalogName = "";
	protected String schemaName = "";
	
	protected String[] keys = null;
	protected String[] values = null;

	private CatalogMetadata metadataInfo;


	public SqlStepDML(String name, InitialProperties properties) {
		super(name, properties);
		
		tableName = properties.getProperty(PROP_SQL_DML_TABLE);
		dmlOperation = DMLOperation.valueOf(properties.getProperty(PROP_SQL_DML_OPERATION));
		catalogName = properties.getProperty(PROP_SQL_DML_JDBC_CATALOGNAME,"");
		schemaName = properties.getProperty(PROP_SQL_DML_JDBC_SCHEMANAME,"");
		
		String keys = properties.getProperty(PROP_SQL_DML_KEYS);
		if(keys!=null) this.keys = keys.split(",");
		String values = properties.getProperty(PROP_SQL_DML_VALUES);
		if(values!=null) this.values = values.split(",");
		
	}

	@Override
	public StepResult execute() {
		StepResult result = new StepResult();
		Connection connection = null;
		
		ProcessData dataOut  = new ProcessData(this.properties); 
				
		result.setDataOut(dataOut);
		try {
			
			connection = this.getDBConnection();
		} catch (ClassNotFoundException | SQLException e) {
			result.setErrorCode(1);
			result.setMessage(e.getMessage());
			
			logger.error("Error getting connection", e);
		}
		
		if(connection!=null) {
			
			if(metadataInfo==null)
				metadataInfo = new CatalogMetadata(catalogName,schemaName);
			
			
			
            PreparedStatement preparedStatement;
			try {
				String query = buildQuery(connection);
				
				preparedStatement = connection.prepareStatement(query);
				
				for(DataRow row: dataIn.getDataRows()) {
					setParameters(preparedStatement, row);
					
					preparedStatement.executeUpdate();
					dataOut.addDataRow(row);
				}
				
				
			} catch (Exception e) {
				result.setErrorCode(2);
				result.setMessage(e.getMessage());
				logger.error("Error executing statement", e);
			}
			
			
			if(connection!=null)
				try {
					connection.close();
				} catch (SQLException e) {
					logger.error("Error closing connection", e);
				}
            

		}
		
		return result;
	}

	private void setParameters(PreparedStatement preparedStatement, DataRow row) throws SQLException {
		
		int index = 1;
		switch(dmlOperation) {
		
		case INSERT:
			
			for(Object value: row.values())
				setObjectValue(value, index++, preparedStatement);
			break;
		case UPDATE:
			
			if(keys!=null && values!=null) {
				for(String value:values) {
					setObjectValue(row.get(value), index++, preparedStatement);	
				}
				for(String key:keys) {
					setObjectValue(row.get(key), index++, preparedStatement);	
				}
				
			}
			
			break;
		}
	}
	
	private String buildQuery(Connection connection) throws SQLException, Exception  {
		
		String query = "";

		

		
		JdbcCatalogLoader loader = new JdbcCatalogLoader();
		loader.loadMetadata(metadataInfo, connection.getMetaData());
		TableMetadata table = metadataInfo.getSchemas().get(schemaName).getTables().get(tableName);
		
		switch(dmlOperation) {
			case INSERT:
				String params = "";
				for(int i=0;i<table.getColumns().entrySet().size();i++) {
					if(params.length()>0) params += ",";
					params+="?";
				}

				query = "INSERT INTO " + tableName + " VALUES(" + params + ")";
				break;
			case UPDATE:
				String set="";
				String where="";

				if(values!=null) {
					for(String value: values) {
						if(set.length()>0) set += ",";
						set += value + "=?";
					}
				}
				
				if(keys!=null) {
					for(String key: keys) {
						if(where.length()>0) where += " AND ";
						where += key + "=?";
					}
				}
				
				
				
				query = "UPDATE " + tableName + " SET " + set + " WHERE " + where;
				break;
			case DELETE:
				break;				
			
		}
		return query;
	}

	private void setObjectValue(Object value, int parameterIndex, PreparedStatement preparedStatement) throws SQLException {
		
		preparedStatement.setObject(parameterIndex, value);

	}
	

}
