package it.nrsoft.nrlib.steps;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import it.nrsoft.nrlib.process.DataRow;
import it.nrsoft.nrlib.process.ProcessData;
import it.nrsoft.nrlib.process.SimpleDataRow;
import it.nrsoft.nrlib.process.StepChainExecutorSpring;
import it.nrsoft.nrlib.process.StepResult;

public class StepChainSpringExecutorTest {
	
	@Test
	public void test() {
		
		
		StepChainExecutorSpring executor = new StepChainExecutorSpring("test2.xml", "stepChain");
		ProcessData dataIn = new ProcessData(null);
		DataRow row = new SimpleDataRow();
		
		row.put("in1", "Ciao");
		row.put("in2", "Mondo");
		
		
		dataIn.addDataRow(row );
		StepResult result = executor.execute( dataIn );
		
		Assert.assertNotNull(result);
		Assert.assertFalse(result.isError());
		
		ProcessData dataOut = result.getDataOut();
		Assert.assertNotNull(dataOut);
		
		
		List<DataRow> rows = dataOut.getDataRows();
		Assert.assertNotNull(rows);
		Assert.assertEquals(1, rows.size());
		
		Assert.assertEquals("Ciao", rows.get(0).get("out1"));
		Assert.assertEquals("Mondo", rows.get(0).get("out2"));
		
		
	}

}
