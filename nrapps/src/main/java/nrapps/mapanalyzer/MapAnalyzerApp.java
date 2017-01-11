package nrapps.mapanalyzer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;
import java.util.logging.LogManager;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;


import it.nrsoft.nrlib.argparser.ArgParser;
import it.nrsoft.nrlib.argparser.InvalidSwitchException;
import it.nrsoft.nrlib.argparser.SwitchDefType;
import it.nrsoft.nrlib.io.FileSystemWalker;
import it.nrsoft.nrlib.io.FileSystemWalker2;
import it.nrsoft.nrlib.time.StopWatch;
import it.nrsoft.nrlib.util.Properties;
import it.nrsoft.nrlib.wax.MapAnalyzer;

public class MapAnalyzerApp {
	
	private static class Params
	{
		public String directory="";
		public String outFileName = null;
		public String errFileName = null;
		public String pattern = "*.xml";
		public boolean verbose;
		public Properties properties = new Properties();
	}
	
	//private static final LogManager logManager = LogManager.getLogManager();
	
	static PrintStream fout = System.out;
	static PrintStream ferr = System.err;

	public static void main(String[] args) throws Exception
	{

//		logManager.readConfiguration(MapAnalyzerApp.class.getResourceAsStream("/jul.properties"));
		
		Params params = getParams(args);
		
		MapAnalyzer ma = new MapAnalyzer(fout,ferr);
		
		List<Map<String,String>> queryData = params.properties.getMultipleValueProp("query");
		
		for(Map<String,String> query : queryData)
		
			if(query.get("type").equalsIgnoreCase("BOOLEAN"))
			{
				
				ma.addBooleanQuery(query.get("test"), query.get("group"), query.get("elsegroup"));
				// Esegue una WHERE
				//ma.setBooleanQuery("/page[@version='4.0']", "Versione 4");
				// Esegue una GROUP BY		
				//ma.setStringQuery("/page/@version",new String[]{"3.0","4.0"});
			}
			else if(query.get("type").equalsIgnoreCase("STRING")) {
				if(query.containsKey("values"))
					ma.addStringQuery(query.get("test"), query.get("values").split(","));
				else
					ma.addStringQuery(query.get("test"));
			}
		
		FileSystemWalker fsw = new FileSystemWalker(ma);
		
		
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		fsw.walk(params.directory,params.pattern);
		stopWatch.stop();
		
		MapAnalyzerResultWriter writer = (MapAnalyzerResultWriter)Class.forName( params.properties.getStringProp("mapanalyzerresultwriter.classname", "MapAnalyzerResultWriterSimple")).newInstance();
		writer.writeResult(fout, ma, stopWatch, params.verbose);
		
		fout.close();
	}

	private static Params getParams(String[] args) throws Exception {
		
		ArgParser argparser = new ArgParser();
		//argparser.setValueSep("=");
		argparser.addSwitchChar('-');
		argparser.addSwitchChar("--");
		
		
		argparser.addSwitchDef(new String[]{"c","config"}, SwitchDefType.stValued,"File di configurazione");
		argparser.addSwitchDef(new String[]{"p","pattern"}, SwitchDefType.stValued,"Pattern dei file da esaminare");
		argparser.addSwitchDef(new String[]{"v","verbose"}, SwitchDefType.stSimple,"Indica se deve essere verboso");
		argparser.addSwitchDef(new String[]{"d","directory"}, SwitchDefType.stValued,"File di configurazione");
		
		
		Params params = new Params(); 
		try {
			argparser.parse(args);
		} catch (InvalidSwitchException e) {
			System.out.println(argparser.usage());
			throw new Exception(e);
		}
		
		if(argparser.getArguments().size()>=1)
			params.outFileName = argparser.getArguments().get(0);

		if(argparser.getArguments().size()>=2)
			params.errFileName = argparser.getArguments().get(1);
		
		
		if(params.outFileName!=null)
			fout = new PrintStream(new FileOutputStream(params.outFileName));
		
		if(params.errFileName!=null)
			ferr = new PrintStream(new FileOutputStream(params.errFileName));
		
		if(argparser.getSwitches().size()>0)
			if(argparser.getSwitches().get("p")!=null)
				params.pattern=argparser.getSwitches().get("p").getValues().get(0);
		
		params.verbose = argparser.getSwitches().containsKey("v");
		
		
		params.properties.addMulipleValuePropName("query");
		
		if(argparser.getSwitches().containsKey("d"))
		{
			params.directory =argparser.getSwitches().get("d").getValues().get(0);
		}
		
		// OVERRIDE DA FILE DI CONF
		if(argparser.getSwitches().containsKey("c"))
		{
			String filename=argparser.getSwitches().get("c").getValues().get(0);
			params.properties.loadProperties(MapAnalyzerApp.class.getResourceAsStream("/" + filename));
			
			
			params.pattern = params.properties.getStringProp("pattern", params.pattern);
			params.verbose = params.properties.getBooleanProp("verbose", params.verbose);
			params.directory = params.properties.getStringProp("directory", params.directory);
			
		}
		
		fout.println("Esamino: " + params.directory);
		fout.println("Pattern: " + params.pattern);
		fout.println("Scrivo risultato su: " + (params.outFileName==null?"stdout":params.outFileName));
		fout.println("Scrivo errori su: " + (params.errFileName==null?"stderr":params.errFileName));
		
		fout.println();
		
		return params;		
	}
	
	



}
