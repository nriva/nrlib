package it.nrsoft.nrlib.util;

import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;




/**
 * Zip file wrapper
 * @author riva
 *
 */
public class ZipFile {
	

	private ZipFile()  { 

	}

	/**
	 * 
	 * @param root
	 * @param filenames path realtive to root
	 * @param zipfilename
	 * @throws IOException
	 */
	public static void zipFile(String root, Iterable<String> filenames, String zipfilename) throws IOException {

		ZipOutputStream zos = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(zipfilename)));
		
		for(String filename : filenames) {

			FileInputStream fis = new FileInputStream(root + "\\" +filename);
	
			byte[] readBuffer = new byte[1024];
			int bytesIn = 0;
			
			String zipentryFilename = filename;
	
			ZipEntry anEntry = new ZipEntry(zipentryFilename);
			zos.putNextEntry(anEntry); 
			while ((bytesIn = fis.read(readBuffer)) != -1) {
				zos.write(readBuffer, 0, bytesIn);
			}
	
			fis.close();
		}
		zos.close();


	}
	
	
	public static void zipFile(String infilename, String zipentryFilename, String zipfilename) throws IOException {

		ZipOutputStream zos = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(zipfilename)));

		FileInputStream fis = new FileInputStream(infilename);

		byte[] readBuffer = new byte[1024];
		int bytesIn = 0;

		ZipEntry anEntry = new ZipEntry(zipentryFilename);
		zos.putNextEntry(anEntry); 
		while ((bytesIn = fis.read(readBuffer)) != -1) {
			zos.write(readBuffer, 0, bytesIn);
		}

		fis.close();
		zos.close();


	}
  	
  
 
  
  
}






