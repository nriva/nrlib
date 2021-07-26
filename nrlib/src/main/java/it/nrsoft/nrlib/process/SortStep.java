package it.nrsoft.nrlib.process;

import java.util.Comparator;
import java.util.List;

public class SortStep extends Step {
	
	public static final String PROP_NAME_SORTFIELDS = "COMPARE_FIELDS";
	
	private String[] compareFieldNames = null;
	
	public SortStep(String name, InitialProperties properties) {
		super(name, properties);
		String v = properties.getProperty(PROP_NAME_SORTFIELDS);
		if(v!=null) {
			compareFieldNames = v.split(",");
		} else {
			compareFieldNames = new String[] {};
		}
	}

	@Override
	public StepResult execute() {
		StepResult result = new StepResult();
		
		List<DataRow> rows = dataIn.getDataRows();
		for(DataRow row: rows) {
			row.setCompareFieldNames(compareFieldNames);
		}
		
		rows.sort(null);
		
		ProcessData dataOut = new ProcessData(this.properties);
		
		
		for(DataRow row: rows) {
			dataOut.addDataRow(row);
			
		}
		
		result.setDataOut(dataOut);
		
		return result;
	}

}
