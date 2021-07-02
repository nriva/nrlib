package it.nrsoft.nrlib.steps;


import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import it.nrsoft.nrlib.process.InitialProperties;
import it.nrsoft.nrlib.process.ProcessData;
import it.nrsoft.nrlib.process.SimpleInitialProperties;
import it.nrsoft.nrlib.process.StepResult;
import it.nrsoft.nrlib.process.sql.SqlStepSelect;
import it.nrsoft.nrlib.process.sql.SqlStep;



public class SqlSelectStepTest {
	

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
        
        PreparedStatement statement2 = connection.prepareStatement("INSERT INTO ACCOUNT VALUES(1, 'TEST1')");
        statement2.execute();
        statement2.close();
        
        statement2 = connection.prepareStatement("INSERT INTO ACCOUNT VALUES(2, 'TEST2')");
        statement2.execute();
        statement2.close();
        
        
        connection.close();
		
	}
	
	
	@Test
	public void test() {
		
				
		SimpleInitialProperties initProperies = new SimpleInitialProperties();
		
		initProperies.addProperty(SqlStep.PROP_SQL_CONNECTION_URL, "jdbc:derby:firstdb");
		initProperies.addProperty(SqlStep.PROP_SQL_CONNECTION_USER, "");
		initProperies.addProperty(SqlStep.PROP_SQL_CONNECTION_PASSWORD, "");
		initProperies.addProperty(SqlStep.PROP_SQL_DRIVER, "org.apache.derby.jdbc.EmbeddedDriver");
		
		initProperies.addProperty(SqlStepSelect.PROP_SQL_SELECT_QUERY, "SELECT * FROM ACCOUNT");
		
		initProperies.addProperty("fieldmap", "ID:ID1,NAME:NAME1");

		SqlStepSelect step = new SqlStepSelect("SqlStep", initProperies );
		step.run();
		
		StepResult result = step.getLastResult();
		
		Assert.assertFalse(result.isError());
	}
	
	@After
	public void tearDown() throws IOException
	{
		
		try {
			DriverManager.getConnection("jdbc:derby:firstdb;shutdown=true");
		} catch (SQLException e) {
		}
		
		File file = new File("firstdb");
		FileUtils.deleteDirectory(file);
	}	

}
