package it.nrsoft.nrlib.util;

import java.io.PrintWriter;
import java.io.StringWriter;



public class Exceptions {
	
	public static String exceptionToString(Exception exception) {
		StringWriter sw = new StringWriter();
		PrintWriter pw =new PrintWriter(sw); 
		exception.printStackTrace(pw);
		return pw.toString();
	}

}
