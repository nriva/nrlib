package it.nrsoft.nrlib.steps;



import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import it.nrsoft.nrlib.process.DataRow;
import it.nrsoft.nrlib.process.InitialProperties;
import it.nrsoft.nrlib.process.ProcessData;
import it.nrsoft.nrlib.process.SimpleDataRow;
import it.nrsoft.nrlib.process.SimpleInitialProperties;
import it.nrsoft.nrlib.process.Step;
import it.nrsoft.nrlib.process.StepResult;
import it.nrsoft.nrlib.process.file.FileStep;
import it.nrsoft.nrlib.process.file.FileStepCsv;
import it.nrsoft.nrlib.process.file.FileStepCsvIn;
import it.nrsoft.nrlib.process.file.FileStepCsvOut;

public class FileStepInputCsvTest {
	
	@Test
	public void test()  {
		
		URL url = FileStepInputCsvTest.class.getResource("/FileStepInputCsv.txt");
		String path = url.getPath();
		if(path.startsWith("/")) path = path.substring(1);
			
		InitialProperties initProperies = new SimpleInitialProperties();
		
		initProperies.addProperty(FileStep.PROP_FILE_NAME, path);
		initProperies.addProperty(FileStepCsv.PROP_FILE_CSV_HEADER, "true");
		
		ProcessData data = new ProcessData(initProperies);
				
		Step step = new FileStepCsvIn("InCsv", initProperies );
		step.setDataIn(data);
		step.run();
		
		StepResult result = step.getLastResult();
		
		Assert.assertFalse(result.isError());
		
		ProcessData dataOut = result.getDataOut();

		Assert.assertNotNull("dataOut null", dataOut);
		Assert.assertNotNull("dataOut.getDataRows() null", dataOut.getDataRows());
		Assert.assertEquals(2, dataOut.getDataRows().size());
	}

}
