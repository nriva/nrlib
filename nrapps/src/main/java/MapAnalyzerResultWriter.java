import java.io.PrintStream;

import it.nrsoft.nrlib.time.StopWatch;
import it.nrsoft.nrlib.wax.MapAnalyzer;

public interface MapAnalyzerResultWriter {

	 void writeResult(PrintStream fout, MapAnalyzer ma, StopWatch stopWatch);
}
