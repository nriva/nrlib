package it.nrsoft.nrlib.io;

import java.io.*;
import java.util.*;

public class FileSystemWalker {
	
	
	private List<FileSystemWalkerListener> listeners = new LinkedList<FileSystemWalkerListener>();

	private String regex = null;
	
	public FileSystemWalker(FileSystemWalkerListener listener)
	{
		listeners.add(listener);
	}
	
	
	/**
	 * @return the listeners
	 */
	public synchronized List<FileSystemWalkerListener> getListeners() {
		return listeners;
	}

	public void walk(String root)
	{
		File rootFolder = new File(root);
		walkFolder(rootFolder);
	}
	
	public void walk(String root,String pattern)
	{
		File rootFolder = new File(root);
		regex = pattern.replace("?", ".?").replace("*", ".*");
		walkFolder(rootFolder);
		
	}
	

	private void visit(File file) {
		for(FileSystemWalkerListener listener : listeners)
		{
			listener.visitFile(file);
		}
		
	}

	private void walkFolder(File file) {
		if(file==null)
			return;
		
//		visit(file);
		
		File[] listFiles = file.listFiles();
		
		if(listFiles==null)
			return;
		for(File child : listFiles)
		{
			if(child.isDirectory())
				walkFolder(child);
			else if(regex!=null)
			{
				if(child.getName().toLowerCase().matches(regex))
					visit(child);
				else
					System.out.println("Rejected " + child.getName());
			}
			else
				visit(child);
		}
		
	}

}
