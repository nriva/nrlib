package it.nrsoft.nrlib.mapping.connectors;

import java.io.*;
import java.sql.*;
import java.util.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;



public class InputConnectorDB extends ConnectorDB implements InputConnector {

	private String querytext;

	private String queryfile;

	/* (non-Javadoc)
	 * @see cadit.mapping.connectors.ConnectorDB#init(java.util.Map)
	 */
	@Override
	public boolean init(Map<String, String> properties) {
		boolean ok = super.init(properties);
		querytext = properties.get("querytext");
		queryfile = properties.get("queryfile");
		return ok;
	}

	static Logger logger = LogManager.getLogger(InputConnectorDB.class.getName());

	private ResultSetMetaData rsMetadata;
	private ResultSet resultSet = null;

	public boolean next() {
		logger.trace("Fetching next row...");
		try {
			return resultSet.next();
		} catch (SQLException e) {
			logger.error("Error fetching",e);
		}
		return false;
	}

	
	public Map<String, Object> getMap(Map<String, Map<String, String>> properties) throws Exception {
		Map<String,Object> map = new TreeMap<String,Object>();
		
		try {
			for(int i=1;i<=rsMetadata.getColumnCount();i++)
			{
				Object obj = resultSet.getObject(i);
				
				Map<String,String> props = properties.get(rsMetadata.getColumnName(i));

				FormatInfo formatInfo = getFormatInfo(props);
				
				switch(rsMetadata.getColumnType(i))
				{
					case java.sql.Types.NCHAR: 
					case java.sql.Types.NVARCHAR:
					case java.sql.Types.LONGNVARCHAR:
					case java.sql.Types.LONGVARCHAR:
					case java.sql.Types.VARCHAR:
					case java.sql.Types.CHAR:
						
						if(formatInfo.getType()==null 
								|| "".equals(formatInfo.getType()) 
								|| formatInfo.getType().equals(TYPENAME_STRING))
						{
							obj = obj.toString();
						}
						else 
							if(formatInfo.getType().equals(TYPENAME_DATE))
							{

								obj = formatInfo.parseDate((String) obj);							
							}
							else 							
							if(formatInfo.getType().equals(TYPENAME_DOUBLE)
						|| formatInfo.getType().equals(TYPENAME_FLOAT)
						|| formatInfo.getType().equals(TYPENAME_LONG)
						|| formatInfo.getType().equals(TYPENAME_INTEGER)
						|| formatInfo.getType().equals(TYPENAME_BIGINTEGER)
						|| formatInfo.getType().equals(TYPENAME_BIGDECIMAL))

						{
							obj = formatInfo.parseNumber(obj.toString());
						}
						else
							throw new Exception("Unsupported conversion");
						break;
						
					case java.sql.Types.NUMERIC:
					case java.sql.Types.DECIMAL:
					case java.sql.Types.BIGINT:
					case java.sql.Types.INTEGER:
					case java.sql.Types.SMALLINT:
					case java.sql.Types.TINYINT:
					case java.sql.Types.DOUBLE:
					case java.sql.Types.FLOAT:
					case java.sql.Types.REAL:						
		
						if(formatInfo.getType() == null
						|| "".equals(formatInfo.getType())
						|| formatInfo.getType().equals(TYPENAME_DOUBLE)
						|| formatInfo.getType().equals(TYPENAME_FLOAT)
						|| formatInfo.getType().equals(TYPENAME_LONG)
						|| formatInfo.getType().equals(TYPENAME_INTEGER)
						|| formatInfo.getType().equals(TYPENAME_BIGDECIMAL)
						|| formatInfo.getType().equals(TYPENAME_BIGINTEGER))
						{

						}
						else if(formatInfo.getType().equals(TYPENAME_STRING))
						{
							obj = formatInfo.formatNumber(obj);
						}
						else
							throw new Exception("Unsupported conversion");
						
						break;
						
					case java.sql.Types.TIMESTAMP:
						
						if(formatInfo.getType()==null
							|| formatInfo.getType().equals("") 
							||formatInfo.getType().equals(TYPENAME_DATE))
						{
							obj = new java.util.Date( ((java.sql.Timestamp) obj).getTime() );
						}						
						else if(formatInfo.getType().equals(TYPENAME_STRING))
						{
							obj = formatInfo.formatDate(obj);
						}
						else
							throw new Exception("Unsupported conversion");
						break;
						
						
					case java.sql.Types.DATE:
					
						
						if(formatInfo.getType()==null|| formatInfo.getType().equals("") 
						||formatInfo.getType().equals(TYPENAME_DATE))
						{
							obj = new java.util.Date( ((java.sql.Date) obj).getTime() );
						}						
						else if(formatInfo.getType().equals(TYPENAME_STRING))
						{
							obj = formatInfo.formatDate(obj);
						}
						else
							throw new Exception("Unsupported conversion");
						break;
						
					default:
						throw new Exception("Unsupported data type: " + rsMetadata.getColumnTypeName(i));
				
				}
				map.put(rsMetadata.getColumnName(i), obj);
			}
		} catch (SQLException e) {
			logger.error("Error reading data from resultset",e);
		}

		dump(map);
		return map;
	}

	@Override
	public boolean open()  {

		boolean ok = true;

		try {
				
			super.open();
			String select = "";
			if(tableMetadata!=null)
				select = builder.buildSelectAll(tableMetadata);
			
			if(querytext!=null && querytext.length()>0)
				select = querytext;
			else if(queryfile!=null && queryfile.length()>0)
			{
				  
				    try {
				    	BufferedReader br = new BufferedReader(new FileReader(queryfile));
				        StringBuilder sb = new StringBuilder();
				        String line = br.readLine();

				        while (line != null) {
				            sb.append(line);
				            sb.append(" ");
				            line = br.readLine();
				        }
				        br.close();
				        select = sb.toString();
				    } catch (FileNotFoundException e) {
				    	logger.warn("Warning reading query file", e);
					} catch (IOException e) {
						logger.warn("Warning reading query file", e);
					} 			
				
			}
			
			
			
			resultSet = connection.queryExecute(select);
			
			rsMetadata = resultSet.getMetaData();
		} catch (SQLException e) {
			logger.error("Error opening", e);
			ok = false;
		}
			
		
		return ok;
	}

	@Override
	public boolean close() {

		boolean ok = true;

			try {
				resultSet.close();
			} catch (SQLException e) {
				ok = false;
				logger.error("Error closing",e);
			}

			if(!super.close())
				ok = false;
		
		return ok;
	}



}
