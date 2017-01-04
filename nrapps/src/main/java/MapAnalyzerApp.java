

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
import it.nrsoft.nrlib.time.StopWatch;
import it.nrsoft.nrlib.util.Properties;
import it.nrsoft.nrlib.wax.MapAnalyzer;

public class MapAnalyzerApp {
	
	private static final LogManager logManager = LogManager.getLogManager();

	public static void main(String[] args) throws Exception
	{

		logManager.readConfiguration(MapAnalyzerApp.class.getResourceAsStream("/jul.properties"));
		
//		Properties properties = new Properties();
//		properties.load(MapAnalyzerApp.class.getResourceAsStream("/application.properties"));
		
		Properties properties = new Properties();
		properties.addMulipleValuePropName("query");
		properties.loadProperties(MapAnalyzerApp.class.getResourceAsStream("/application.properties"));

		
		String path="";
		String outFile = null;
		String errFile = null;
		
		ArgParser argparser = new ArgParser();
		//argparser.setValueSep("=");
		argparser.addSwitchChar('-');
		argparser.addSwitchChar("--");
		
		argparser.addSwitchDef(new String[]{"p","pattern"}, SwitchDefType.stValued,"Pattern dei file da esaminare");
		
		
		argparser.setMinNumArgs(1);
		
		System.out.println(argparser.usage());
		
		
		argparser.parse(args);
		
		if(argparser.getArguments().size()>=1)
			path = argparser.getArguments().get(0);
			
		if(argparser.getArguments().size()>=2)
			outFile = argparser.getArguments().get(1);

		if(argparser.getArguments().size()>=3)
			errFile = argparser.getArguments().get(2);
		
		PrintStream fout = System.out;
		if(outFile!=null)
			fout = new PrintStream(new FileOutputStream(outFile));
		
		PrintStream ferr = System.err;
		if(errFile!=null)
			ferr = new PrintStream(new FileOutputStream(errFile));
		
		
		String pattern = "*.xml";
		if(argparser.getSwitches().size()>0)
			if(argparser.getSwitches().get("p")!=null)
				pattern=argparser.getSwitches().get("p").getValues().get(0);
		
		fout.println("Esamino: " + path);
		fout.println("Pattern: " + pattern);
		fout.println("Scrivo risultato su: " + (outFile==null?"stdout":outFile));
		fout.println("Scrivo errori su: " + (errFile==null?"stderr":errFile));
		
		fout.println();
		
		MapAnalyzer ma = new MapAnalyzer(fout,ferr);
		
		List<Map<String,String>> queryData = properties.getMultipleValueProp("query");
		
		
		for(Map<String,String> query : queryData)
		
			if(query.get("type").equalsIgnoreCase("BOOLEAN"))
			{
				
				ma.addBooleanQuery(query.get("test"), query.get("group"), query.get("elsegroup"));
				// Esegue una WHERE
				//ma.setBooleanQuery("/page[@version='4.0']", "Versione 4");
				// Esegue una GROUP BY		
				//ma.setStringQuery("/page/@version",new String[]{"3.0","4.0"});
			}
		
		FileSystemWalker fsw = new FileSystemWalker(ma);
		
		
		fsw.getListeners().add(ma);
		
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		fsw.walk(path,pattern);
		stopWatch.stop();
		
		MapAnalyzerResultWriter writer = (MapAnalyzerResultWriter)Class.forName( properties.getStringProp("mapanalyzerresultwriter.classname", "MapAnalyzerResultWriterSimple")).newInstance();
		writer.writeResult(fout, ma, stopWatch);
		
		fout.close();
	}
	
	



}
