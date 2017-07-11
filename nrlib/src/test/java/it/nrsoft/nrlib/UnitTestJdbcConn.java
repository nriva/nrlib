package it.nrsoft.nrlib;




import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import it.nrsoft.nrlib.sql.jdbc.JdbcConnection;


public class UnitTestJdbcConn {
	
//	public String driver = "org.apache.derby.jdbc.EmbeddedDriver";
//	public String protocol = "jdbc:derby:";
//	public String dbname = "c:/Temp/derbyDB";
	
	
//	  private static final String DB_DRIVER = "org.h2.Driver";
//	  private static final String DB_CONNECTION = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1";
	
	private static final String DB_DRIVER = "org.sqlite.JDBC";
	private static final String DB_CONNECTION = "jdbc:sqlite:test.db";
	  
	  
	  private static final String DB_USER = "";
	  private static final String DB_PASSWORD = "";	  

	@Before
	public final void setup() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException
	{

		Class.forName(DB_DRIVER).newInstance();

		//Connection conn = DriverManager.getConnection(protocol + dbname + ";create=true");
		Connection conn = DriverManager.getConnection(DB_CONNECTION);
		Statement statement = conn.createStatement();
		statement.execute("create table QGSID00 (i int primary key)");
		statement.execute("insert into QGSID00 values(1)");
		statement.execute("insert into QGSID00 values(2)");
		statement.close();
		conn.close();
	}

	@Test
	public final void test() throws SQLException, Exception {
		
		InputStream propConnStream = UnitTestJdbcConn.class.getResourceAsStream("/connection.properties");		
		
		java.util.Properties cadprop = new Properties();
		cadprop.load(propConnStream);
		
		JdbcConnection conn = new JdbcConnection(cadprop);
		conn.open();
		
		ResultSet rs = conn.queryExecute("select * from QGSID00");
		rs.next();
		assertEquals(1, rs.getInt(1));
		rs.next();
		assertEquals(2, rs.getInt(1));
		rs.close();
		conn.close();		
	}
	
	@After
	public final void teardown() throws SQLException
	{
		Connection conn = DriverManager.getConnection(DB_CONNECTION);
		Statement statement = conn.createStatement();
		statement.execute("drop table QGSID00");
		statement.close();
		conn.close();
	}
	

}
