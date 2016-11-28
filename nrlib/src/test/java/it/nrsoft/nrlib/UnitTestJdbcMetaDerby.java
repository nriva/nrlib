package it.nrsoft.nrlib;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import org.junit.Test;



public class UnitTestJdbcMetaDerby {
	
    private String driver = "org.apache.derby.jdbc.EmbeddedDriver";
    private String protocol = "jdbc:derby:";
	

    @Test
	public final void test() throws SQLException {
        Properties props = new Properties(); // connection properties
        // providing a user name and password is optional in the embedded
        // and derbyclient frameworks
        props.put("user", "user1");
        props.put("password", "user1");

        /* By default, the schema APP will be used when no username is
         * provided.
         * Otherwise, the schema name is the same as the user name (in this
         * case "user1" or USER1.)
         *
         * Note that user authentication is off by default, meaning that any
         * user can connect to your database using any password. To enable
         * authentication, see the Derby Developer's Guide.
         */

        String dbName = "derbyDB"; // the name of the database

        /*
         * This connection specifies create=true in the connection URL to
         * cause the database to be created when connecting for the first
         * time. To remove the database, remove the directory derbyDB (the
         * same as the database name) and its contents.
         *
         * The directory derbyDB will be created under the directory that
         * the system property derby.system.home points to, or the current
         * directory (user.dir) if derby.system.home is not set.
         */
        Connection conn = null;
        conn = DriverManager.getConnection(protocol + dbName
                + ";create=true", props);
        
        DatabaseMetaData meta = conn.getMetaData();
        
		 ResultSet catalogs = meta.getCatalogs();
	
		 while (catalogs.next() ) {
			 System.out.println(catalogs.getString(1));
		 }
		 
			ResultSet _schemas = meta.getSchemas();
			while (_schemas.next())
			{


					System.out.println(_schemas.getString(1));
			}
					 
        

	}

}
