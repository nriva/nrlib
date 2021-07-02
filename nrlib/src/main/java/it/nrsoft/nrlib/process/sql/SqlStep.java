package it.nrsoft.nrlib.process.sql;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import it.nrsoft.nrlib.process.InitialProperties;
import it.nrsoft.nrlib.process.Step;


public abstract class SqlStep extends Step {
	
	
	public static final String PROP_SQL_DRIVER = "sql.driver";
	public static final String PROP_SQL_CONNECTION_PASSWORD = "sql.connection.password";
	public static final String PROP_SQL_CONNECTION_USER = "sql.connection.user";
	public static final String PROP_SQL_CONNECTION_URL = "sql.connection.url";

	
    public SqlStep(String name,InitialProperties properties) {
		super(name, properties);
	}

	protected Connection getDBConnection() throws ClassNotFoundException, SQLException {
        Connection dbConnection = null;
        
        String url = properties.getProperty(PROP_SQL_CONNECTION_URL);
		String user = properties.getProperty(PROP_SQL_CONNECTION_USER);
		String password = properties.getProperty(PROP_SQL_CONNECTION_PASSWORD);
        String driver = properties.getProperty(PROP_SQL_DRIVER);
        
        
        
		Class.forName(driver);

		dbConnection = DriverManager.getConnection(url, user, password);
        return dbConnection;
    }	

}
