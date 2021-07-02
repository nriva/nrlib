package nrapps.process.cmd;

import it.nrsoft.nrlib.process.ProcessData;
import it.nrsoft.nrlib.process.StepChainExecutorSpring;

public class ProcessRunnerSpringCmd {
	
	public static void main(String[] args) {
		
		String springFile = args[0];
		String stepChainName = args[1];
		
		StepChainExecutorSpring executor = new StepChainExecutorSpring(springFile, stepChainName);
		
		ProcessData dataIn = null;
		executor.execute(dataIn );
	}

}
