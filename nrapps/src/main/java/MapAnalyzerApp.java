

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import it.nrsoft.nrlib.argparser.ArgParser;
import it.nrsoft.nrlib.argparser.InvalidSwitchException;
import it.nrsoft.nrlib.io.FileSystemWalker;
import it.nrsoft.nrlib.time.StopWatch;
import it.nrsoft.nrlib.wax.MapAnalyzer;

public class MapAnalyzerApp {

	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException, InvalidSwitchException
	{

		String path="";
		String outFile = null;
		String errFile = null;
		
		ArgParser argparser = new ArgParser();
		
		argparser.setMinNumArgs(1);
		
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
		
		
		fout.println("Esamino " + path);
		fout.println("Scrivo risultato su " + outFile==null?"stdout":outFile);
		fout.println("Scrivo errori su " + errFile==null?"stderr":errFile);
		
		fout.println();
		
		MapAnalyzer ma = new MapAnalyzer(fout,ferr);
		FileSystemWalker fsw = new FileSystemWalker(ma);
		
		
		fsw.getListeners().add(ma);
		
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		fsw.walk(path,"*.xml");
		stopWatch.stop();
		
		
		for(Entry<String, List<String>> entry : ma.getFiles().entrySet())
		{
			fout.println("File " + entry.getKey() + " # " + entry.getValue().size());
		}
		
		fout.println();
		
		

		
		fout.println("Esaminati " + ma.getNumberOfFiles() + " file in " + stopWatch.getElapsedTimeFormatted());
		fout.println();
		
		for(Entry<String, List<String>> entry : ma.getFiles().entrySet())
		{
			fout.println("File " + entry.getKey());
			for(String filename : entry.getValue())
				fout.println(filename);
			fout.println();
			fout.println();
		}
		
		fout.close();
	}

}
