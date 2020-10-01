package it.nrsoft.nrlib;


import static org.junit.Assert.*;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.BasicConfigurator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import it.nrsoft.nrlib.sql.*;
import it.nrsoft.nrlib.sql.jdbc.*;


/**
 * 
 */

/**
 * @author riva
 *
 */
public class UnitTestJdbcMeta {
	
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
	public final void test() throws Exception {
//		InputStream propConnStream = UnitTestJdbcConn.class.getResourceAsStream("/connection.properties");		
		
		
		BasicConfigurator.configure();		
		
		java.util.Properties cadprop = new java.util.Properties();
		cadprop.load( UnitTestJdbcConn.class.getResourceAsStream("/connection.properties"));

		
		JdbcConnection conn = new JdbcConnection(cadprop);
		boolean ok = conn.open();
		assertTrue(conn.getErrorMessage(),ok);
		
		CatalogMetadata info = new CatalogMetadata("","APP");
		
		JdbcCatalogLoader loader = new JdbcCatalogLoader();
		loader.loadMetadata(info, conn.getConnection().getMetaData());
		
		
		
		TableMetadata table = info.getSchemas().get("APP").getTables().get("ACCOUNT");
		assertNotNull(table);
		
		BasicSqlStatementBuilder builder = new BasicSqlStatementBuilder(false, false, true, false); 
		String select = builder.buildSelectAll(table);
		System.out.println(select);
		System.out.println(builder.buildInsert(table));
		System.out.println(builder.buildUpdate(table));
		System.out.println(builder.buildDelete(table));
		
		System.out.println(table.buildCreateStmt());
		
		for(IndexMetadata index : table.getIndexes().values())
		{
			System.out.println(index.buildCreateStmt());
		}
		
		
		ResultSet rs = conn.queryExecute(select);
		while(rs.next())
		{
			
			System.out.println( rs.getObject(1).toString() + "," + rs.getObject(2) );
			
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
