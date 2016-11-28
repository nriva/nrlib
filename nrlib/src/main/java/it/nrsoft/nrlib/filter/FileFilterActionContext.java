package it.nrsoft.nrlib.filter;

public class FileFilterActionContext {
	
	
	private long currLine=0;
	



	/**
	 * @return the currLine
	 */
	public long getCurrLine() {
		return currLine;
	}

	/**
	 * @param currLine the currLine to set
	 */
	public void setCurrLine(long currLine) {
		this.currLine = currLine;
	}
	
	public void clear()
	{
		currLine = 0;
	}
	
	

}
