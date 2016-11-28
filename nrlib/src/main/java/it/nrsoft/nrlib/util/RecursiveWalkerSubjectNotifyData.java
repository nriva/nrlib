package it.nrsoft.nrlib.util;

public class RecursiveWalkerSubjectNotifyData
{
    private String elementName;
    
    

    /**
	 * @return the elementName
	 */
	public String getElementName() {
		return elementName;
	}

	private boolean continueWalk = true;
	
	

    /**
	 * @return the continueWalk
	 */
	public boolean isContinueWalk() {
		return continueWalk;
	}



	/**
	 * @param continueWalk the continueWalk to set
	 */
	public void setContinueWalk(boolean continueWalk) {
		this.continueWalk = continueWalk;
	}



	public RecursiveWalkerSubjectNotifyData(String elementName)
    {
        this.elementName = elementName;
    }
}
