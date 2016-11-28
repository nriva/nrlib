package it.nrsoft.nrlib.wax;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;
import java.util.Map.Entry;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import it.nrsoft.nrlib.io.FileSystemWalker;

public class MapAnalyzerTest {

	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException
	{
		
		
		PrintStream fout = new PrintStream(new FileOutputStream("c:/temp/log.txt"));
		PrintStream ferr = new PrintStream(new FileOutputStream("c:/temp/err.txt"));
		
		
		MapAnalyzer ma = new MapAnalyzer(fout,ferr);

		FileSystemWalker fsw = new FileSystemWalker(ma);
		
		fsw.walk(args[0],"*.xml");
		
		
		
		for(Entry<String, List<String>> entry : ma.getFiles().entrySet())
		{
			fout.println("File " + entry.getKey() + " # " + entry.getValue().size());
		}
		
		fout.println();
		
		fout.println("Numero totale file " + ma.getNumberOfFiles());
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
