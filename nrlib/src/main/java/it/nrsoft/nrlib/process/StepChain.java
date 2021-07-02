package it.nrsoft.nrlib.process;

import java.util.LinkedList;
import java.util.List;

public class StepChain implements Runnable {
	
	private String name;
	
	public StepChain() {
		this("");
	}
	
	
	public StepChain(String name) {
		super();
		this.name = name;
	}

	private List<Step> steps = new LinkedList<>();
	
	private StepResult result;
	
	public void addStep(Step step) {
		steps.add(step);
	}
	
	private ProcessData dataIn = null;
	
	

	@Override
	public void run() {
		
		StepResult result = null;
		
		if(dataIn==null) {
			dataIn = steps.get(0).createProcessData();
		}
		
		ProcessData data = dataIn;
		for(Step step: steps) {
			step.setDataIn(data);
			result = step.execute();
			if(!result.isError()) {
				data = result.getDataOut();
				data.setVariables(dataIn.getVariables());
			}
			else
				break;
			
		}
		
		this.result = result;
		
		
		
	}

	public StepResult getResult() {
		return result;
	}

	public void setDataIn(ProcessData dataIn) {
		this.dataIn = dataIn;
	}

	public List<Step> getSteps() {
		return steps;
	}

	public void setSteps(List<Step> steps) {
		this.steps = steps;
	}

	public String getName() {
		return name;
	}

}
