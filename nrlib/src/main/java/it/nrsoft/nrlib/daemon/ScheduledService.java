package it.nrsoft.nrlib.daemon;

import org.apache.log4j.Logger;

public class ScheduledService implements Runnable {
	
		private static int i=0;
	 
	   private static final Logger logger = 
	       Logger.getLogger(ScheduledService.class);
	 
	    public void run() {
	    	i++;
	        logger.info("I'm alive!(" + i  + ")");
	    }
	}