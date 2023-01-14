package it.nrsoft.nrlib.mapping.connectors;

import java.io.*;
import java.sql.SQLException;
import java.util.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import it.nrsoft.nrlib.sql.*;
import it.nrsoft.nrlib.sql.jdbc.*;

public abstract class ConnectorDB extends Connector {
	
	static Logger logger = LogManager.getLogger(ConnectorDB.class.getName());
	
	
	protected JdbcConnection connection = null;
	
	
	protected String tablename="";
	protected String connpropfile="";
	protected String catalogname="";
	protected String schemaname="";
	
	protected TableMetadata tableMetadata;
	
	protected BasicSqlStatementBuilder builder=null;
	
	/* (non-Javadoc)
	 * @see cadit.mapping.connectors.Connector#open()
	 */
	@Override
	public boolean open() {
		
		boolean ok=true;
		
		Properties prop = new Properties();
		try {
			prop.load(new FileReader(connpropfile));
			
			connection = new JdbcConnection(prop);
			connection.open();
			
			
			if(tablename.length()>0) {
				CatalogMetadata info = new CatalogMetadata(catalogname,schemaname);
				
				JdbcCatalogLoader loader = new JdbcCatalogLoader();
				loader.loadMetadata(info, connection.getConnection().getMetaData());
				
				tableMetadata = info.getSchemas().get(schemaname).getTables().get(tablename);
				
				builder = new BasicSqlStatementBuilder(true, false, true, true);
			}
			connection.getConnection().setAutoCommit(false);
			
		} catch (FileNotFoundException e) {
			logger.error("Error opening", e);
			ok = false;
		} catch (IOException e) {
			logger.error("Error opening", e);
			ok = false;
		} catch (SQLException e) {
			logger.error("Error opening", e);
			ok = false;
		} catch (Exception e) {
			logger.error("Error opening", e);
			ok = false;
		}
		
		return ok;
	}


	/* (non-Javadoc)
	 * @see cadit.mapping.connectors.Connector#close()
	 */
	@Override
	public boolean close() {
		connection.close();
		return true;
	}









	@Override
	public boolean init(Map<String, String> properties) {
		super.init(properties);
		if(properties.containsKey("tablename"))
			tablename = properties.get("tablename");
		connpropfile = properties.get("connpropfile");
		catalogname = properties.get("catalogname");
		schemaname = properties.get("schemaname");
		return true;
	}	

}
