package it.nrsoft.nrlib.io;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import it.nrsoft.nrlib.pattern.SubjectHelper;

public class StreamCopier {
	
	public class StartNotify implements it.nrsoft.nrlib.notify.StartNotify  {

		private String message="Inizio Copia";

		public StartNotify(String message) {
			this.message = message;
		}
		
		public StartNotify() {}

		@Override
		public String getMessage() {
			// TODO Auto-generated method stub
			return message;
		}

	}
	
	public static class ProgressNotify implements it.nrsoft.nrlib.notify.ProgressNotify  {

		private static int steps = 0;

		
		public ProgressNotify() {
			steps++;
		}

		@Override
		public String getMessage() {
			return new StringBuilder("Copia in corso... (step ").append(steps).append(")").toString() ;
		}

	}
	
	public class EndNotify implements it.nrsoft.nrlib.notify.EndNotify  {

		private String message="Copiati";
		private long bytes;

		public EndNotify(long bytes) {
			this.bytes = bytes;
		}
		
		public EndNotify() {}

		@Override
		public String getMessage() {
			return message + " " + String.valueOf(bytes) + " bytes";
		}

	}
	
	
	private SubjectHelper subjectHelper = new SubjectHelper(); 
	
	
	
	 public SubjectHelper getSubjectHelper() {
		return subjectHelper;
	}

	private static final int BUF_SIZE = 0x1000; // 4K
	 
	 public long copy(InputStream from, OutputStream to) throws IOException
	 {
		 return _copy(from,to,BUF_SIZE);
	 }
	 
	 public long copy(InputStream from, OutputStream to,int bufsize) throws IOException
	 {
		 subjectHelper.notify(new StartNotify());
		 
		 long bytes = _copy(from, to,bufsize);
		 
		 subjectHelper.notify(new EndNotify(bytes));
		 return bytes;
	 }
	
	  private long _copy(InputStream from, OutputStream to, int bufsize)
		      throws IOException {
		  
		byte[] buf = new byte[bufsize];
		long total = 0;
		while (true) {
		  int r = from.read(buf);
		  if (r == -1) {
		    break;
		  }
		  to.write(buf, 0, r);
		  subjectHelper.notify(new ProgressNotify());
		  total += r;
		}
		
		return total;
		  }

}
