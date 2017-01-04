import java.io.PrintStream;
import java.util.List;
import java.util.Map.Entry;

import it.nrsoft.nrlib.time.StopWatch;
import it.nrsoft.nrlib.wax.MapAnalyzer;

public class MapAnalyzerResultWriterSimple implements MapAnalyzerResultWriter {

	@Override
	public void writeResult(PrintStream fout, MapAnalyzer ma, StopWatch stopWatch, boolean verbose) {
		fout.println("Esaminati " + ma.getNumberOfFiles() + " file in " + stopWatch.getElapsedTimeFormatted());
		fout.println();
		
		fout.println("Elenco file trovati per categoria:");
		for(Entry<String, List<String>> entry : ma.getFiles().entrySet())
		{
			fout.println("Categoria:\t" + entry.getKey() + "\t#\t" + entry.getValue().size());
		}

		
		if(verbose)  {
			fout.println();		
			for(Entry<String, List<String>> entry : ma.getFiles().entrySet())
			{
				fout.println("Elenco della categoria: " + entry.getKey());
				for(String filename : entry.getValue())
					fout.println(filename);
				fout.println();
				fout.println();
			}
		}
	}	

}
