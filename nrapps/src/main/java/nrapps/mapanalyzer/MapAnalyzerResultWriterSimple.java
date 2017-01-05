package nrapps.mapanalyzer;

import java.io.File;
import java.io.PrintStream;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import it.nrsoft.nrlib.time.StopWatch;
import it.nrsoft.nrlib.wax.MapAnalyzer;

public class MapAnalyzerResultWriterSimple implements MapAnalyzerResultWriter {
	
	static Map<String,String> categories = new TreeMap<String,String>();
	
	static {
		categories.put("14" ,"AZIONE: Selezione + Lista (anche Cross Browser)");
		categories.put("15" ,"AZIONE: GEstione (Anche Cross Browser)");
		categories.put("16" ,"AZIONE: Selezione + Lista + Manutenzione (Anche Cross Browser)");
		categories.put("17" ,"AZIONE: Lista Editabile (Anche Cross Browser)");
		categories.put("20" ,"AZIONE: Lista Editabile \"Fast Edit (2)\" (Anche Cross Browser)");
		categories.put("21" ,"AZIONE: Master-Detail (Anche Cross Browser)");
		categories.put("22" ,"OpenCity Wax (Cross Browser)");
		categories.put("4", "Selezione (Anche Cross Browser)");
		categories.put("99","Vuoto");
		categories.put("err","n/a");
		categories.put("npg","n/a");
	}

	@Override
	public void writeResult(PrintStream fout, MapAnalyzer ma, StopWatch stopWatch, boolean verbose) {
//		fout.println("Esaminati " + ma.getNumberOfFiles() + " file in " + stopWatch.getElapsedTimeFormatted());
//		fout.println();
//		
//		fout.println("Elenco file trovati per categoria:");
//		for(Entry<String, List<String>> entry : ma.getFiles().entrySet())
//		{
//			fout.println("Categoria:\t" + entry.getKey() + "\t#\t" + entry.getValue().size());
//		}

		
		if(verbose)  {
			fout.println();		
			for(Entry<String, List<String>> entry : ma.getFiles().entrySet())
			{
//				fout.println("Elenco della categoria: " + entry.getKey());
				for(String filename : entry.getValue())
				{
					File file = new File(filename);
					fout.println(file.getName().substring(0,2).toUpperCase() + file.getName().substring(4,7).toUpperCase() + "," + categories.get(entry.getKey()));
				}
//				fout.println();
//				fout.println();
			}
		}
	}	

}
