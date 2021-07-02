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



public class SqlStepUpdateTest {
	

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
        
        
        PreparedStatement statement = connection.prepareStatement("CREATE TABLE ACCOUNT(ID int primary key, name VARCHAR(100), city CHAR(10))");
        statement.execute();
        
        PreparedStatement statement2 = connection.prepareStatement("INSERT INTO ACCOUNT VALUES(1, 'TEST1', 'VERONA')");
        statement2.execute();
        statement2.close();
        
        statement2 = connection.prepareStatement("INSERT INTO ACCOUNT VALUES(2, 'TEST2', 'VICENZA')");
        statement2.execute();
        statement2.close();
        
        
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
		initProperies.addProperty(SqlStepDML.PROP_SQL_DML_OPERATION, "UPDATE");
		
		initProperies.addProperty(SqlStepDML.PROP_SQL_DML_KEYS, "ID");
		initProperies.addProperty(SqlStepDML.PROP_SQL_DML_VALUES, "NAME,CITY");
		
		initProperies.addProperty(SqlStepDML.PROP_SQL_DML_JDBC_CATALOGNAME, "");
		initProperies.addProperty(SqlStepDML.PROP_SQL_DML_JDBC_SCHEMANAME, "APP");

		
		//initProperies.addProperty("fieldmap", "ID:ID1,NAME:NAME1");
				
				

		SqlStepDML step = new SqlStepDML("UpdateStep", initProperies );
		ProcessData dataIn = new ProcessData(initProperies);
		DataRow row = new SimpleDataRow();
		row.put("ID", 1);
		row.put("NAME", "PIPPO");
		row.put("CITY", "LONDRA");
		dataIn.addDataRow(row );
		
		row = new SimpleDataRow();
		row.put("ID", 2);
		row.put("NAME", "PLUTO");
		row.put("CITY", "PARIGI");
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
        		assertEquals("LONDRA    ", rs.getString("CITY"));
        		break;
        	case 2:
        		assertEquals("PLUTO", rs.getString("NAME"));
        		assertEquals("PARIGI    ", rs.getString("CITY"));
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
