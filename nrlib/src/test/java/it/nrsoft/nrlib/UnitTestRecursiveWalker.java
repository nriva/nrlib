package it.nrsoft.nrlib;

import org.junit.Test;

import it.nrsoft.nrlib.io.FileSystemPathValidator;
import it.nrsoft.nrlib.io.FileSystemWalker2;
import it.nrsoft.nrlib.pattern.Observer;
import it.nrsoft.nrlib.util.RecursiveWalkerElementValidator;
import it.nrsoft.nrlib.util.RecursiveWalkerElementValidatorDecorator;
import it.nrsoft.nrlib.util.RecursiveWalkerElementValidatorDummy;
import it.nrsoft.nrlib.util.RecursiveWalkerSubjectNotifyData;

public class UnitTestRecursiveWalker {
	


	@Test
	public final void test1()
	{
		FileSystemWalker2 walker = new FileSystemWalker2();
		
		walker.setValidator(new FileSystemPathValidator(null,"js"));
		walker.getSubjectHelper().addObserver(new Observer() {
			
			@Override
			public void Update(Object subject) {
				RecursiveWalkerSubjectNotifyData data = (RecursiveWalkerSubjectNotifyData)subject;
				System.out.println(data.getElementName());
				
			}
		});
		
		walker.walk("c:\\Lavoro\\js");
	}

}
