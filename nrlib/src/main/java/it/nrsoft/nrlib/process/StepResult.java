package it.nrsoft.nrlib.process;

public class StepResult {
	
	private int errorCode=0;
	private int returnCode=0;
	
	private String message = "";
	
	private ProcessData dataOut;

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public int getReturnCode() {
		return returnCode;
	}

	public void setReturnCode(int returnCode) {
		this.returnCode = returnCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public boolean isError() {
		return errorCode>0;
	}

	public ProcessData getDataOut() {
		return dataOut;
	}

	public void setDataOut(ProcessData dataOut) {
		this.dataOut = dataOut;
	}
	
	
	

}
