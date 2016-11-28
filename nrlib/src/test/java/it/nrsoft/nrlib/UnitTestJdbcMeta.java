package it.nrsoft.nrlib;


import static org.junit.Assert.*;

import java.io.*;
import java.sql.ResultSet;

import org.apache.log4j.BasicConfigurator;
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

	@Test
	public final void test() throws Exception {
//		InputStream propConnStream = UnitTestJdbcConn.class.getResourceAsStream("/connection.properties");		
		
		
		BasicConfigurator.configure();		
		
		java.util.Properties cadprop = new java.util.Properties();
		cadprop.load( new FileInputStream("connection.properties"));

		
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

}
