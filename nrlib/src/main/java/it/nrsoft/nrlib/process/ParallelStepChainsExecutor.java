package it.nrsoft.nrlib.process;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import it.nrsoft.nrlib.tuples.Pair;

public class ParallelStepChainsExecutor implements Runnable {
	
	List<Pair<StepChain,ProcessData>> stepChains
		= new LinkedList<>();
	
	List<Future<StepResult>> results = new LinkedList<>();
	
	public void addStepChain(StepChain stepChain, ProcessData processData) {

		stepChains.add(new Pair<>(stepChain, processData));
	}

	@Override
	public void run() {
		ExecutorService executor = Executors.newFixedThreadPool(10);
		
		for(Pair<? extends StepChain, ? extends ProcessData> stepChainAndData: stepChains) {
			
			StepChain stepChain = stepChainAndData.getMember1();
			ProcessData dataIn = stepChainAndData.getMember2();
			stepChain.setDataIn(dataIn);
			
			Future<StepResult> future = executor.submit(
				()-> {
					stepChain.run(); 
					return stepChain.getResult();
					});	

			results.add(future);
			
		}
		
		
		
	}

	public List<Future<StepResult>> getResults() {
		return results;
	}
	
	

}
