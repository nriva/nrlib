package it.nrsoft.nrlib.time;

import java.util.concurrent.TimeUnit;

public class StopWatch {
	
	
	private class SimpeTimeFormatter implements TimeFormatter {
		
		private static final String FORMAT_MIN_D_SEC = "%d min : %d sec : %d ms";
		
		@Override
		public String formatElapsed(long elapsed) {

			return String.format(FORMAT_MIN_D_SEC, 
				    TimeUnit.NANOSECONDS.toMinutes(elapsed),
				    TimeUnit.NANOSECONDS.toSeconds(elapsed) - 
				    TimeUnit.MINUTES.toSeconds(TimeUnit.NANOSECONDS.toMinutes(elapsed)),
				    		TimeUnit.NANOSECONDS.toMillis(elapsed)
				    		- TimeUnit.MILLISECONDS.toNanos(TimeUnit.NANOSECONDS.toMillis(elapsed))
					);		
		}
	}; 
	
	
	
	public void setFormatter(TimeFormatter formatter) {
		this.formatter = formatter;
	}

	private long startTime=0;
	private long stopTime=0;
	
	private TimeFormatter formatter = new 	SimpeTimeFormatter();
	public long start()
	{
		return startTime = System.nanoTime();
	}
	
	public long stop()
	{
		if(startTime==0)
			throw new IllegalStateException();
		return stopTime = System.nanoTime();
	}
	
	/**
	 * @throws IllegalStateException
	 * @return
	 */
	public long elapsed()
	{
		if(startTime==0 || stopTime==0)
			throw new IllegalStateException();
		
		return stopTime - startTime;
	}
	
	/**
	 * @throws IllegalStateException
	 * @return
	 */
	public String getElapsedTimeFormatted()
	{
		if(formatter==null)
			formatter = new	SimpeTimeFormatter();
		return formatter.formatElapsed(elapsed());
	}
	
	public String getElapsedTimeFormatted(TimeFormatter formatter)
	{
		return formatter.formatElapsed(elapsed());
	}
	

}
