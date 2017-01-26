package nrapps.jspanalyzer;

import it.nrsoft.nrlib.io.FileSystemWalker;
import it.nrsoft.nrlib.time.StopWatch;

public class WaxJspAnalyzerApp {

	public static void main(String[] args) {

		
		WaxJspAnalyzer wja = new WaxJspAnalyzer();

		FileSystemWalker fsw = new FileSystemWalker(wja);
		
		
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		fsw.walk(args[0],"*.jsp");
		stopWatch.stop();


	}

}
