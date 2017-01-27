package it.nrsoft.nrlib;


import static org.junit.Assert.*;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
	
	public String driver = "org.apache.derby.jdbc.EmbeddedDriver";
	public String protocol = "jdbc:derby:";
	public String dbname = "c:/Temp/derbyDB";
	
	
	@Before
	public final void setup() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException
	{

		Class.forName(driver).newInstance();

		Connection conn = DriverManager.getConnection(protocol + dbname + ";create=true");
		Statement statement = conn.createStatement();
		statement.execute("create table QGSID00 (i int primary key)");
		statement.execute("insert into QGSID00 values(1)");
		statement.execute("insert into QGSID00 values(2)");
		statement.close();
		conn.close();
	}
	
	@After
	public final void teardown() throws SQLException
	{
		Connection conn = DriverManager.getConnection(protocol + dbname);
		Statement statement = conn.createStatement();
		statement.execute("drop table QGSID00");
		statement.close();
		conn.close();
	}	
	

	@Test
	public final void test() throws Exception {
		InputStream propConnStream = UnitTestJdbcConn.class.getResourceAsStream("/connection.properties");		
		BasicConfigurator.configure();		
		
		java.util.Properties cadprop = new java.util.Properties();
		cadprop.load( propConnStream );

		
		JdbcConnection conn = new JdbcConnection(cadprop);
		boolean ok = conn.open();
		assertTrue(conn.getErrorMessage(),ok);
		
		CatalogMetadata info = new CatalogMetadata("","APP");
		
		JdbcCatalogLoader loader = new JdbcCatalogLoader();
		loader.loadMetadata(info, conn.getConnection().getMetaData());
		
		TableMetadata table = info.getSchemas().get("APP").getTables().get("QGSID00");
		assertNotNull(table);
		
		BasicSqlStatementBuilder builder = new BasicSqlStatementBuilder(false, false, true, false);
		String select = builder.buildSelectAll(table);
		assertEquals("SELECT I FROM QGSID00", select);

		assertEquals("INSERT INTO QGSID00(I) VALUES (?)", builder.buildInsert(table));
		assertEquals("UPDATE QGSID00 SET  WHERE I=?", builder.buildUpdate(table));
		assertEquals("DELETE FROM QGSID00 WHERE I=?", builder.buildDelete(table));
		
		assertEquals("CREATE TABLE \"QGSID00\" (\"I\" INTEGER NOT NULL, PRIMARY KEY (\"I\"))", table.buildCreateStmt());
		
//		for(IndexMetadata index : table.getIndexes().values())
//			System.out.println(index.buildCreateStmt());
		
//		ResultSet rs = conn.queryExecute(select);
//		rs.next();
//		assertEquals(1, rs.getInt(1));
//		rs.next();
//		assertEquals(2, rs.getInt(1));
//		rs.close();
		conn.close();		
	}

}
