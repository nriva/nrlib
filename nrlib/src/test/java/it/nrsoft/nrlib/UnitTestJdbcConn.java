package it.nrsoft.nrlib;




import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import it.nrsoft.nrlib.sql.jdbc.JdbcConnection;


public class UnitTestJdbcConn {
	
	@Before
	public void setup() throws SQLException
	{
        Connection connection = null;
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        try {
            connection = DriverManager.getConnection("jdbc:derby:firstdb;create=true");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        
        PreparedStatement statement = connection.prepareStatement("CREATE TABLE ACCOUNT(ID int primary key, name VARCHAR(100))");
        statement.execute();
        
        PreparedStatement statement2 = connection.prepareStatement("INSERT INTO ACCOUNT VALUES(1, 'TEST')");
        statement2.execute();
        
        connection.close();
		
	}


	@Test
	public final void test() throws SQLException, Exception {
		
		InputStream propConnStream = UnitTestJdbcConn.class.getResourceAsStream("/connection.properties");		
		
		java.util.Properties cadprop = new Properties();
		cadprop.load(propConnStream);
		
		JdbcConnection conn = new JdbcConnection(cadprop);
		conn.open();
		
		ResultSet rs = conn.queryExecute("select * from ACCOUNT");
		while(rs.next())
		{
			System.out.println(rs.getString(1));
		}
		conn.close();		
		
	}
	
	
	@After
	public void tearDown() throws IOException
	{
		
		try {
			DriverManager.getConnection("jdbc:derby:firstdb;shutdown=true");
		} catch (SQLException e) {
		}
		
		File file = new File("firstdb");
		TestUtils.deleteDirectoryRecursion(file);
	}

}
