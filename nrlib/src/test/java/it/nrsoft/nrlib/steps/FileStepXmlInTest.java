package it.nrsoft.nrlib.steps;

import java.net.URL;

import org.junit.Assert;
import org.junit.Test;

import it.nrsoft.nrlib.process.DataRow;
import it.nrsoft.nrlib.process.InitialProperties;
import it.nrsoft.nrlib.process.ProcessData;
import it.nrsoft.nrlib.process.SimpleInitialProperties;
import it.nrsoft.nrlib.process.Step;
import it.nrsoft.nrlib.process.StepResult;
import it.nrsoft.nrlib.process.file.FileStep;
import it.nrsoft.nrlib.process.file.FileStepCsv;
import it.nrsoft.nrlib.process.file.FileStepCsvIn;
import it.nrsoft.nrlib.process.xml.FileStepXmlIn;

public class FileStepXmlInTest {
	
	@Test
	public void test() {
		
		URL url = FileStepInputCsvTest.class.getResource("/example.xml");
		String path = url.getPath();
		if(path.startsWith("/")) path = path.substring(1);
			
		InitialProperties initProperies = new SimpleInitialProperties();
		
		initProperies.addProperty(FileStep.PROP_FILE_NAME, path);
		initProperies.addProperty(FileStepXmlIn.PROP_XML_DATATAGS, "employee@id,firstName,lastName");
		
		ProcessData data = new ProcessData(initProperies);
				
		Step step = new FileStepXmlIn("InXml", initProperies );
		step.setDataIn(data);
		step.run();
		
		StepResult result = step.getLastResult();
		
		Assert.assertFalse(result.isError());
		
		ProcessData dataOut = result.getDataOut();

		Assert.assertNotNull("dataOut null", dataOut);
		Assert.assertNotNull("dataOut.getDataRows() null", dataOut.getDataRows());
		Assert.assertEquals(4, dataOut.getDataRows().size());
		
		for(DataRow row: dataOut.getDataRows()) {
			System.out.println(row);
		}
		
		
	}

}
