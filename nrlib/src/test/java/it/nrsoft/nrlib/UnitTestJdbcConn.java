package it.nrsoft.nrlib;




import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import org.junit.Test;

import it.nrsoft.nrlib.sql.jdbc.JdbcConnection;


public class UnitTestJdbcConn {


	@Test
	public final void test() throws SQLException, Exception {
		
		InputStream propConnStream = UnitTestJdbcConn.class.getResourceAsStream("/connection.properties");		
		
		java.util.Properties cadprop = new Properties();
		cadprop.load(propConnStream);
		
		JdbcConnection conn = new JdbcConnection(cadprop);
		conn.open();
		
		ResultSet rs = conn.queryExecute("select * from QGSID00");
		while(rs.next())
		{
			System.out.println(rs.getString(1));
		}
		conn.close();		
		
	}

}
