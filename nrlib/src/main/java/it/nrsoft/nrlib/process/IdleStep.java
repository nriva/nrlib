package it.nrsoft.nrlib.process;

public class IdleStep extends Step {
	
	long timeout = 1000;

	public IdleStep(String name, InitialProperties properties) {
		super(name, properties);
		if(properties!=null)
			timeout = Long.valueOf(properties.getProperty("idle.timeout", "10000"));
	}

	@Override
	public StepResult execute() {
		StepResult result = new StepResult();
		result.setDataOut(dataIn);
		
		result.setErrorCode(0);
		result.setMessage(name + " waited for " + timeout + " ms!");
		
		try {
			Thread.sleep(timeout);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}

}
