package it.nrsoft.nrlib.steps;


import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import it.nrsoft.nrlib.process.DataRow;
import it.nrsoft.nrlib.process.InitialProperties;
import it.nrsoft.nrlib.process.ProcessData;
import it.nrsoft.nrlib.process.SimpleDataRow;
import it.nrsoft.nrlib.process.SimpleInitialProperties;
import it.nrsoft.nrlib.process.StepResult;
import it.nrsoft.nrlib.process.sql.SqlStepSelect;
import it.nrsoft.nrlib.process.sql.SqlStep;
import it.nrsoft.nrlib.process.sql.SqlStepDML;



public class SqlStepInsertTest {
	

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
        
        connection.close();
		
	}
	
	
	@Test
	public void test() throws SQLException {
		
				
		SimpleInitialProperties initProperies = new SimpleInitialProperties();
		
		initProperies.addProperty(SqlStep.PROP_SQL_CONNECTION_URL, "jdbc:derby:firstdb");
		initProperies.addProperty(SqlStep.PROP_SQL_CONNECTION_USER, "");
		initProperies.addProperty(SqlStep.PROP_SQL_CONNECTION_PASSWORD, "");
		initProperies.addProperty(SqlStep.PROP_SQL_DRIVER, "org.apache.derby.jdbc.EmbeddedDriver");
		
		initProperies.addProperty(SqlStepDML.PROP_SQL_DML_TABLE, "ACCOUNT");
		initProperies.addProperty(SqlStepDML.PROP_SQL_DML_OPERATION, "INSERT");
		
		initProperies.addProperty(SqlStepDML.PROP_SQL_DML_JDBC_CATALOGNAME, "");
		initProperies.addProperty(SqlStepDML.PROP_SQL_DML_JDBC_SCHEMANAME, "APP");

		
		//initProperies.addProperty("fieldmap", "ID:ID1,NAME:NAME1");
				
				

		SqlStepDML step = new SqlStepDML("InsertStep", initProperies );
		ProcessData dataIn = new ProcessData(initProperies);
		DataRow row = new SimpleDataRow();
		row.put("ID", 1);
		row.put("name", "PIPPO");
		dataIn.addDataRow(row );
		
		row = new SimpleDataRow();
		row.put("ID", 2);
		row.put("name", "PLUTO");
		dataIn.addDataRow(row );
		
		step.setDataIn(dataIn );
		step.run();
		
		StepResult result = step.getLastResult();
		
		// CHECK
		
		Assert.assertFalse(result.getMessage(),result.isError());
		
        Connection connection = null;
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        try {
            connection = DriverManager.getConnection("jdbc:derby:firstdb");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM ACCOUNT");
        ResultSet rs = statement.executeQuery();
        
        while(rs.next()) {
        	switch(rs.getInt("ID")) {
        	case 1:
        		assertEquals("PIPPO", rs.getString("NAME"));
        		break;
        	case 2:
        		assertEquals("PLUTO", rs.getString("NAME"));
        		break;        		
        	}
        		
        }
        
        rs.close();
        connection.close();
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
