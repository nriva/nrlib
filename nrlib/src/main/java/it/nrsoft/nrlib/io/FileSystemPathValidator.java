package it.nrsoft.nrlib.io;

import it.nrsoft.nrlib.util.RecursiveWalkerElementValidator;
import it.nrsoft.nrlib.util.RecursiveWalkerElementValidatorDecorator;

public class FileSystemPathValidator extends RecursiveWalkerElementValidatorDecorator {
	
	
		protected String extension = null;
		public FileSystemPathValidator(RecursiveWalkerElementValidator other, String extension) {
			super(other);
			this.extension = extension;
		}
		
	    @Override
	    public boolean ValidateElement(String elementName)
	    {
	    	return elementName.endsWith("." + extension);
	    }

}
