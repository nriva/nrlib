package it.nrsoft.nrlib.steps;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import it.nrsoft.nrlib.process.DataRow;
import it.nrsoft.nrlib.process.InitialProperties;
import it.nrsoft.nrlib.process.ProcessData;
import it.nrsoft.nrlib.process.SimpleDataRow;
import it.nrsoft.nrlib.process.SimpleInitialProperties;
import it.nrsoft.nrlib.process.Step;
import it.nrsoft.nrlib.process.StepResult;
import it.nrsoft.nrlib.process.file.FileStep;
import it.nrsoft.nrlib.process.file.FileStepCsv;
import it.nrsoft.nrlib.process.file.FileStepCsvOut;

public class FileStepOutputCsvTest {
	
	private File tmpOut = null;
	
	@After
	public void tearDown() {
		if(tmpOut!=null) {
			tmpOut.delete();
		}
		
		File file = new File( System.getProperty("java.io.tmpdir") + "/pippoSpring.txt");
		if(file.exists())
			file.delete();
	}
	
	@Test
	public void test() throws IOException {
		Map<String,String> initPropMap = new HashMap<>();
		
		
		tmpOut = File.createTempFile("pippo", ".txt");
		
		
		initPropMap.put(FileStep.PROP_FILE_NAME, tmpOut.getAbsolutePath());
		initPropMap.put(FileStepCsv.PROP_FILE_CSV_HEADER, "true");
		
			
		InitialProperties initProperies = new SimpleInitialProperties();
		initProperies.setProperties(initPropMap);
		
		Map<String, Object> dataRowValues = new HashMap<>();
		dataRowValues.put("COLONNA1", "VALORE1");
		dataRowValues.put("COLONNA2", "VALORE2");
		
		DataRow row = new SimpleDataRow();
		row.setRowValues(dataRowValues);
		
		
		
		List<DataRow> rows = new LinkedList<>();
		rows.add(row);
		
		
		ProcessData data = new ProcessData(initProperies);
		data.setDataRows(rows );
				
		Step step = new FileStepCsvOut("OutCsv", initProperies );
		step.setDataIn(data);
		step.run();
		
		StepResult result = step.getLastResult();
		
		Assert.assertFalse(result.isError());	
		
		
		byte[] expected = getBytes(FileStepOutputCsvTest.class.getResourceAsStream("/FileStepOutputCsv_result.txt"));
		byte[] actual = getBytes(new FileInputStream(tmpOut.getAbsolutePath()));
		
		Assert.assertArrayEquals(expected, actual);
		
	}
	
	
	private byte[] getBytes(InputStream inputStream) throws IOException {
		
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		
		int b = 0;
		
		while((b = inputStream.read())!=-1) {
			baos.write(b);
		}
		
	
		byte[] r = baos.toByteArray();
		baos.close();
		
		return r;

	}
	
	
	@Test
	public void testSpring() {
		
		
		
		ClassPathXmlApplicationContext context 
			= new ClassPathXmlApplicationContext("test_csv_out.xml");
		
		FileStepCsvOut step = (FileStepCsvOut)context.getBean("step1");
		
		step.run();
		
		StepResult result = step.getLastResult();
		
		Assert.assertEquals(result.getMessage(), false, result.isError());
		

		context.close();
		
		
	}	
	
	
	

}
