package it.nrsoft.nrlib.process.transform;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import it.nrsoft.nrlib.process.DataRow;
import it.nrsoft.nrlib.process.InitialProperties;
import it.nrsoft.nrlib.process.ProcessData;
import it.nrsoft.nrlib.process.SimpleDataRow;
import it.nrsoft.nrlib.process.Step;
import it.nrsoft.nrlib.process.StepResult;

public class TransformationStep extends Step {
	
	private Map<String,Transformation> transformations = new LinkedHashMap<>();

	public TransformationStep(String name, InitialProperties properties) {
		super(name, properties);
	}


	@Override
	public StepResult execute() {
		
		StepResult result = new StepResult();
		ProcessData dataOut = new ProcessData(properties);
		
		for(DataRow row: dataIn.getDataRows()) {
			
			
			SimpleDataRow newRow = new SimpleDataRow();
			
			for(Entry<String, Transformation> entry: transformations.entrySet()) {
				
				String newField = entry.getKey();
				Transformation transformation = entry.getValue();
				
				
				Object value = transformation.apply(dataIn.getVariables(), row);
				newRow.put(newField, value );
			}
			
			dataOut.addDataRow(newRow);
			
		}

		
		
		result.setDataOut(dataOut);
		
		return result;
	}
	
	
	
	
	
	public void addTransformation(String newField, Transformation transformation) {
		this.transformations.put(newField, transformation);
	}


	public Map<String, Transformation> getTransformations() {
		return transformations;
	}


	public void setTransformations(Map<String, Transformation> transformations) {
		this.transformations = transformations;
	}
	
	

}
