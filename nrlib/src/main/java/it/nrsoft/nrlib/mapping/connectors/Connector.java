package it.nrsoft.nrlib.mapping.connectors;

import java.text.*;
import java.util.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class Connector {

	static Logger logger = LogManager.getLogger(Connector.class.getName());	
	
	
	protected static final String DEF_PROPNAME_TYPE = "type";
	protected static final String DEF_PROPNAME_FMT = "fmt";
	protected static final String DEF_PROPNAME_LABEL = "label";
	
	protected static final String PROPNAME_LOCALE = "locale";
	protected static final String PROPNAME_FIRSTROWHEADERS = "firstrowheaders";
	protected static final String PROPNAME_SEPARATOR = "separator";
	protected static final String PROPNAME_FILENAME = "filename";
	protected static final String PROPNAME_HEADERS = "headers";


	protected static final String TYPENAME_STRING = "java.lang.String";
	protected static final String TYPENAME_BIGINTEGER = "java.math.BigInteger";
	protected static final String TYPENAME_BIGDECIMAL = "java.math.BigDecimal";
	protected static final String TYPENAME_INTEGER = "java.lang.Integer";
	protected static final String TYPENAME_LONG = "java.lang.Long";
	protected static final String TYPENAME_FLOAT = "java.lang.Float";
	protected static final String TYPENAME_DOUBLE = "java.lang.Double";
	protected static final String TYPENAME_DATE = "java.util.Date";

	public abstract boolean open();

	public abstract boolean close();
	
	public boolean init(Map<String,String> properties)
	{
		if(properties.containsKey(PROPNAME_LOCALE))
			locale = new Locale(properties.get(PROPNAME_LOCALE));
		
		defDateFormatLocale = DateFormat.getDateInstance(DateFormat.SHORT, locale);
		defNumberFormatLocale = NumberFormat.getInstance(locale);

		return true;
	}
	
	Locale locale = Locale.ENGLISH;
	
	protected DateFormat defDateFormatLocale = null;
	protected NumberFormat defNumberFormatLocale = null;	
	
	public void setLocale(String localeName)
	{
		if(!locale.getLanguage().equals(localeName))
		{
			locale = new Locale(localeName);
			defDateFormatLocale = DateFormat.getDateInstance(DateFormat.SHORT, locale);
			defNumberFormatLocale = NumberFormat.getInstance(locale);
		}
	}

	protected void createDefFormatter() {
		if(defDateFormatLocale==null)
			defDateFormatLocale = DateFormat.getDateInstance(DateFormat.SHORT, locale);
		if(defNumberFormatLocale==null)
			defNumberFormatLocale = NumberFormat.getInstance(locale);
	}
	
	
	protected static class FormatInfo {
		
		private NumberFormat numberFormat = null;
		private DateFormat dateFormat = null;
		private String type = null;
		private String fmt = null;
		/**
		 * @return the type
		 */
		public String getType() {
			return type;
		}
		/**
		 * @return the fmt
		 */
		public String getFmt() {
			return fmt;
		}
		
		public String formatNumber(Object obj)
		{
			if(numberFormat==null)
				return obj.toString();
			return numberFormat.format(obj);
		}
		
		public String formatDate(Object obj)
		{
			if(dateFormat==null)
				return obj.toString();
			return dateFormat.format(obj);
		}

		public Date parseDate(String source) throws ParseException {
			return dateFormat.parse(source);
		}
		
		public Number parseNumber(String source) throws ParseException {
			return numberFormat.parse(source);
		}		
		
		
		
		
		
	}
	
	protected FormatInfo getFormatInfo(Map<String, String> properties) {
		
		
		createDefFormatter();
		
		FormatInfo info = new FormatInfo(); 
		if(properties != null)
		{
			info.type = properties.get(DEF_PROPNAME_TYPE);
			info.fmt = properties.get(DEF_PROPNAME_FMT);
		}
		
		
		if(info.type!=null &&( info.type.equals(TYPENAME_DOUBLE)
				|| info.type.equals(TYPENAME_FLOAT)
				|| info.type.equals(TYPENAME_LONG)
				|| info.type.equals(TYPENAME_INTEGER)
				|| info.type.equals(TYPENAME_BIGINTEGER)
				|| info.type.equals(TYPENAME_BIGDECIMAL))) 
		{
			if(info.fmt!=null)
				info.numberFormat = new DecimalFormat(info.fmt); 
			else
				info.numberFormat = defNumberFormatLocale;	
		}
		
		if(info.type!=null &&  info.type.equals(TYPENAME_DATE)) {
			if(info.fmt!=null)
			{
				int dateStyle=-1;
				int timeStyle=-1;
				int p = info.fmt.indexOf('+');
				if(p>-1)
				{
					dateStyle = getStyleValue( info.fmt.substring(0, p) );
					timeStyle = getStyleValue( info.fmt.substring(p+1) );
				}
				
				
				if(info.fmt.equalsIgnoreCase("SHORT"))
				{
					info.dateFormat = defDateFormatLocale;
				}
			
				else if(dateStyle!=-1 && timeStyle!=-1)
					info.dateFormat = DateFormat.getDateTimeInstance(dateStyle, timeStyle, locale);
				else if(dateStyle!=-1)
					info.dateFormat = DateFormat.getDateInstance(dateStyle, locale);
				else
					info.dateFormat = new SimpleDateFormat(info.fmt,locale);
			}
			else
				info.dateFormat = defDateFormatLocale;
		}
		return info;
	}

	private int getStyleValue(String substring) {
		if(substring.equalsIgnoreCase("SHORT"))
			return DateFormat.SHORT;
		if(substring.equalsIgnoreCase("MEDIUM"))
			return DateFormat.MEDIUM;
		if(substring.equalsIgnoreCase("LONG"))
			return DateFormat.LONG;		
		
		return 0;
	}	
	
	
	protected void dump(Map<String, Object> input) {

		if(logger.isTraceEnabled()) {
			logger.trace("Map returned:");
			for(Map.Entry<String, Object> entry : input.entrySet())
			{
				logger.trace( entry.getKey() + " = " + entry.getValue() + "[" + entry.getValue().getClass().getName() + "]");
			}
		}
		
	}	

}