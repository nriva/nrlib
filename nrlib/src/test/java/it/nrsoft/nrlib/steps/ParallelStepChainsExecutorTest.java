package it.nrsoft.nrlib.steps;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.junit.Test;

import it.nrsoft.nrlib.process.IdleStep;
import it.nrsoft.nrlib.process.ParallelStepChainsExecutor;
import it.nrsoft.nrlib.process.ProcessData;
import it.nrsoft.nrlib.process.Step;
import it.nrsoft.nrlib.process.StepChain;
import it.nrsoft.nrlib.process.StepResult;

public class ParallelStepChainsExecutorTest {
	
	@Test
	public void test() throws InterruptedException, ExecutionException {
		
		
		StepChain sc1 = new StepChain("sc1");
		Step step1 = new IdleStep("Step1", null);
		sc1.addStep(step1);
		Step step2 = new IdleStep("Step2", null);
		sc1.addStep(step2);
		
		StepChain sc2 = new StepChain("sc2");
		Step step3 = new IdleStep("Step3", null);
		sc2.addStep(step3);
		Step step4 = new IdleStep("Step4", null);
		sc2.addStep(step4);

		
		ParallelStepChainsExecutor executor = new ParallelStepChainsExecutor();
		
		ProcessData dataIn = new ProcessData(null);
		executor.addStepChain(sc1, dataIn);
		executor.addStepChain(sc2, dataIn);
		
		executor.run();
		
		for(Future<StepResult> furtureResult : executor.getResults()) {
			
			StepResult result = furtureResult.get();
			if(result!=null) System.out.println(result.getMessage());
			
		}
		
	}

}
