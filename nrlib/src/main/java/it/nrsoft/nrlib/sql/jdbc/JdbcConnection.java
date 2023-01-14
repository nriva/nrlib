package it.nrsoft.nrlib.sql.jdbc;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;
import java.sql.ResultSet;
import java.sql.Statement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;




	/**
	 * Jdbc Connection Wrapper.
	 * The class reads connection parameters from a property file.
	 * Key properties are:<br>
	 * drivername = jdbc driver name<br>
	 * connectionstring = jdbc connection string<br>
	 * If connectionstring is not provided, then it is built upon other parameters (@protocol, server and other protocol specific parameters)
	 * @author nriva
	 *
	 */

public class JdbcConnection {
	
	static Logger logger = LogManager.getLogger(JdbcConnection.class.getName());
	
    
	public  String catalogSeparator;
    public  String catalogName;
    
    private Connection dbConn = null;
    private String driverName;
    private String connString;
    private Properties propFile;
    


    /**
     * Create a new Jdbc connection wrapper.
     * @param propFile properties used to create the Jdbc connection
     */
	public JdbcConnection(Properties propFile) {
		this.propFile = propFile;
	}
	
	private String errorMessage = "";



	/**
	 * Open the wrapped JdbConnection.
	 * @return the error message, empty string means everything ok
	 */
	public boolean open() {
		dbConn =  null;
		
		try {
	    	Class.forName(getDriverName());
	    	Connection cn = DriverManager.getConnection(getConnectionString(), this.propFile);
	    	dbConn = cn;
		} catch (Exception e) {
			errorMessage = e.getMessage();
			logger.error("Connection open",e);
		}
		return dbConn!=null;
	}
	
	
	
	/**
	 * @return the errorMessage
	 */
	public String getErrorMessage() {
		return errorMessage;
	}



	/**
	 * Returns the wrapped connection.
	 * @return the connection
	 */
	public Connection getConnection() {
		return this.dbConn;
	}
	
	
	
	/**
	 * Close the wrapped connection.
	 */
	public void close() {
		try {
			dbConn.close();
		} catch (Exception e) {
			logger.error("Error closing",e);
		}
		dbConn = null;
	}
	
	
    /**
     * Set the driver name reading it from the property file.
     */
	private String getDriverName() {
		if(driverName==null || "".equals(driverName))
			this.driverName = propFile.getProperty(JdbcMetadata.PROPERTY_DRIVERNAME);
		
		logger.info("Using drivername = " + driverName);
		return driverName;
    }
    
    /**
     * Set the connection string reading it from the property file.
     */
	private String getConnectionString(){
    	this.connString = propFile.getProperty("connectionstring");
    	if(connString==null || connString.equals(""))
    	{
    		connString = "jdbc:" + propFile.getProperty(JdbcMetadata.PROPERTY_PROTOCOL);
    		
    		if(JdbcMetadata.connectionStringExSeq.containsKey(propFile.getProperty(JdbcMetadata.PROPERTY_PROTOCOL)))
    		{
    			String[] exSeq = JdbcMetadata.connectionStringExSeq.get(propFile.getProperty(JdbcMetadata.PROPERTY_PROTOCOL));
    			if(exSeq!=null)
    			{
    				for(String name : exSeq)
    				{
    					connString += ":" + propFile.getProperty(name);
    				}
    			}
    		}
    		
    		
    		if(propFile.containsKey("serverName")) {
    		
	    		connString += "://";
	    		
	    		String server = propFile.getProperty("serverName");
	    		if(server==null|| "".equals(server))
	    			server = propFile.getProperty("server");
	    		connString += server;
    		}
    	}
    	
    	logger.info("Using connectionString = " + connString);
    	
    	return connString;
    }
    

    
    /**
     * Execute a query and return the corresponding resultset.
     * @param sqlQuery the query to be executed
     * @return the result set, null in case of error
     */
    public ResultSet queryExecute(String sqlQuery) {

        ResultSet rs = null;
        Statement stmt = null;
        try {
            stmt = dbConn.createStatement();
            logger.trace("Executing " + sqlQuery);
            rs = stmt.executeQuery(sqlQuery);
        } catch (SQLException e1) {            
        	logger.error("Error executing query",e1);
            rs= null;
        }
        return rs;
    }
    
    
    public ResultSet queryExecute(String sqlQuery,Object[] params) {

        ResultSet rs = null;
        PreparedStatement stmt = null;
        try {
        	logger.trace("Preparing " + sqlQuery);
            stmt = dbConn.prepareStatement(sqlQuery);
            for(int i=1; i<=params.length; i++)
            	stmt.setObject(i, params[i-1]);
            rs = stmt.executeQuery();
        } catch (SQLException e1) {            
        	logger.error("Error executing query",e1);
            rs= null;
        }
        return rs;
    }    
    
    public PreparedStatement prepareStatement(String sqlCmd)
    {
    	PreparedStatement stmt = null;
		try {
			logger.trace("Preparing " + sqlCmd);
			stmt = dbConn.prepareStatement(sqlCmd);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			logger.error("Error preparing statement",e);
			stmt = null;
		}
    	return stmt;
    }
    
    /**
     * Execute a SQL command.
     * @param sqlCmd the command to be executed
     * @return the possible error message
     */
    public String commandExecute(String sqlCmd) {

        String errorString = "";
        
        Statement stmt = null;
        try {
        	stmt = dbConn.createStatement();
        	stmt.execute(sqlCmd);    
        } 
        catch (SQLException e1) {
        	errorString = e1.toString();
        	logger.error("Error executing command",e1);
        }
        
        return errorString;
    }    
}


