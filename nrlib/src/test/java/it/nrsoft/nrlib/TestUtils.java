package it.nrsoft.nrlib;

import java.io.File;
import java.io.IOException;

public class TestUtils {
	
	public static void deleteDirectoryRecursion(File file) throws IOException {
		  if (file.isDirectory()) {
		    File[] entries = file.listFiles();
		    if (entries != null) {
		      for (File entry : entries) {
		    	  deleteDirectoryRecursion(entry);
		      }
		    }
		  }
		  if (!file.delete()) {
		    throw new IOException("Failed to delete " + file);
		  }
		}

}
