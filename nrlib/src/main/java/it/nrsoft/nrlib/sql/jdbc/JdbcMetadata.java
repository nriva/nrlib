package it.nrsoft.nrlib.sql.jdbc;

import java.util.Map;
import java.util.TreeMap;

public class JdbcMetadata {
	
    public static final String PROPERTY_PROTOCOL = "@protocol";
	public static final String PROPERTY_DRIVERNAME = "@driverName";
	
	
	
	public static final String PROTOCOL_AS400 = "as400";

	public static final String PROTOCOL_SQLSERVER = "sqlserver";
	
	public static final String PROTOCOL_JTDS = "jtds";
	
	public static final String PROTOCOL_DERBY = "derby";

	public static final String[] sqlserver_propertyNames = {	

			"applicationIntent",
			"applicationName",
			"authenticationScheme",
			"connectionstring",
			"databaseName",
			"disableStatementPooling",
			"encrypt",
			"failoverPartner",
			"hostNameInCertificate",
			"instanceName",
			"integratedSecurity",
			"lastUpdateCount",
			"lockTimeout",
			"loginTimeout",
			"multiSubnetFailover",
			"packetSize",
			"password",
			"portNumber",
			"responseBuffering",
			"selectMethod",
			"sendStringParametersAsUnicode",
			"sendTimeAsDatetime",
			"serverName",
			"trustServerCertificate",
			"trustStore",
			"trustStorePassword",
			"userName",
			"workstationID",
			"xopenStates"
		} ;
	
	public static final String[] as400_propertyNames = {	

		"applicationIntent",
		"applicationName",
		"authenticationScheme",
		"connectionstring",
		"databaseName",
		"disableStatementPooling",
		"encrypt",
		"failoverPartner",
		"hostNameInCertificate",
		"instanceName",
		"integratedSecurity",
		"lastUpdateCount",
		"lockTimeout",
		"loginTimeout",
		"multiSubnetFailover",
		"packetSize",
		"password",
		"portNumber",
		"responseBuffering",
		"selectMethod",
		"sendStringParametersAsUnicode",
		"sendTimeAsDatetime",
		"serverName",
		"trustServerCertificate",
		"trustStore",
		"trustStorePassword",
		"userName",
		"workstationID",
		"xopenStates"
	} ;	
	
	public static final String[] jtds_propertyNames = {
		"appName",
		"autoCommit",
		"batchSize",
		"bindAddress",
		"bufferDir",
		"bufferMaxMemory",
		"bufferMinPackets",
		"cacheMetaData",
		"database",
		"domain",
		"instance",
		"lastUpdateCount",
		"lobBuffer",
		"loginTimeout",
		"macAddress",
		"maxStatements",
		"namedPipe",
		"packetSize",
		"password",
		"port",
		"prepareSQL",
		"progName",
		"processId",
		"sendStringParametersAsUnicode",
		"server",
		"servertype",
		"socketTimeout",
		"socketKeepAlive",
		"ssl",
		"tcpNoDelay",
		"TDS",
		"useCursors",
		"useJCIFS",
		"useLOBs",
		"useNTLMv2",
		"user",
		"wsid",
		"xaEmulation"		
	};
	
	public static final String[] derby_propertyNames = {
		"database"
	};
	
	public static final String[] jdbcProtocols = {PROTOCOL_SQLSERVER, PROTOCOL_AS400, PROTOCOL_JTDS, PROTOCOL_DERBY};
	
	public static Map<String,String[]> propertyNames = new TreeMap<String,String[]>();
	
	public static Map<String,String[]> drivers = new TreeMap<String,String[]>();
	
	public static Map<String,String[]> connectionStringExSeq = new TreeMap<String,String[]>();
	
	static {
		
		propertyNames.put(PROTOCOL_SQLSERVER, sqlserver_propertyNames);
		propertyNames.put(PROTOCOL_AS400, as400_propertyNames);
		propertyNames.put(PROTOCOL_JTDS, jtds_propertyNames);
		propertyNames.put(PROTOCOL_DERBY, derby_propertyNames);
		
		drivers.put(PROTOCOL_SQLSERVER, new String[] {"com.microsoft.sqlserver.jdbc.SQLServerDriver"});
		drivers.put(PROTOCOL_AS400, new String[] {"com.ibm.as400.access.AS400JDBCDriver"});
		drivers.put(PROTOCOL_JTDS, new String[] {"net.sourceforge.jtds.jdbc.Driver"});
		drivers.put(PROTOCOL_DERBY, new String[] {"org.apache.derby.jdbc.EmbeddedDriver"});
		
		connectionStringExSeq.put(PROTOCOL_JTDS, new String[] {"servertype"});
		connectionStringExSeq.put(PROTOCOL_DERBY, new String[] {"database"});
		
		
	}
	
	
	public static boolean hasTypeSize(int typeId)
	{
		boolean bHasTypeSize = false;
		switch(typeId)
		{
		case java.sql.Types.CHAR:
		case java.sql.Types.NCHAR:
		case java.sql.Types.VARCHAR:
		case java.sql.Types.NVARCHAR:
		case java.sql.Types.BINARY:
		case java.sql.Types.VARBINARY:
		case java.sql.Types.NUMERIC:
		case java.sql.Types.DECIMAL:
			bHasTypeSize = true;
			break;
		case java.sql.Types.FLOAT:
		case java.sql.Types.DATE:
		case java.sql.Types.DOUBLE:
		case java.sql.Types.INTEGER:
			bHasTypeSize = false;
			break;
			
		}
		return bHasTypeSize;
	}
	
	public static boolean hasTypePrecision(int typeId)
	{
		boolean bHasTypePrecision = false;
		switch(typeId)
		{
		case java.sql.Types.CHAR:
		case java.sql.Types.NCHAR:
		case java.sql.Types.VARCHAR:
		case java.sql.Types.NVARCHAR:
		case java.sql.Types.BINARY:
		case java.sql.Types.VARBINARY:
		case java.sql.Types.FLOAT:
		case java.sql.Types.DATE:
		case java.sql.Types.DOUBLE:
		case java.sql.Types.INTEGER:
			bHasTypePrecision = false;
			break;
			
		case java.sql.Types.NUMERIC:
		case java.sql.Types.DECIMAL:
			
			bHasTypePrecision = true;
			break;
			
		}
		return bHasTypePrecision;
	}	
	


}
