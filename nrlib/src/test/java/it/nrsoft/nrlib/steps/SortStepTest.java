package it.nrsoft.nrlib.steps;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import it.nrsoft.nrlib.process.DataRow;
import it.nrsoft.nrlib.process.InitialProperties;
import it.nrsoft.nrlib.process.ProcessData;
import it.nrsoft.nrlib.process.SimpleDataRow;
import it.nrsoft.nrlib.process.SimpleInitialProperties;
import it.nrsoft.nrlib.process.SortStep;
import it.nrsoft.nrlib.process.Step;
import it.nrsoft.nrlib.process.StepResult;
import it.nrsoft.nrlib.process.transform.TransformationStep;

public class SortStepTest {
	
	@Test
	public void testSort() {
		
		InitialProperties initProps = new SimpleInitialProperties();
		initProps.addProperty(SortStep.PROP_NAME_SORTFIELDS, "in1");
		SortStep step = new SortStep("SortStep", initProps );

		step.setDataIn(createTestCase(step,null));
		StepResult result = step.execute();
		
		for(DataRow row: result.getDataOut().getDataRows()) {
			System.out.println(row.toString());
		}
		
	}
	
	
	private ProcessData createTestCase(Step step, Map<String, Object> variables) {
		ProcessData dataIn = step.createProcessData();
		dataIn.setVariables(variables);
		
		DataRow row = new SimpleDataRow();
		row.put("in1", "pippo10");
		row.put("in2", "pluto10");
		dataIn.addDataRow(row );
		
		row = new SimpleDataRow();
		row.put("in1", "pippo02");
		row.put("in2", "pluto02");
		dataIn.addDataRow(row );
		
		return dataIn;
	}


}
