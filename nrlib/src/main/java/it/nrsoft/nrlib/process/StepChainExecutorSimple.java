package it.nrsoft.nrlib.process;

public class StepChainExecutorSimple extends StepChainExecutor {
	
	private StepChain stepChain;

	public StepChainExecutorSimple(StepChain stepChain) {
		super();
		this.stepChain = stepChain;
	}

	@Override
	protected StepChain beforeExecute() {
		// TODO Auto-generated method stub
		return stepChain;
	}






}
