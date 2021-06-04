package nrapps;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import nrapps.fileanalyzer.FileAnalyzerApp;

public class LogAnalyzerTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		String[] args = new String[] {
				"--output=loganalyzer_output.txt",
				//"-s",								// stdout
				"-a=context",
				"-p=beforeLen:2",
				"-p=afterLen:2",
				"-p=afterMarker:++ ",
				"-p=beforeMarker:-- ",
				//"--matchpfx=%1$s@%2$d",
				"--matchfmt=>> %1$s",
				"^pippo:[\\d]*",
				"C:\\Users\\riva\\git\\nrlib\\nrapps\\src\\test\\resources\\loganalyzer_input.txt",
				"C:\\Users\\riva\\git\\nrlib\\nrapps\\src\\test\\resources\\loganalyzer_input2.txt"
				};
		FileAnalyzerApp.main(args );
	}

}
