package it.nrsoft.nrlib.process;

import org.springframework.context.support.ClassPathXmlApplicationContext;


public class StepChainExecutorSpring extends StepChainExecutor {
	
	ClassPathXmlApplicationContext context = null;
	private String springFileName;
	private String stepChainBeanName;
	
	
	public StepChainExecutorSpring(String springFileName, String stepChainBeanName) {
		this.springFileName = springFileName;
		this.stepChainBeanName = stepChainBeanName;
	}
	
	public StepChain beforeExecute() {
		
		context = new ClassPathXmlApplicationContext(springFileName);
		return (StepChain)context.getBean(stepChainBeanName);
		
	}
	public void afterExecute() {
		if(context!=null)
			context.close();
	}
	



	

}
