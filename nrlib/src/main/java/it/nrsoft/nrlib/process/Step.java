package it.nrsoft.nrlib.process;

public abstract class Step implements Runnable {
	
	private StepResult result = null;
	
	protected String name ="";

	protected InitialProperties properties;
	
	protected ProcessData dataIn;
	
	private FieldMapping fieldMap = null;
	
	
	public Step(String name, InitialProperties properties) {
		this.name = name;
		this.properties = properties;
		
		if(properties!=null) {
		
			String fieldMapSource = properties.getProperty("fieldmap");
			
			if(fieldMapSource!=null) {
				if(fieldMapSource.length()>0)
					fieldMap = new FieldMapping(fieldMapSource);
			}
		}
		
	}
	
	protected String getOutputFieldName(String fieldName) {
		
		String newFieldName = fieldName;
		if(fieldMap!=null)
			if(fieldMap.hasMapForField(fieldName))
				newFieldName = fieldMap.mapField(fieldName);
		
		return newFieldName;
		
	}
	
	
	public ProcessData createProcessData() {
		return new ProcessData(properties);
	}
	
	
	public abstract StepResult execute();


	@Override
	public void run() {
		result = execute();
		
	}


	public StepResult getLastResult() {
		return result;
	}


	public void setDataIn(ProcessData dataIn) {
		this.dataIn = dataIn;
	}

}
