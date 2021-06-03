package it.nrsoft.nrlib;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import it.nrsoft.nrlib.io.FileSystemPathValidator;
import it.nrsoft.nrlib.io.FileSystemWalker2;
import it.nrsoft.nrlib.pattern.Observer;
import it.nrsoft.nrlib.util.RecursiveWalkerElementValidator;
import it.nrsoft.nrlib.util.RecursiveWalkerElementValidatorDecorator;
import it.nrsoft.nrlib.util.RecursiveWalkerElementValidatorDummy;
import it.nrsoft.nrlib.util.RecursiveWalkerSubjectNotifyData;

public class UnitTestRecursiveWalker {
	

	@Before
	public final void setup() throws IOException
	{
//		File dir = new File("/ciao");
//		dir.mkdirs();
//		File file = new File("/ciao/prova.txt");
//		dir.createNewFile();
	}
	

	@Test
	public final void test1()
	{
//		FileSystemWalker2 walker = new FileSystemWalker2();
//		
//		walker.setValidator(new FileSystemPathValidator(null,"js"));
//		walker.getSubjectHelper().addObserver(new Observer() {
//			
//			@Override
//			public void Update(Object subject) {
//				RecursiveWalkerSubjectNotifyData data = (RecursiveWalkerSubjectNotifyData)subject;
//				System.out.println(data.getElementName());
//				
//			}
//		});
//		
//		walker.walk("/ciao");
	}
	
	@After
	public final void teardown()
	{
//		File dir = new File("/ciao");
//		dir.delete();
//		File file = new File("/ciao/prova.txt");
//		file.delete();
	}	

}
