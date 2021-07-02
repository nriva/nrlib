package it.nrsoft.nrlib.process;

public abstract class StepChainExecutor {
	
	protected abstract StepChain beforeExecute();
	protected void afterExecute() {}
	
	private StepResult executeStepChain(StepChain stepChain, ProcessData dataIn) {
		stepChain.setDataIn(dataIn);

		stepChain.run();
		StepResult result = stepChain.getResult();
		return result;
	}
	
	public StepResult execute(ProcessData dataIn) {
		
		StepChain stepChain = beforeExecute();

		StepResult result = executeStepChain(stepChain, dataIn);
		
		afterExecute();
		
		return result;
	}

}
