package it.nrsoft.nrlib.process.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Properties;

import it.nrsoft.nrlib.process.DataRow;
import it.nrsoft.nrlib.process.InitialProperties;
import it.nrsoft.nrlib.process.ProcessData;
import it.nrsoft.nrlib.process.SimpleDataRow;
import it.nrsoft.nrlib.process.StepResult;


public class SqlStepSelect extends SqlStep {
	
	public static final String PROP_SQL_SELECT_QUERY = "sql.query";
	protected String query = "";
	
	

	public SqlStepSelect(String name,InitialProperties properties) {
		super(name,properties);
		
		query = properties.getProperty(PROP_SQL_SELECT_QUERY);
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
		}
		
		if(connection!=null) {
            PreparedStatement selectPreparedStatement;
            ResultSet rs = null;
			try {
				selectPreparedStatement = connection.prepareStatement(this.query);
				rs = selectPreparedStatement.executeQuery();
			} catch (SQLException e) {
				result.setErrorCode(2);
				result.setMessage(e.getMessage());	
			}
			
			if(!result.isError()) {
				try {
					ResultSetMetaData metadata = rs.getMetaData();
					
					while(rs.next()) {
						
						DataRow row = new SimpleDataRow();
						for(int c=1;c<=metadata.getColumnCount();c++) {
							Object value = getObjectValue(rs, c, metadata);
							
							row.put(getOutputFieldName(metadata.getColumnName(c)), value);
							
						}
						dataOut.addDataRow(row);						
						
					}
					rs.close();
				} catch (SQLException e) {
					result.setErrorCode(3);
					result.setMessage(e.getMessage());	
				}
			}
			
			if(connection!=null)
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
            

		}
		
		return result;
	}

	private Object getObjectValue(ResultSet rs, int c,ResultSetMetaData metadata) throws SQLException {
		
		Object value = null; //  rs.getObject(c);
		
		int t=metadata.getColumnType(c);
		switch(t) {
			case java.sql.Types.BIT:
				value = new Boolean( rs.getInt(c) == 1 );
				break;
			case java.sql.Types.BOOLEAN:
				value = new Boolean( rs.getBoolean(c) );
				break;
			case java.sql.Types.TINYINT:
			case java.sql.Types.SMALLINT:
			case java.sql.Types.INTEGER:
				value = new Long( rs.getLong(c));
				break;
			case java.sql.Types.BIGINT:
				break;
			case java.sql.Types.FLOAT:
			case java.sql.Types.REAL:
			case java.sql.Types.DOUBLE:
			case java.sql.Types.NUMERIC:
			case java.sql.Types.DECIMAL:
				value = new Double( rs.getDouble(c));
				break;
			case java.sql.Types.CHAR:
			case java.sql.Types.VARCHAR:
			case java.sql.Types.LONGVARCHAR:
			case java.sql.Types.NCHAR:
			case java.sql.Types.NVARCHAR:
			case java.sql.Types.LONGNVARCHAR:
				value= new String( rs.getString(c));
				break;
			case java.sql.Types.DATE:
				break;
			case java.sql.Types.TIME:
				break;
			case java.sql.Types.TIMESTAMP:
				break;
			case java.sql.Types.TIME_WITH_TIMEZONE:
				break;
			case java.sql.Types.ROWID:
				break;
				

		}
		
		
		
		return value;
	}







	
	

}	

