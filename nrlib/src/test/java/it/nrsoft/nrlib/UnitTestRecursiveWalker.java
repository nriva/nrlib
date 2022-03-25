package it.nrsoft.nrlib;

import java.io.File;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import it.nrsoft.nrlib.io.FileSystemPathValidator;
import it.nrsoft.nrlib.io.FileSystemWalker2;
import it.nrsoft.nrlib.pattern.Observer;
import it.nrsoft.nrlib.util.RecursiveWalkerSubjectNotifyData;

public class UnitTestRecursiveWalker {
	
	@Before
	public final void before() throws IOException
	{
		File dir = new File("testfsw/scripts");
		dir.mkdirs();
		
		File js = new File("testfsw/scripts/test.js");
		js.createNewFile();
	}
	


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
		
		walker.walk("testfsw/scripts");
	}
	
	@After
	public void after() throws IOException
	{
		File file = new File("testfsw");
		TestUtils.deleteDirectoryRecursion(file);

	}

}
